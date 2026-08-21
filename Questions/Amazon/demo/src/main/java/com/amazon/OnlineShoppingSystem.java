package com.amazon;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.amazon.ChainOfResponsibility.DeliveredOrderReviewValidationHandler;
import com.amazon.ChainOfResponsibility.DuplicateReviewValidationHandler;
import com.amazon.ChainOfResponsibility.OrderExistsReviewValidationHandler;
import com.amazon.ChainOfResponsibility.OrderOwnershipReviewValidationHandler;
import com.amazon.ChainOfResponsibility.OrderReviewValidationHandler;
import com.amazon.ChainOfResponsibility.ProductInOrderReviewValidationHandler;
import com.amazon.ChainOfResponsibility.RatingRangeReviewValidationHandler;
import com.amazon.ChainOfResponsibility.ReviewValidationContext;
import com.amazon.ChainOfResponsibility.ReviewValidationResult;
import com.amazon.Exceptions.OutOfStockException;
import com.amazon.Models.Address;
import com.amazon.Models.Customer;
import com.amazon.Models.Order;
import com.amazon.Models.OrderReview;
import com.amazon.Models.Product;
import com.amazon.Models.ShoppingCart;
import com.amazon.Services.InventoryService;
import com.amazon.Services.OrderService;
import com.amazon.Services.PaymentService;
import com.amazon.Services.SearchService;
import com.amazon.Strategy.PaymentStrategy;

public class OnlineShoppingSystem {
    private static volatile OnlineShoppingSystem instance;

    // Data stores
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Map<String, OrderReview> orderReviews = new ConcurrentHashMap<>();
    private final Map<String, ReentrantLock> customerLocks = new ConcurrentHashMap<>();

    // Services
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final SearchService searchService;
    private final OrderReviewValidationHandler reviewValidationChain;

    private OnlineShoppingSystem() {
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.orderService = new OrderService(inventoryService);
        this.searchService = new SearchService(products.values());
        this.reviewValidationChain = buildReviewValidationChain();
    }

    private OrderReviewValidationHandler buildReviewValidationChain() {
        OrderReviewValidationHandler orderExists = new OrderExistsReviewValidationHandler();
        OrderReviewValidationHandler orderOwnership = new OrderOwnershipReviewValidationHandler();
        OrderReviewValidationHandler deliveredOrder = new DeliveredOrderReviewValidationHandler();
        OrderReviewValidationHandler productInOrder = new ProductInOrderReviewValidationHandler();
        OrderReviewValidationHandler ratingRange = new RatingRangeReviewValidationHandler();
        OrderReviewValidationHandler duplicateReview = new DuplicateReviewValidationHandler();

        orderExists
                .setNext(orderOwnership)
                .setNext(deliveredOrder)
                .setNext(productInOrder)
                .setNext(ratingRange)
                .setNext(duplicateReview);
        return orderExists; 
    }

    private String getReviewKey(String orderId, String productId) {
        return orderId + "::" + productId;
    }

    public static OnlineShoppingSystem getInstance() {
        if (instance == null) {
            synchronized (OnlineShoppingSystem.class) {
                if (instance == null) { // double-checked locking because of volatile keyword 
                    instance = new OnlineShoppingSystem();
                }
            }
        }
        return instance;
    }

    // --- Facade Methods for simplified interaction ---
    public void addProduct(Product product, int initialStock) {
        products.put(product.getId(), product);
        inventoryService.addStock(product, initialStock);
    }

    public Customer registerCustomer(String name, String email, String password, Address address) {
        Customer customer = new Customer(name, email, password, address);
        customers.put(customer.getId(), customer);
        return customer;
    }

    private ReentrantLock getCustomerLock(String customerId) {
        return customerLocks.computeIfAbsent(customerId, ignored -> new ReentrantLock());
    }

    public void addToCart(String customerId, String productId, int quantity) {
        Customer customer = customers.get(customerId);
        Product product = products.get(productId);
        ReentrantLock customerLock = getCustomerLock(customerId);
        // Per-customer lock allows parallel operations across different customers.
        customerLock.lock();
        try {
            customer.getAccount().getCart().addItem(product, quantity);
        } finally {
            customerLock.unlock();
        }
    }

    public ShoppingCart getCustomerCart(String customerId) {
        ReentrantLock customerLock = getCustomerLock(customerId);
        customerLock.lock();
        try {
            Customer customer = customers.get(customerId);
            return customer.getAccount().getCart();
        } finally {
            customerLock.unlock();
        }
    }

    public List<Product> searchProducts(String name) {
        return searchService.searchByName(name);
    }

    public Order placeOrder(String customerId, PaymentStrategy paymentStrategy) {
        Customer customer = customers.get(customerId);
        ShoppingCart cart = customer.getAccount().getCart();
        ReentrantLock customerLock = getCustomerLock(customerId);
        try {
            if (!customerLock.tryLock(2, TimeUnit.SECONDS)) {
                // if other thread is already processing checkout for this customer, we wait for a short time and fail fast if still locked 
                System.out.println("Checkout is busy for this customer. Please retry.");
                return null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Checkout interrupted. Please retry.");
            return null;
        }

        // Lock the whole checkout so payment and order creation remain atomic per customer.
        try {
            double amount = cart.calculateTotal();
            if (cart.isEmpty()) {
                System.out.println("Cannot place an order with an empty cart.");
                return null;
            }

            // Serialize payment and order creation per customer to prevent duplicate concurrent checkouts.
            boolean paymentSuccess = paymentService.processPayment(paymentStrategy, amount);
            if (!paymentSuccess) {
                System.out.println("Payment failed. Please try again.");
                return null;
            }

            try {
                Order order = orderService.createOrder(customer, cart);
                orders.put(order.getId(), order);
                cart.clearCart();
                return order;
            } catch (OutOfStockException e) {
                System.err.println("Order placement failed: " + e.getMessage());
                boolean refundSuccess = paymentService.processRefund(paymentStrategy, amount);
                if (!refundSuccess) {
                    System.err.println("Automatic refund failed. Please contact support.");
                }
                return null;
            } catch (Exception e) {
                System.err.println("Order placement failed: " + e.getMessage());
                boolean refundSuccess = paymentService.processRefund(paymentStrategy, amount);
                if (!refundSuccess) {
                    System.err.println("Automatic refund failed. Please contact support.");
                }
                return null;
            }
        } finally {
            customerLock.unlock();
        }
    }

    public boolean submitOrderReview(String customerId, String orderId, String productId, int rating, String comment) {
        ReentrantLock customerLock = getCustomerLock(customerId);
        customerLock.lock();
        try {
            Order order = orders.get(orderId);
            String reviewKey = getReviewKey(orderId, productId);
            ReviewValidationContext context = new ReviewValidationContext(
                    customerId,
                    orderId,
                    productId,
                    rating,
                    order,
                    orderReviews,
                    reviewKey
            );
            // review validation chain 
            ReviewValidationResult validationResult = reviewValidationChain.validate(context);
            if (!validationResult.isValid()) {
                System.out.println(validationResult.getMessage());
                return false;
            }

            String safeComment = comment == null ? "" : comment.trim();
            orderReviews.put(reviewKey, new OrderReview(orderId, productId, customerId, rating, safeComment));
            System.out.println("Review submitted successfully.");
            return true;
        } finally {
            customerLock.unlock();
        }
    }

    public Optional<OrderReview> getOrderReview(String orderId, String productId) {
        return Optional.ofNullable(orderReviews.get(getReviewKey(orderId, productId)));
    }
}
