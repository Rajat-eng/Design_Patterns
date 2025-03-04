package SOLID.Open_Closed.Class;

import SOLID.Open_Closed.Interface.Invoice;

public class DiscountedInvoice implements Invoice {
    public double calculateTotal() {
        return 80;
    }
}
