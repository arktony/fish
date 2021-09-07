/*
 * By Ark Programmers.
 */
package org.ark.businessdata;

import java.util.Date;

/**
 *
 * @author apple.inc
 */
public class modelBusiness {
    
    private int id;
    private String name;
    private String email;
    private String phone;
    private String status;
    private Date regDate;

    public modelBusiness() {
    }

    public modelBusiness(int id, String name, String email, String phone, String status, Date regDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
    
    
    
}
