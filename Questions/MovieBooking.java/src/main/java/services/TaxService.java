package services;

public class TaxService {
    public static final double SERVICE_TAX = 0.14;
    public static final double SWACHH_CESS = 0.005;
    public static final double KRISHI_CESS = 0.005;

   

    public double calculateTotalWithTaxes(int baseAmount) {
        return baseAmount * (1 + SERVICE_TAX + SWACHH_CESS + KRISHI_CESS);
    }

    public double getServiceTax(double base) {
        return base * SERVICE_TAX;
    }

    public double getSwachhCess(double base) {
        return base * SWACHH_CESS;
    }

    public double getKrishiCess(double base) {
        return base * KRISHI_CESS;
    }
}
