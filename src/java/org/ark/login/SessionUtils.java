package org.ark.login;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils implements Serializable {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
    
    

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userId");
        } else {
            return null;
        }
    }
    
    
    public static String getEmail() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("email");
        } else {
            return null;
        }
    }


    public static String getBusinessId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("businessId");
        } else {
            return null;
        }
    }
    
    public static String getYear() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("year");
        } else {
            return null;
        }
    }
    
    
   

    public static String getbusinessName() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("businessName");
        } else {
            return null;
        }
    }
    
}
