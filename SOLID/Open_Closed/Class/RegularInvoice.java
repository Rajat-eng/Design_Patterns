package SOLID.Open_Closed.Class;

import SOLID.Open_Closed.Interface.Invoice;

public class RegularInvoice implements Invoice{
    public double calculateTotal() {
        return 100;
    }
}
