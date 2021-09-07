
package org.ark.sales;

public class modelSalesItems {
    
    private String name;
    private double sellPrice;
    private int qty;
    private double subTotal;

    public modelSalesItems() {
    }

    public modelSalesItems(String name, double sellPrice, int qty, double subTotal) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.qty = qty;
        this.subTotal = subTotal;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    
}
