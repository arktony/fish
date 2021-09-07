/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ark.messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author fumes
 */
public class phoneMessages {
    
    public static void sendMessage(String Mess,String phoneNo){
    
        try {
            
            String mes = URLEncoder.encode(Mess, "UTF-8");
            
            String rr = getString("http://smgw1.yo.co.ug:9100/sendsms?ybsacctno=1000601891&password=5uMpEg&origin=Eco_Stove&sms_content=" + mes + "&destinations=" + phoneNo + "&nostore=1");
            
            if (rr.endsWith("=OK")) {
                
                System.out.println("Message Sent-----------");
            } else {
                System.out.println("Message failed*************");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(phoneMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getString(String url) {

        String data = "";
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                //System.out.println(line);
                data = line;
            }
        } catch (IOException ex) {
            Logger.getLogger(phoneMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

}
