package org.ark.purchases;


public class modelPurchaseItems {
    
    private String name;
    private double unitcost;
    private int qty;
    private double subTotal;

    public modelPurchaseItems() {
    }

    public modelPurchaseItems(String name, double unitcost, int qty, double subTotal) {
        this.name = name;
        this.unitcost = unitcost;
        this.qty = qty;
        this.subTotal = subTotal;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitcost() {
        return unitcost;
    }

    public void setUnitcost(double unitcost) {
        this.unitcost = unitcost;
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
