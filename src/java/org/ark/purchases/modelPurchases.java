package org.ark.purchases;

public class modelPurchases {
    
    private String id;
    private String purchaseNo;
    private String amt;
    private String amtPaid;
    private String supplier;
    private String pdate;
    private String status;
    private String balance;

    public modelPurchases() {
    
    }

    public modelPurchases(String id, String purchaseNo, String amt, String amtPaid, String supplier, String pdate, String status, String balance) {
        this.id = id;
        this.purchaseNo = purchaseNo;
        this.amt = amt;
        this.amtPaid = amtPaid;
        this.supplier = supplier;
        this.pdate = pdate;
        this.status = status;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(String amtPaid) {
        this.amtPaid = amtPaid;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    
    }
