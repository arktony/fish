/*
 Ark Programmers.
 */
package org.ark.login;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SessionManager implements HttpSessionBindingListener {

    private String name;

    public SessionManager(String name) {
        this.name = name;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        SessionManager user = (SessionManager)event.getValue();
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        SessionManager user = (SessionManager)event.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
