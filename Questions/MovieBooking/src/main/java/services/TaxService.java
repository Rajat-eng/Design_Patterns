package services;

import models.Tax;

public class TaxService {
    public static final double SERVICE_TAX = 0.14;
    public static final double SWACHH_CESS = 0.005;
    public static final double KRISHI_CESS = 0.005;
    private Tax tax;
   
    public Tax generateTax(int amount){
        double serviceTax = amount * SERVICE_TAX;
        double swachhCess = amount * SWACHH_CESS;
        double krishiCess = amount * KRISHI_CESS;
        tax = new Tax(serviceTax, swachhCess, krishiCess);
        return tax;
    }

    public double calculateWithTaxes(int amount){
        double totalAmount = amount *(1+ SERVICE_TAX + SWACHH_CESS + KRISHI_CESS);
        return totalAmount;
    }
}
