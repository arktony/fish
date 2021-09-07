package org.ark.product;


public class modelProduct {
    
    private String id;
    private String name;
    private String kgfrom;
    private String kgto;
    private double buyingPrice;
    private double sellingPrice;

    public modelProduct() {
    }

    public modelProduct(String id, String name, String kgfrom, String kgto, double buyingPrice, double sellingPrice) {
        this.id = id;
        this.name = name;
        this.kgfrom = kgfrom;
        this.kgto = kgto;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
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

    public String getKgfrom() {
        return kgfrom;
    }

    public void setKgfrom(String kgfrom) {
        this.kgfrom = kgfrom;
    }

    public String getKgto() {
        return kgto;
    }

    public void setKgto(String kgto) {
        this.kgto = kgto;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    
    
}
