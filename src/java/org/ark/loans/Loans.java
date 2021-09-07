package org.ark.loans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.login.SessionUtils;
import org.ark.messages.glow;

@ManagedBean
@ViewScoped
public class Loans {
    
    reportGeneralSelect rgs = new reportGeneralSelect();
    private boolean disabledApproveBtn = true;
    
    //All loan values
    private Double amount=0d;
    private Date loanDate;
    private int term =0;
    private String item="";
    private String notes="";
    private String loanStatus="PENDING";
    private String loanMajorName="";
    private String groupLabelName="";
    
    SimpleDateFormat simpleformater = new SimpleDateFormat("yyyy-MM-dd");
    
    private ArrayList<modelLoans> getAllLoansContainer = new ArrayList<>();
    private modelLoans selectedLoan;
    private String selectedLoanGroup="";
    
    @PostConstruct
    public void init() {
        
        getAllLoans();
        
    }
    
    public void getAllLoans(){
        if(!selectedLoanGroup.equals("") && !selectedLoanGroup.equals("0")){
            if(selectedLoanGroup.equals("1")){
                groupLabelName="Supplier Name";
                getAllLoansContainer.clear();
                ArrayList<ArrayList<String>> getLoans = rgs.reportSelect("select * from supplier_loans where business_id='" + SessionUtils.getBusinessId() + "'");
               
                for (int i = 0; i < getLoans.size(); i++) {
                    ArrayList<ArrayList<String>> name =rgs.reportSelect("select firstName,lastName from suppliers where id='"+getLoans.get(i).get(8)+"'");
                    for (int j = 0; j < name.size(); j++) {
                        loanMajorName = name.get(j).get(0) +" "+ name.get(j).get(1);
                    }
                    getAllLoansContainer.add(new modelLoans(
                            getLoans.get(i).get(0),
                            getLoans.get(i).get(1),
                            getLoans.get(i).get(2),
                            getLoans.get(i).get(3),
                            getLoans.get(i).get(4),
                            getLoans.get(i).get(5),
                            getLoans.get(i).get(6),
                            loanMajorName
                    ));
                    
                }
            }else{
                groupLabelName="Customer Name";
                getAllLoansContainer.clear();
                ArrayList<ArrayList<String>> getLoans2 = rgs.reportSelect("select * from customer_loans where business_id='" + SessionUtils.getBusinessId() + "'");
                for (int i = 0; i < getLoans2.size(); i++) {
                    loanMajorName=rgs.reportSelectxx("select fullName from customers where id='"+getLoans2.get(i).get(8)+"'");
                    getAllLoansContainer.add(new modelLoans(
                            getLoans2.get(i).get(0),
                            getLoans2.get(i).get(1),
                            getLoans2.get(i).get(2),
                            getLoans2.get(i).get(3),
                            getLoans2.get(i).get(4),
                            getLoans2.get(i).get(5),
                            getLoans2.get(i).get(6),
                            loanMajorName
                    ));
                    
                }
                
            }
        }else{
            
            glow.errorGlowMessage("You Must Select a Loan Group");
            
        }
    }
    
    //Handle Approve Loan Button
    
    public void handleApproveBtn(){
        if(selectedLoan.getId() !=null){
        disabledApproveBtn=false;
        }
    }
    
    public void approveLoan(){
        if(selectedLoan.getId() !=null && !selectedLoanGroup.equals("") && !selectedLoanGroup.equals("0")){
            
            if(selectedLoanGroup.equals("1")){
                if(rgs.reportInsert("update supplier_loans set status='APPROVED' where id='"+selectedLoan.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                    glow.successGlowMessage("Loan Approved");
                    getAllLoans();
                }else{
                    glow.errorGlowMessage("Loan NOT Approve!. Something went wrong.");
                }
            }else if(selectedLoanGroup.equals("2")){
                if(rgs.reportInsert("update customer_loans set status='APPROVED' where id='"+selectedLoan.getId()+"' AND business_id='"+SessionUtils.getBusinessId()+"'")){
                    glow.successGlowMessage("Loan Approved");
                    getAllLoans();
                }else{
                    glow.errorGlowMessage("Loan NOT Approve!. Something went wrong.");
                }
            }
            
        }
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
    public modelLoans getSelectedLoan() {
        return selectedLoan;
    }

    public void setSelectedLoan(modelLoans selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public ArrayList<modelLoans> getGetAllLoansContainer() {
        return getAllLoansContainer;
    }

    public void setGetAllLoansContainer(ArrayList<modelLoans> getAllLoansContainer) {
        this.getAllLoansContainer = getAllLoansContainer;
    }

    public String getSelectedLoanGroup() {
        return selectedLoanGroup;
    }

    public void setSelectedLoanGroup(String selectedLoanGroup) {
        this.selectedLoanGroup = selectedLoanGroup;
    }

    public String getLoanMajorName() {
        return loanMajorName;
    }

    public void setLoanMajorName(String loanMajorName) {
        this.loanMajorName = loanMajorName;
    }

    public String getGroupLabelName() {
        return groupLabelName;
    }

    public void setGroupLabelName(String groupLabelName) {
        this.groupLabelName = groupLabelName;
    }

    public boolean isDisabledApproveBtn() {
        return disabledApproveBtn;
    }

    public void setDisabledApproveBtn(boolean disabledApproveBtn) {
        this.disabledApproveBtn = disabledApproveBtn;
    }
    
}
