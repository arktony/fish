package org.ark.purchases;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.login.SessionUtils;


public class PurchaseCard {
    DateTimeFormatter formater1 = DateTimeFormatter.ofPattern("EEE MMM dd, yyyy");
    DateTimeFormatter formater2 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df2 = new DecimalFormat("#,###");

    reportGeneralSelect rgs = new reportGeneralSelect();
    Purchases purchases = new Purchases();

    public String purchase(modelPurchases purchaseModel) {
        ArrayList<String> purchaseData = new ArrayList<>();
        purchaseData.add(purchases.SelectedPurchase());

        StringBuilder WEB = new StringBuilder();
        try {
            rgs.reportInsert("insert into errorlogs(name) values('managed to feel reach html')");
            String image = "";

            rgs.reportInsert("insert into errorlogs(name) values('managed to start html')");

            WEB.append("<html>\n"
                    + "<head>\n"
                    + "    <meta charset=\"utf-8\">\n"
                    + "    <style>\n"
                    + "    .invoice-box {\n"
                    + "        max-width: 800px;\n"
                    + "        margin: auto;\n"
                    + "        padding: 30px;\n"
                    + "        border: 1px solid #eee;\n"
                    + "        box-shadow: 0 0 10px rgba(0, 0, 0, .15);\n"
                    + "        font-size: 16px;\n"
                    + "        line-height: 24px;\n"
                    + "        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n"
                    + "        color: #555;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table {\n"
                    + "        width: 100%;\n"
                    + "        line-height: inherit;\n"
                    + "        text-align: left;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table td {\n"
                    + "        padding: 5px;\n"
                    + "        vertical-align: top;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr td:nth-child(2) {\n"
                    + "        text-align: right;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.top table td {\n"
                    + "        padding-bottom: 20px;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.top table td.title {\n"
                    + "        font-size: 45px;\n"
                    + "        line-height: 45px;\n"
                    + "        color: #333;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.information table td {\n"
                    + "        padding-bottom: 40px;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.heading td {\n"
                    + "        background: #eee;\n"
                    + "        border-bottom: 1px solid #ddd;\n"
                    + "        font-weight: bold;\n"
                    + "    }\n"
                    + "    .totalbox {\n"
                    + "        background: #eee;\n"
                    + "        border-bottom: 1px solid #ddd;\n"
                    + "        font-weight: bold;\n"
                    + "    }\n"
                    + "    .invoiceword {\n"
                    + "        font-size: 30px;\n"
                    + "        background: #eee;\n"
                    + "        border-bottom: 1px solid #ddd;\n"
                    + "        font-weight: bold;\n"
                    + "    }\n"
                    + "    .companyname {\n"
                    + "        font-size: 30px;\n"
                    + "        border-bottom: 1px solid #ddd;\n"
                    + "        font-weight: bold;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.details td {\n"
                    + "        padding-bottom: 20px;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.item td{\n"
                    + "        border-bottom: 1px solid #eee;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.item.last td {\n"
                    + "        border-bottom: none;\n"
                    + "    }\n"
                    + "    \n"
                    + "    .invoice-box table tr.total td:nth-child(2) {\n"
                    + "        border-top: 2px solid #eee;\n"
                    + "        font-weight: bold;\n"
                    + "    }\n"
                    + "    \n"
                    + "    @media only screen and (max-width: 600px) {\n"
                    + "        .invoice-box table tr.top table td {\n"
                    + "            width: 100%;\n"
                    + "            display: block;\n"
                    + "            text-align: center;\n"
                    + "        }\n"
                    + "        \n"
                    + "        .invoice-box table tr.information table td {\n"
                    + "            width: 100%;\n"
                    + "            display: block;\n"
                    + "            text-align: center;\n"
                    + "        }\n"
                    + "    }\n"
                    + "    \n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "<div class=\"invoice-box\">");

            WEB.append("<table cellpadding=\"0\" cellspacing=\"0\">");
            WEB.append("<tr class=\"top\">\n"
                    + "                <td class=\"title\">\n"
                    + "                </td>\n"
                    + "                \n"
                    + "                <td colspan=\"4\">\n"
                    + "                    <table>\n"
                    + "                        <tr>\n"
                    + "                            <td class=\"companyname\" align=\"center\">\n"
                    + "                                " + SessionUtils.getbusinessName() + "\n"
                    + "                            </td>\n"
                    + "                        </tr>\n"
                    + "                        <tr>\n"
                    + "                            <td align=\"center\">\n"
                    + "                                " + SessionUtils.getEmail() + "\n"
                    + "                            </td>\n"
                    + "                        </tr>\n"
                    + "                    </table>\n"
                    + "                </td>\n"
                    + "</tr>");
            WEB.append("</table>");

            ////invoice and client section
            WEB.append("<table cellpadding=\"0\" cellspacing=\"0\">");

            WEB.append("<tr class=\"heading\">\n"
                    + "                <td class=\"invoiceword\" colspan=\"2\">\n"
                    + "                    TAX INVOICE\n"
                    + "                </td>\n"
                    + "                \n"
                    + "                <td colspan=\"3\">\n"
                    + "                    No." + purchaseData.get(2) + "\n"
                    + "                </td>\n"
                    + "</tr>");

            WEB.append("</table>");

            WEB.append("<table cellpadding=\"0\" cellspacing=\"0\">");

            WEB.append("<tr class=\"item last\">\n"
                    + "                            <td>\n"
                    + "                                Bill for: \n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                \n"
                    + "                            </td>\n"
                    + "                            <td align=\"left\">\n"
                    + "                                " + purchaseData.get(3)+ "<br>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                \n"
                    + "                            </td>\n"
                    + "</tr>");
            WEB.append("<tr class=\"item last\">\n"
                    + "                            <td>\n"
                    + "                                Created: \n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                \n"
                    + "                            </td>\n"
                    + "                            <td align=\"left\">\n"
                    + "                                " + simple.format(purchaseData.get(1)) + "\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                \n"
                    + "                            </td>\n"
                    + "</tr>");
            ////data table
            WEB.append("<table cellpadding=\"0\" cellspacing=\"0\">");

            WEB.append("<tr class=\"heading\">\n"
                    + "                <td>\n"
                    + "                    Purchase Details\n"
                    + "                </td>\n"
                    + "                <td>\n"
                    + "                </td>\n"
                    + "                <td colspan=\"1\" align=\"left\">\n"
                    + "                    Date\n"
                    + "                </td>\n"
                    + "                <td>\n"
                    + "                    Supplier\n"
                    + "                </td>\n"
                    + "                \n"
                    + "                <td>\n"
                    + "                    Total Amount\n"
                    + "                </td>\n"
                    + "                <td>\n"
                    + "                    Amount Paid\n"
                    + "                </td>\n"
                    + "                <td>\n"
                    + "                    Balance\n"
                    + "                </td>\n"
                    
                    + "</tr>");

            WEB.append("</table>");

        } catch (NumberFormatException ex) {
            Logger.getLogger(PurchaseCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return WEB.toString();
    }
    
}
