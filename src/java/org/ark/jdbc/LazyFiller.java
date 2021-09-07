package org.ark.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author fumes
 */
public class LazyFiller implements Serializable {

    public ArrayList<String> FillArrayList(ArrayList<ArrayList<String>> container, int position, String filter) {

        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < container.size(); i++) {

            if ("".equals(filter)) {
                temp.add(container.get(i).get(position));
            } else if (container.get(i).get(position).equals(filter)) {
                temp.add(container.get(i).get(position));
            }
        }

        return temp;
    }

    public ArrayList<String> FillArrayListWithQuery(String query, int position, String filter) {
        reportGeneralSelect rgs = new reportGeneralSelect();
        System.out.println(query);
        ArrayList<ArrayList<String>> container = rgs.reportSelect(query);
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < container.size(); i++) {

            if ("".equals(filter)) {
                temp.add(container.get(i).get(position));
            } else if (container.get(i).get(position).equals(filter)) {
                temp.add(container.get(i).get(position));
            }
        }

        return temp;
    }

    public ArrayList<String> getSalutation() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Mr.");
        temp.add("Mrs.");
        temp.add("Ms.");
        temp.add("Dr.");
        temp.add("Prof.");

        return temp;
    }

    public ArrayList<String> getResidenceOwnershipStatus() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Rented");
        temp.add("Leased");
        temp.add("Owned");

        return temp;
    }

    public ArrayList<String> getEmploymentStatus() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Employed");
        temp.add("Self Employed");
        temp.add("UnEmployed");

        return temp;
    }

    public ArrayList<String> getIDType() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("National");
        temp.add("PassPort");
        temp.add("Employment");
        temp.add("Student");

        return temp;
    }

    public ArrayList<String> getGender() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Male");
        temp.add("Female");
        temp.add("Others");

        return temp;
    }

    public ArrayList<String> getMaritalStatus() {

        ArrayList<String> temp = new ArrayList<>();
        temp.add("Married");
        temp.add("Single");
        temp.add("Widowed");
        temp.add("Divorced");
        temp.add("Separated");
        temp.add("Others");

        return temp;
    }

    public static String getDate() {

        Calendar dateInAttendance = new GregorianCalendar();

        int FlatDays = 0;
        int TotalDays = 0;
        int ExcessDays = 0;
        double Amount = 0d;
        double FlatRate = 0d;

        int year = dateInAttendance.get(Calendar.YEAR);
        int month = dateInAttendance.get(Calendar.MONTH) + 1;
        int dayOfMonth = dateInAttendance.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
        int minute = dateInAttendance.get(Calendar.MINUTE);
        int second = dateInAttendance.get(Calendar.SECOND);
        int hourOfDay = dateInAttendance.get(Calendar.HOUR_OF_DAY); // 24 hour clock

        String datetoday = year + "-" + month + "-" + dayOfMonth;

        return datetoday;
    }

    public static String hashStrings(String original) {

        String hashed = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
            
            hashed=bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LazyFiller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hashed;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public static String RandomNumberGenerator(int length) {

        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);

        return generatedString;
    }
}
