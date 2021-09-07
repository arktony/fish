package org.ark.purchases;


public class modelPurchaseItemsFetch {
    private String id;
    private String name;
    private double unitcost;
    private int qty;
    private double subTotal;

    public modelPurchaseItemsFetch() {
    }

    public modelPurchaseItemsFetch(String id, String name, double unitcost, int qty, double subTotal) {
        this.id = id;
        this.name = name;
        this.unitcost = unitcost;
        this.qty = qty;
        this.subTotal = subTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
