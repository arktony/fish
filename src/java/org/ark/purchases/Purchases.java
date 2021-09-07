package org.ark.purchases;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import org.ark.supplier.supplierModel;

@ManagedBean
@ViewScoped
public class Purchases implements Serializable{

    reportGeneralSelect rgs = new reportGeneralSelect();

    private int purchaseNo = 1;
    private Double amt = 0d;
    private Double amtPaid = 0d;
    private String supplier;
    private Date pdate;
    private String status = "PENDING";
    private Double balance = 0d;
    private Double tTotalAmount =0d;
    private Double tAmountPaid =0d;
    private Double tBalance = 0d;

    //Purchase items variables
    private String name = "";
    private Double unitcost = 0d;
    private int qty = 0;
    private Double subTotal = 0d;
    private String purchase = "";

    private supplierModel selectedSup;
    private String supName = "";
    
    private boolean disableApproveBtn = true;
    private boolean disableCancelBtn = true;
    private boolean disableDeleteBtn = true;

    private ArrayList<modelPurchaseItems> newPurchaseItems = new ArrayList<>();
    private ArrayList<modelPurchases> purchaseData = new ArrayList<>();
    private modelPurchases selectedPurchase;
    
    private ArrayList<modelPurchaseItemsFetch> purchaseItems = new ArrayList<>();

    DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat simpleformater = new SimpleDateFormat("yyyy-MM-dd HH");

    @PostConstruct
    public void init() {
        populatePurchases();
    }

    public void populatePurchases() {

        purchaseData.clear();

        ArrayList<ArrayList<String>> selectPurchases = rgs.reportSelect("select * from purchases where business_id='" + SessionUtils.getBusinessId() + "'");

        for (int i = 0; i < selectPurchases.size(); i++) {
            
            ArrayList<ArrayList<String>> SupplierNames =rgs.reportSelect("select firstName,lastName from suppliers where id='"+selectPurchases.get(i).get(4)+"'");
           
            String realSupplierNames = "";
//            String fib
            balance=Double.parseDouble(selectPurchases.get(i).get(2)) - Double.parseDouble(selectPurchases.get(i).get(3));
            
            for (int j = 0; j < SupplierNames.size(); j++) {
                
                realSupplierNames = SupplierNames.get(j).get(0)+" "+SupplierNames.get(j).get(1);
            }
            
            tTotalAmount += Double.parseDouble(selectPurchases.get(i).get(2));
            tAmountPaid +=Double.parseDouble(selectPurchases.get(i).get(3));
            tBalance +=balance;
            
            purchaseData.add(new modelPurchases(selectPurchases.get(i).get(0), selectPurchases.get(i).get(1), selectPurchases.get(i).get(2), selectPurchases.get(i).get(3), realSupplierNames, selectPurchases.get(i).get(5), selectPurchases.get(i).get(6),String.valueOf(balance)));
        }
    }

    public List<String> getSupplierz(String query) {

        ArrayList<ArrayList<String>> getSupplier = rgs.reportSelect("select id,firstName,lastName from suppliers where business_id='" + SessionUtils.getBusinessId() + "'");

        List<String> selectSup = new ArrayList<>();

        for (int i = 0; i < getSupplier.size(); i++) {

//            selectSup.add(getSupplier.get(i).get(1) +" "+getSupplier.get(i).get(2));  
            selectSup.add(getSupplier.get(i).get(1));
        }

        return selectSup;
    }

    public List<String> getItems(String query) {

        ArrayList<ArrayList<String>> loadItems = rgs.reportSelect("select id,name,buying from items where business_id='" + SessionUtils.getBusinessId() + "'");

        List<String> allItems = new ArrayList<>();

        for (int i = 0; i < loadItems.size(); i++) {

            allItems.add(loadItems.get(i).get(1));
        }

        return allItems;
    }
    
    public void purchaseCard() {

        if (selectedPurchase != null) {
            PurchaseCard purchaseCard = new PurchaseCard();
            purchase = purchaseCard.purchase(selectedPurchase);
        }
    }
    
    public void enableApproveBtn(){
        if(!selectedPurchase.getId().isEmpty()){
            disableApproveBtn =false;
            disableCancelBtn =false;
            disableDeleteBtn =false;
        }
    }
    
    public void approvePurchase(){
        
        if(!selectedPurchase.getId().isEmpty()){
            
            if(rgs.reportInsert("update purchases set status='APPROVED' where id='"+selectedPurchase.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                glow.successGlowMessage("Purchase Approved successfully.");
                populatePurchases();
                
            }else{
                
                glow.errorGlowMessage("Something went wrong. Purchase NOT Approved. Please try again");
            }
        
        }
    }
    
    public void cancePurchase(){
        
        if(!selectedPurchase.getId().isEmpty()){
            
            if(rgs.reportInsert("update purchases set status='CANCELED' where id='"+selectedPurchase.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                
                glow.successGlowMessage("Purchase successfully Canceled");
                populatePurchases();
                
            }else{
                
                glow.errorGlowMessage("Something went wrong. Purchase NOT Canceled. Please try again");
            }
        
        }
    }
    
    public void populatePurchseItemsDetails(){
        
        System.err.println(selectedPurchase.getId() + "This is ID from table.");
        
        if(!selectedPurchase.getId().isEmpty()){
           ArrayList<ArrayList<String>> getPurchaseItemsById = rgs.reportSelect("select * from purchase_items where purchases_id='"+selectedPurchase.getId()+"'");
           if(!getPurchaseItemsById.isEmpty()){
               for (int i = 0; i < getPurchaseItemsById.size(); i++) {
                   purchaseItems.add(new modelPurchaseItemsFetch(
                           getPurchaseItemsById.get(i).get(0), 
                           getPurchaseItemsById.get(i).get(1), 
                           Double.parseDouble(getPurchaseItemsById.get(i).get(2)), 
                           Integer.parseInt(getPurchaseItemsById.get(i).get(3)), 
                           Double.parseDouble(getPurchaseItemsById.get(i).get(4))
                   ));
               }
           }
           
           purchaseNo =Integer.parseInt(selectedPurchase.getPurchaseNo());
           supplier =selectedPurchase.getSupplier();
        }
    }

    
    public String SelectedPurchase(){
        ArrayList<String> pDetails= new ArrayList<>();
        if(selectedPurchase != null){
           
           pDetails.add(selectedPurchase.getId());
           pDetails.add(selectedPurchase.getPdate());
           pDetails.add(selectedPurchase.getPurchaseNo());
           pDetails.add(selectedPurchase.getSupplier());
           pDetails.add(selectedPurchase.getAmt());
           pDetails.add(selectedPurchase.getAmtPaid());
           pDetails.add(selectedPurchase.getBalance());
        }
       return pDetails.toString();
    }

    public void setBuyingPrice() {

        String loadPriceByName = rgs.reportSelectxx("select buying from items where name='" + name + "'");
        unitcost = Double.parseDouble(loadPriceByName);

    }

    public void populateNewPurchaseItems() {

        if (qty == 0 && unitcost.equals(0)) {
        } else {

            subTotal = unitcost * qty;
            newPurchaseItems.add(new modelPurchaseItems(name, unitcost, qty, subTotal));
            amt = amt + subTotal;
            unitcost = 0d;
            qty = 0;

        }

    }
    
    public void dePopulateNewPurchaseItems(){
        newPurchaseItems.clear();
        amt=0d;
        amtPaid=0d;
        balance=0d;
    }

    public void savePurchase() {
        Random rand = new Random();

        if (!supplier.isEmpty() && pdate != null && !"".equals(name) && !unitcost.equals(0)) {

            String getSupplierId = rgs.reportSelectxx("select id from suppliers where firstName='" + supplier + "'");

            purchaseNo = rand.nextInt((100000 - 100) + 1) + 100;

            ArrayList<String> result = new ArrayList<>();

            for (int i = 0; i < newPurchaseItems.size(); i++) {

                result.add(newPurchaseItems.get(i).getName());
                result.add(String.valueOf(newPurchaseItems.get(i).getUnitcost()));
                result.add(String.valueOf(newPurchaseItems.get(i).getQty()));
                result.add(String.valueOf(newPurchaseItems.get(i).getSubTotal()));
            }

            ArrayList<String> qrys = new ArrayList<>();

            qrys.add("insert into purchases(pNum,amt,amtPaid,supplier,pdate,status,user_id,business_id) values('" + purchaseNo + "','" + amt + "','" + amtPaid + "','" + Integer.parseInt(getSupplierId) + "','" + simpleformater.format(pdate) + "','" + status + "','" + SessionUtils.getUserId() + "','" + SessionUtils.getBusinessId() + "')");
            qrys.add("SET @id=(select id from purchases where pNum='" + purchaseNo + "')");
            qrys.add("insert into purchased_items(name,unitcost,qty,subTotal,purchases_id) values('" + result.get(0) + "','" + result.get(1) + "','" + result.get(2) + "','" + result.get(3) + "',@id)");

            if (rgs.reportInsertTransaction(qrys)) {

                glow.successGlowMessage("Purchase has been created successfully.");
                populatePurchases();

            } else {

                glow.errorGlowMessage("Something went wrong. Purchase NOT save");

            }

        }
    }

    public void calculateBalance() {
        balance = amt - amtPaid;
    }

    public int getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(int purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(double amtPaid) {
        this.amtPaid = amtPaid;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public supplierModel getSelectedSup() {
        return selectedSup;
    }

    public void setSelectedSup(supplierModel selectedSup) {
        this.selectedSup = selectedSup;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public ArrayList<modelPurchaseItems> getNewPurchaseItems() {
        return newPurchaseItems;
    }

    public void setNewPurchaseItems(ArrayList<modelPurchaseItems> newPurchaseItems) {
        this.newPurchaseItems = newPurchaseItems;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public ArrayList<modelPurchases> getPurchaseData() {
        return purchaseData;
    }

    public void setPurchaseData(ArrayList<modelPurchases> purchaseData) {
        this.purchaseData = purchaseData;
    }

    public modelPurchases getSelectedPurchase() {
        return selectedPurchase;
    }

    public void setSelectedPurchase(modelPurchases selectedPurchase) {
        this.selectedPurchase = selectedPurchase;
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

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
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

    public ArrayList<modelPurchaseItemsFetch> getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(ArrayList<modelPurchaseItemsFetch> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    

}
