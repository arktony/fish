package org.ark.sales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.login.SessionUtils;
import org.ark.messages.glow;

@ManagedBean
@ViewScoped
public class Sales {

    reportGeneralSelect rgs = new reportGeneralSelect();

    private int saleNo = 1;
    private Double amt = 0d;
    private Double amtPaid = 0d;
    private String customer;
    private Date sdate;
    private String status = "PENDING";
    private Double balance = 0d;
    private Double tTotalAmount = 0d;
    private Double tAmountPaid = 0d;
    private Double tBalance = 0d;

    //Sales items variables
    private String name = "";
    private Double sellPrice = 0d;
    private int qty = 0;
    private Double subTotal = 0d;

    //Other valiables
    private boolean offerLoanOnSale = false;
    private boolean disableApproveBtn = true;
    private boolean disableCancelBtn = true;
    private boolean disableDeleteBtn = true;

    private ArrayList<modelSalesItems> newSalesItems = new ArrayList<>();
    SimpleDateFormat simpleformater = new SimpleDateFormat("yyyy-MM-dd");

    private ArrayList<modelSales> saleData = new ArrayList<>();
    private modelSales selectedSale;

    @PostConstruct
    public void init() {
        populateSales();
    }

    public void populateSales() {

        saleData.clear();

        ArrayList<ArrayList<String>> selectSales = rgs.reportSelect("select * from sales where business_id='" + SessionUtils.getBusinessId() + "' ORDER BY id ASC");
        String truCustomerName = "";

        for (int i = 0; i < selectSales.size(); i++) {

            ArrayList<ArrayList<String>> CustomerName = rgs.reportSelect("select * from customers where id='" + selectSales.get(i).get(4) + "'");

            for (int j = 0; j < CustomerName.size(); j++) {
                truCustomerName = CustomerName.get(j).get(1);
            }
            balance = Double.parseDouble(selectSales.get(i).get(2)) - Double.parseDouble(selectSales.get(i).get(3));
            tTotalAmount += Double.parseDouble(selectSales.get(i).get(2));
            tAmountPaid += Double.parseDouble(selectSales.get(i).get(3));
            tBalance += balance;
            saleData.add(new modelSales(selectSales.get(i).get(0), selectSales.get(i).get(1), selectSales.get(i).get(2), selectSales.get(i).get(3), String.valueOf(balance), truCustomerName.toUpperCase(), selectSales.get(i).get(5), selectSales.get(i).get(6)));
        }
    }

    public List<String> getClientz(String query) {

        ArrayList<ArrayList<String>> getClients = rgs.reportSelect("select id,fullName from customers where business_id='" + SessionUtils.getBusinessId() + "'");

        List<String> selectClient = new ArrayList<>();

        for (int i = 0; i < getClients.size(); i++) {
            selectClient.add(getClients.get(i).get(1));
        }

        return selectClient;
    }

    public List<String> getItems(String query) {

        ArrayList<ArrayList<String>> loadItems = rgs.reportSelect("select id,name,selling from items where business_id='" + SessionUtils.getBusinessId() + "'");

        List<String> allItems = new ArrayList<>();

        for (int i = 0; i < loadItems.size(); i++) {

            allItems.add(loadItems.get(i).get(1));
        }

        return allItems;
    }

    public void setSellingPrice() {

        String loadPriceByName = rgs.reportSelectxx("select selling from items where name='" + name + "'");
        sellPrice = Double.parseDouble(loadPriceByName);

    }
    
    public void enableApproveBtn(){
        if(!selectedSale.getId().isEmpty()){
            disableApproveBtn =false;
            disableCancelBtn =false;
            disableDeleteBtn =false;
        }
    }
    

    public void populateNewSaleItems() {

        if (qty == 0 && sellPrice.equals(0)) {
        } else {

            subTotal = sellPrice * qty;
            newSalesItems.add(new modelSalesItems(name, sellPrice, qty, subTotal));
            amt = amt + subTotal;
            sellPrice = 0d;
            qty = 0;
        }

    }

    public void dePopulateNewSaleItem() {
        newSalesItems.clear();
        amt = 0d;
        amtPaid = 0d;
        balance = 0d;
    }

    public void calculateBalance() {
        balance = amt - amtPaid;
    }
    
    public void approveSale(){
        if(!selectedSale.getId().isEmpty()){
            
            if(rgs.reportInsert("update sales set status='APPROVED' where id='"+selectedSale.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                glow.successGlowMessage("Sale Approved successfully.");
                populateSales();
            }else{
                
                glow.errorGlowMessage("Something went wrong. Sale NOT Approved. Please try again");
            }
        
        }
    }
    
    public void cancelSale(){
        if(!selectedSale.getId().isEmpty()){
            
            if(rgs.reportInsert("update sales set status='CANCELED' where id='"+selectedSale.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                glow.successGlowMessage("Sale successfully Canceled");
                populateSales();
            }else{
                
                glow.errorGlowMessage("Something went wrong. Sale NOT Canceled. Please try again");
            }
        
        }
    }

    public void saveSale() {
        Random rand = new Random();

        if (!customer.isEmpty() && sdate != null && !"".equals(name) && !sellPrice.equals(0)) {

            String getCustomerId = rgs.reportSelectxx("select id from customers where fullName='" + customer + "'");

            saleNo = rand.nextInt((10000 - 100) + 1) + 100;
            balance = amt - amtPaid;

            ArrayList<String> result = new ArrayList<>();

            for (int i = 0; i < newSalesItems.size(); i++) {

                result.add(newSalesItems.get(i).getName());
                result.add(String.valueOf(newSalesItems.get(i).getSellPrice()));
                result.add(String.valueOf(newSalesItems.get(i).getQty()));
                result.add(String.valueOf(newSalesItems.get(i).getSubTotal()));
            }
            
                ArrayList<String> qrys = new ArrayList<>();
                qrys.add("insert into sales(sNum,amt,amtPaid,customer,sdate,status,user_id,business_id) values('" + saleNo + "','" + amt + "','" + amtPaid + "','" + Integer.parseInt(getCustomerId) + "','" + simpleformater.format(sdate) + "','" + status + "','" + SessionUtils.getUserId() + "','" + SessionUtils.getBusinessId() + "')");
                qrys.add("SET @id=(select id from sales where sNum='" + saleNo + "')");
                qrys.add("insert into sale_items(name,sellPrice,qty,subTotal,sales_id) values('" + result.get(0) + "','" + result.get(1) + "','" + result.get(2) + "','" + result.get(3) + "',@id)");
                if (balance != 0 && balance > 0) {
                    qrys.add("insert into customer_loans(amount,loanDate,item,status,customers_id,business_id) values('" + balance + "','" + simpleformater.format(sdate) + "','CREDIT ON SALE','PENDING','" + Integer.parseInt(getCustomerId) + "','" + SessionUtils.getBusinessId() + "')");
                    glow.successGlowMessage("You have given Balance of  " + balance + " as default loan to customer" + customer + ".");
                }
                if (rgs.reportInsertTransaction(qrys)) {
                    
                    glow.successGlowMessage("Sale has been created successfully.");
                    populateSales();

                } else {

                    glow.errorGlowMessage("Something went wrong. Sale NOT save");

                }

            
        }
    }

    public int getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(int saleNo) {
        this.saleNo = saleNo;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(Double amtPaid) {
        this.amtPaid = amtPaid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public ArrayList<modelSalesItems> getNewSalesItems() {
        return newSalesItems;
    }

    public void setNewSalesItems(ArrayList<modelSalesItems> newSalesItems) {
        this.newSalesItems = newSalesItems;
    }

    public ArrayList<modelSales> getSaleData() {
        return saleData;
    }

    public void setSaleData(ArrayList<modelSales> saleData) {
        this.saleData = saleData;
    }

    public modelSales getSelectedSale() {
        return selectedSale;
    }

    public void setSelectedSale(modelSales selectedSale) {
        this.selectedSale = selectedSale;
    }

    public Double gettTotalAmount() {
        return tTotalAmount;
    }

    public void settTotalAmount(Double tTotalAmount) {
        this.tTotalAmount = tTotalAmount;
    }

    public Double gettAmountPaid() {
        return tAmountPaid;
    }

    public void settAmountPaid(Double tAmountPaid) {
        this.tAmountPaid = tAmountPaid;
    }

    public Double gettBalance() {
        return tBalance;
    }

    public void settBalance(Double tBalance) {
        this.tBalance = tBalance;
    }

    public boolean isOfferLoanOnSale() {
        return offerLoanOnSale;
    }

    public void setOfferLoanOnSale(boolean offerLoanOnSale) {
        this.offerLoanOnSale = offerLoanOnSale;
    }

    public boolean isDisableApproveBtn() {
        return disableApproveBtn;
    }

    public void setDisableApproveBtn(boolean disableApproveBtn) {
        this.disableApproveBtn = disableApproveBtn;
    }

    public boolean isDisableCancelBtn() {
        return disableCancelBtn;
    }

    public void setDisableCancelBtn(boolean disableCancelBtn) {
        this.disableCancelBtn = disableCancelBtn;
    }

    public boolean isDisableDeleteBtn() {
        return disableDeleteBtn;
    }

    public void setDisableDeleteBtn(boolean disableDeleteBtn) {
        this.disableDeleteBtn = disableDeleteBtn;
    }

}
