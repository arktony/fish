/*
 *Ark Programers.
 */
package org.ark.login;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.messages.glow;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

    reportGeneralSelect rgs = new reportGeneralSelect();

    private String email = "";
    private String password = "";
    private String fullName = "";
    private String businessname = "";
    private String businessId = "";
    private String usersId = "";
    private int sessionTimeout = 1800000;
    private int year=0;

    //validate login
    public String validateUsernamePassword() {
        try {
            ArrayList<ArrayList<String>> getCredentials = rgs.reportSelect("select id,firstName,lastName,email,phone,business_id from user where email='" + email + "' and password='" + password + "' and status='ACTIVE'");

            int bizId = Integer.parseInt(getCredentials.get(0).get(5));

            if (!getCredentials.isEmpty()) {

                ArrayList<ArrayList<String>> getbusinessdata = rgs.reportSelect("select * from business where id='" + bizId + "'");
                Calendar cal =Calendar.getInstance();
                if (!getbusinessdata.isEmpty()) {
                    if ("ACTIVE".equals(getbusinessdata.get(0).get(4))) {
                        year =cal.get(Calendar.YEAR);
                        HttpSession session = SessionUtils.getSession();

                        session.setAttribute("userId", getCredentials.get(0).get(0));
                        session.setAttribute("businessId", getCredentials.get(0).get(5));
                        session.setAttribute("firstName", getCredentials.get(0).get(1));
                        session.setAttribute("lastName", getCredentials.get(0).get(2));
                        session.setAttribute("businessName", getbusinessdata.get(0).get(1));
                        session.setAttribute("year", year);

                        System.out.println(getbusinessdata.get(0).get(1));

                        session.setAttribute("email", getCredentials.get(0).get(3));

                        fullName = getCredentials.get(0).get(1) + " " + getCredentials.get(0).get(2);

                        System.out.println(fullName + "Me right");

                        businessname = SessionUtils.getbusinessName().toUpperCase();
                        businessId = SessionUtils.getBusinessId();
                        usersId = SessionUtils.getUserId();

                        return "index";
                    } else {

                        return "index";
                    }
                } else {
                    HttpSession session = SessionUtils.getSession();
                    session.invalidate();

                    return "login";
                }
            } else {
                glow.errorGlowMessage("Incorrect Username and Passowrd. Please try again");

                return "login";
            }
        } catch (Exception e) {
            HttpSession session = SessionUtils.getSession();
            session.invalidate();

            return "login";
        }
    }

    ///getImages
    public StreamedContent getProfileImage() throws IOException, SQLException {

        HttpSession session = SessionUtils.getSession();
        if (session != null) {
            FacesContext context = FacesContext.getCurrentInstance();

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {

                String id = context.getExternalContext().getRequestParameterMap()
                        .get("pid");

                byte[] image = new profileImageDAO1().getProductImage(id);

                return new DefaultStreamedContent(new ByteArrayInputStream(image));
            }
        } else {
            try {

                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
                FacesContext.getCurrentInstance().responseComplete();

            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //on idle
    public void onIdle() {

        try {
            HttpSession session = SessionUtils.getSession();
            session.invalidate();

            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }

    public reportGeneralSelect getRgs() {
        return rgs;
    }

    public void setRgs(reportGeneralSelect rgs) {
        this.rgs = rgs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
