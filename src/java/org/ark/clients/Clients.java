package org.ark.clients;

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
public class Clients{
    reportGeneralSelect rgs = new reportGeneralSelect();
    private boolean disabledLoanBtn = true;
    
    private String fullName="";
    private String phone="";
    private String email="";
    private String address="";
    private String status="ACTIVE";
    
    private ArrayList<modelClients> clients = new ArrayList<>();
    private modelClients selectedClient;
    
    //All loan values
    private Double amount=0d;
    private Date loanDate;
    private int term =0;
    private String item="";
    private String notes="";
    private String loanStatus="PENDING";
    SimpleDateFormat simpleformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @PostConstruct
    public void init() {
        populateClients();
    }
    
    
    public void populateClients() {
        clients.clear();
        ArrayList<ArrayList<String>> getClients = rgs.reportSelect("select * from customers where business_id='" + SessionUtils.getBusinessId() + "'");
        
          for (int i = 0; i < getClients.size(); i++) {
            clients.add(new modelClients(
                    getClients.get(i).get(0),
                    getClients.get(i).get(1).toUpperCase(),
                    getClients.get(i).get(2),
                    getClients.get(i).get(3),
                    getClients.get(i).get(4),
                    getClients.get(i).get(5)
                   
            ));
            
            System.out.println(getClients.get(i).get(3) +" Is the client email");
            System.out.println(getClients.get(i).get(4) +" Is the client address");
        }
            
    }
    
    //Giving Loans to Suppliers
    
    public void giveLoanToClient(){
        
        System.out.println(selectedClient.getId()+" This is the selected supplier");
        if(loanDate !=null && amount !=null && !item.isEmpty() && term !=0){
        
        if(selectedClient.getId() !=null){
            disabledLoanBtn =false;
            
                if(rgs.reportInsert("insert into customer_loans(amount,loanDate,term,item,notes,status,suppliers_id) values('"+amount+"','"+simpleformater.format(loanDate)+"','"+term+"','"+item+"','"+notes+"','"+loanStatus+"','"+selectedClient.getId()+"')")){
                    
                    glow.successGlowMessage("Loan added Sucessfully.");
                    
                }
                
            }else{
                
                glow.errorGlowMessage("All fields are Mandatory");
            }
            
        }else{
            
            glow.errorGlowMessage("You must select a Client from table. ");
        }
    }
    
    public void saveClient() {
        
        if(!fullName.isEmpty() && !address.isEmpty() && !phone.isEmpty()){
            
             ArrayList<ArrayList<String>> checkExist = rgs.reportSelect("SELECT id FROM customers WHERE fullName='" + fullName + "' AND phone='"+phone+"' ");
             
             if(checkExist.isEmpty()){
                
                 if(rgs.reportInsert("insert into customers(fullName,phone,email,address,status,business_id) "
                         + "values('"+fullName+"','"+phone+"','"+email+"','"+address+"','"+status+"','"+SessionUtils.getBusinessId()+"')")){
                     
                     glow.successGlowMessage("Client successfully added");
                     
                     fullName=phone=email=address=status="";
                     
                     populateClients();
                     
                 }else{
                     
                     glow.errorGlowMessage("Something went wrong Client not saved. Please try again.");
                 }
             }else{
                 
                 glow.errorGlowMessage("Client with same details already exists.");
             }
        }else{
            
            glow.warningGlowMessage("All fields marked with (*) are mandatory");
            
        }
        
    }
    
    public void onRowEdit(RowEditEvent event) {

        ArrayList<ArrayList<String>> checkId = rgs.reportSelect("select id from customers where fullName='" + ((modelClients) event.getObject()).getFullName() + "' and id!='" + ((modelClients) event.getObject()).getId() + "'");

        if (checkId.isEmpty()) {

            String qry = "update customers set "
                    + "fullName='" + ((modelClients) event.getObject()).getFullName() + "',"
                    + " phone='" + ((modelClients) event.getObject()).getPhone() + "', "
                    + " email='" + ((modelClients) event.getObject()).getEmail() + "', "
                    + "address='"+((modelClients) event.getObject()).getAddress()+"' WHERE id='" + ((modelClients) event.getObject()).getId() + "'";

            if (rgs.reportInsert(qry)) {

                glow.successGlowMessage("Client successfully updated");
                populateClients();

            } else {

                glow.errorGlowMessage("Something went wrong!!!. Client not Updated. Please try again.");

            }
        }

    }

    public void onRowCancel(RowEditEvent event) {

        if (rgs.reportInsert("delete from customers where id='" + ((modelClients) event.getObject()).getId() + "'")) {

            glow.successGlowMessage("Client deleted successfully");
            
            populateClients();
        } else {

            glow.errorGlowMessage("Something went wrong!!.Client not deleted. Please try again.");
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public ArrayList<modelClients> getClients() {
        return clients;
    }

    public void setClients(ArrayList<modelClients> clients) {
        this.clients = clients;
    }

    public modelClients getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(modelClients selectedClient) {
        this.selectedClient = selectedClient;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
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
    
}
