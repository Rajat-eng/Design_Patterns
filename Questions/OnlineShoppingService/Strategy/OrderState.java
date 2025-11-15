package Questions.OnlineShoppingService.Strategy;

import Questions.OnlineShoppingService.Model.Order;

public interface OrderState {
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
}