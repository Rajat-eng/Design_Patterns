package models;

public class Tax {
    private double serviceTax;
    private double swatchCess;
    private double krishiCess;

    public Tax(double serviceTax, double swatchCess, double krishiCess) {
        this.serviceTax = serviceTax;
        this.swatchCess = swatchCess;
        this.krishiCess = krishiCess;
    }
    public double getServiceTax() {
        return serviceTax;
    }   
    public double getSwatchCess() {
        return swatchCess;
    }
    public double getKrishiCess() {
        return krishiCess;
    }
    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }
    public void setSwatchCess(double swatchCess) {
        this.swatchCess = swatchCess;
    }
    public void setKrishiCess(double krishiCess) {
        this.krishiCess = krishiCess;
    }
    
}
