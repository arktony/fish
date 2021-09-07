
package org.ark.sales;


public class modelSales {
    
    private String id;
    private String saleNo;
    private String amt;
    private String amtPaid;
    private String balance;
    private String customer;
    private String sdate;
    private String status;

    public modelSales() {
    }

    public modelSales(String id, String saleNo, String amt, String amtPaid, String balance, String customer, String sdate, String status) {
        this.id = id;
        this.saleNo = saleNo;
        this.amt = amt;
        this.amtPaid = amtPaid;
        this.balance = balance;
        this.customer = customer;
        this.sdate = sdate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
}
