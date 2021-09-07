
package org.ark.loans;

public class modelLoans {
    private String id;
    private String amount;
    private String loanDate;
    private String term;
    private String item;
    private String notes;
    private String loanStatus;
    private String mainId;

    public modelLoans() {
    }

    public modelLoans(String id, String amount, String loanDate, String term, String item, String notes, String loanStatus, String mainId) {
        this.id = id;
        this.amount = amount;
        this.loanDate = loanDate;
        this.term = term;
        this.item = item;
        this.notes = notes;
        this.loanStatus = loanStatus;
        this.mainId = mainId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
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

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

        
    
}
