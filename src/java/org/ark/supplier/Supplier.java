package org.ark.supplier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.login.SessionUtils;
import org.ark.messages.glow;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class Supplier {
    
    reportGeneralSelect rgs = new reportGeneralSelect();
    private boolean disabledLoanBtn = true;
    //Supplier Data
    
    private String firstName="";
    private String lastName="";
    private String phone="";
    private String email="";
    private String nin="";
    private String address="";
    private String status="ACTIVE";
    
    private ArrayList<modelSupplier> suppliers = new ArrayList<>();
    private modelSupplier selectedSupplier;
    
    //All loan values
    private Double amount=0d;
    private Date loanDate;
    private int term =0;
    private String item="";
    private String notes="";
    private String loanStatus="PENDING";
    SimpleDateFormat simpleformater = new SimpleDateFormat("yyyy-MM-dd");
    
    @PostConstruct
    public void init() {
        populateSuppliers();
    }
    
    public void populateSuppliers() {
        
        suppliers.clear();

        ArrayList<ArrayList<String>> getSupplier = rgs.reportSelect("select id,firstName,lastName,phone,email,nin,address from suppliers where business_id='" + SessionUtils.getBusinessId() + "'");
        
        System.out.println(getSupplier+ " Supplier Details");

          for (int i = 0; i < getSupplier.size(); i++) {
            suppliers.add(new modelSupplier(
                    
                    getSupplier.get(i).get(0), 
                    getSupplier.get(i).get(1).toUpperCase(),
                    getSupplier.get(i).get(2).toUpperCase(),
                    getSupplier.get(i).get(3), 
                    getSupplier.get(i).get(4), 
                    getSupplier.get(i).get(5).toUpperCase(),
                    getSupplier.get(i).get(6)
                    
            ));
        }
    }
    
    //Giving Loans to Suppliers
    public void handleLoanButton(){
        if(selectedSupplier.getId() !=null){
            disabledLoanBtn =false;
        }
    }
    
    public void giveLoanToSupplier(){
        
        if(selectedSupplier.getId() !=null){
            disabledLoanBtn =false;
            
            if(loanDate !=null && amount !=null && !item.isEmpty() && term !=0){
                
                
                if(rgs.reportInsert("insert into supplier_loans(amount,loanDate,term,item,notes,status,suppliers_id,business_id) values('"+amount+"','"+simpleformater.format(loanDate)+"','"+term+"','"+item+"','"+notes+"','"+loanStatus+"','"+selectedSupplier.getId()+"','"+SessionUtils.getBusinessId()+"')")){
                    
                    glow.successGlowMessage("Loan added Sucessfully.");
                }
                
            }else{
                
                glow.errorGlowMessage("All fields are Mandatory");
            }
            
        }else{
            
            glow.errorGlowMessage("You must select a supplier from table. ");
        }
    }
    
    public void onRowEdit(RowEditEvent event) {

        ArrayList<ArrayList<String>> checkId = rgs.reportSelect("select id from suppliers where name='" + ((modelSupplier) event.getObject()).getFirstName() + "' and id!='" + ((modelSupplier) event.getObject()).getId() + "'");

        if (checkId.isEmpty()) {

            String qry = "update suppliers set firstName='" + ((modelSupplier) event.getObject()).getFirstName() + "',"
                    + " lastName='" + ((modelSupplier) event.getObject()).getLastName() + "', "
                    + " phone='" + ((modelSupplier) event.getObject()).getPhone() + "', "
                    + " email='" + ((modelSupplier) event.getObject()).getEmail() + "', "
                    + " nin='" + ((modelSupplier) event.getObject()).getNin() + "',"
                    + "address='"+((modelSupplier) event.getObject()).getAddress()+"' WHERE id='" + ((modelSupplier) event.getObject()).getId() + "'";

            if (rgs.reportInsert(qry)) {

                glow.successGlowMessage("Supplier successfully updated");


            } else {

                glow.errorGlowMessage("Something went wrong!!!. Supplier not Updated. Please try again.");

            }
        }

    }

    public void onRowCancel(RowEditEvent event) {

        if (rgs.reportInsert("delete from suppliers where id='" + ((modelSupplier) event.getObject()).getId() + "'")) {

            glow.successGlowMessage("Product deleted successfully");
            
            populateSuppliers();
            
        } else {

            glow.errorGlowMessage("Something went wrong!!. Supplier not deleted. Please try again.");
        }
    }
    
    public void saveSupplier() {
        
        if(!"".equals(firstName)&& !"".equals(lastName)&& !"".equals(phone)){
            
             ArrayList<ArrayList<String>> checkExist = rgs.reportSelect("SELECT id FROM suppliers WHERE firstName='" + firstName + "' AND phone='"+phone+"' ");
             
             if(checkExist.isEmpty()){
                
                 if(rgs.reportInsert("insert into suppliers(firstName,lastName,phone,email,nin,address,status,business_id) "
                         + "values('"+firstName+"','"+lastName+"','"+phone+"','"+email+"','"+nin+"','"+address+"','"+status+"','"+SessionUtils.getBusinessId()+"')")){
                     glow.successGlowMessage("Product successfully added");
                     
                     firstName=lastName=phone=email=nin=address=status="";
                     
                     populateSuppliers();
                 }else{
                     
                     glow.errorGlowMessage("Something went wrong Supplier not saved. Please try again.");
                 }
             }else{
                 
                 glow.errorGlowMessage("Supplier with same details already exists.");
             }
        }else{
            
            glow.warningGlowMessage("All fields marked with (*) are mandatory");
            
        }
        
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<modelSupplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<modelSupplier> suppliers) {
        this.suppliers = suppliers;
    }

    public modelSupplier getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(modelSupplier selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public boolean isDisabledLoanBtn() {
        return disabledLoanBtn;
    }

    public void setDisabledLoanBtn(boolean disabledLoanBtn) {
        this.disabledLoanBtn = disabledLoanBtn;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
    
    
}
