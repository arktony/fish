/*
 * The Ark Computer Programmers And Animators.
 */
package org.ark.businessdata;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.messages.glow;

@SessionScoped
@ManagedBean
public class business implements Serializable{

    private reportGeneralSelect rgs = new reportGeneralSelect();
    private String id = "";
    private String name = "";
    private String email = "";
    private String phone = "";
    private String firstName = "";
    private String lastName = "";
    private String password="";
    private String status = "ACTIVE";
    private boolean superFlag= true;

    @PostConstruct
    public void init() {
    }

    public void saveBusiness() {
        if (!"".equals(name) && !"".equals(email) && !"".equals(phone)) {
            
            ArrayList<ArrayList<String>> checkBusiness = rgs.reportSelect("select id from business where email='" + email + "' AND phone='" + phone + "'");
            
            if (checkBusiness.isEmpty()) {
                
                ArrayList<String> qrys = new ArrayList<>();
                
                qrys.add("insert into business(name,email,phone,status) values('"+name+"','"+email+"','"+phone+"','"+status+"')");
                qrys.add("SET @id=(select id from business where name='" + name + "' and email='" + email + "')");
                qrys.add("insert into user(firstName,lastName,email,phone,status,password,superFlag,business_id) values('"+firstName+"','"+lastName+"','"+email+"','"+phone+"','"+status+"','"+password+"','"+superFlag+"',@id)");
                
                if(rgs.reportInsertTransaction(qrys)){
                    glow.successGlowMessage("user saved successfully");
                    name=email=phone=firstName=lastName=password="";
                }
            }else{
                glow.errorGlowMessage("Business with same details Already exists.");
            }
        }else{
            glow.warningGlowMessage("All fields are required");
        }

    }

    public reportGeneralSelect getRgs() {
        return rgs;
    }

    public void setRgs(reportGeneralSelect rgs) {
        this.rgs = rgs;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuperFlag() {
        return superFlag;
    }

    public void setSuperFlag(boolean superFlag) {
        this.superFlag = superFlag;
    }
}
