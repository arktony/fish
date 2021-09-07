package org.ark.product;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.ark.jdbc.reportGeneralSelect;
import org.ark.login.SessionUtils;
import org.ark.messages.glow;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class Product {

    reportGeneralSelect rgs = new reportGeneralSelect();

    private String name = "";
    private String kgfrom = "";
    private String kgto = "";
    private double buyingPrice;
    private double sellingPrice;
    private String status = "ACTIVE";

    private ArrayList<modelProduct> products = new ArrayList<>();
    private modelProduct selectedProduct;

    @PostConstruct
    public void init() {

        populateProducts();

    }

    public void populateProducts() {

        products.clear();
        ArrayList<ArrayList<String>> getProducts;
        getProducts = rgs.reportSelect("SELECT id,name,kgfrom,kgto,buying,selling FROM items WHERE business_id='" + SessionUtils.getBusinessId() + "' AND status='ACTIVE'");
        
        System.out.println(getProducts);
        
        
            
            for (int i = 0; i < getProducts.size(); i++) {

                products.add(new modelProduct(getProducts.get(i).get(0), getProducts.get(i).get(1), getProducts.get(i).get(2), getProducts.get(i).get(3), Double.parseDouble(getProducts.get(i).get(4)), Double.parseDouble(getProducts.get(i).get(5))));

            }

    }

    public void onRowEdit(RowEditEvent event) {

        ArrayList<ArrayList<String>> checkName = rgs.reportSelect("select id from items where name='" + ((modelProduct) event.getObject()).getName() + "' and id!='" + ((modelProduct) event.getObject()).getId() + "'");

        if (checkName.isEmpty()) {

            String qry = "update items set name='" + ((modelProduct) event.getObject()).getName() + "',"
                    + " kgfrom='" + ((modelProduct) event.getObject()).getKgfrom() + "', "
                    + " kgto='" + ((modelProduct) event.getObject()).getKgfrom() + "', "
                    + " buying='" + ((modelProduct) event.getObject()).getBuyingPrice() + "', "
                    + " selling='" + ((modelProduct) event.getObject()).getSellingPrice() + "' WHERE id='" + ((modelProduct) event.getObject()).getId() + "'";

            if (rgs.reportInsert(qry)) {

                glow.successGlowMessage("Product successfully updated");


            } else {

                glow.errorGlowMessage("Something went wrong product not saved. Please try again.");

            }
        }

    }

    public void onRowCancel(RowEditEvent event) {

        if (rgs.reportInsert("delete from items where id='" + ((modelProduct) event.getObject()).getId() + "'")) {

            glow.successGlowMessage("Product deleted successfully");

            populateProducts();

        } else {

            glow.errorGlowMessage("Something went wrong product not deleted. Please try again.");
        }
    }

    public void saveProduct() {
        if (!name.isEmpty() && !kgfrom.isEmpty() && !kgto.isEmpty() && !"".equals(buyingPrice) && !"".equals(sellingPrice)) {

            ArrayList<ArrayList<String>> check = rgs.reportSelect("SELECT id FROM items WHERE name='" + name + "' ");
            if (check.isEmpty()) {

                if (rgs.reportInsert("insert into items(name,kgfrom,kgto,buying,selling,status,business_id) values('" + name + "','" + kgfrom + "','" + kgto + "','" + buyingPrice + "','" + sellingPrice + "','" + status + "','" + SessionUtils.getBusinessId() + "')")) {

                    glow.successGlowMessage("Product successfully added");

                    name = kgfrom = kgto = "";
                    buyingPrice = sellingPrice = 0;


                } else {
                    glow.errorGlowMessage("Something went wrong product not saved. Please try again.");
                }
            } else {
                glow.errorGlowMessage("Product with same details already exists.");
            }
        } else {
            glow.warningGlowMessage("All fields are mandatory");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKgfrom() {
        return kgfrom;
    }

    public void setKgfrom(String kgfrom) {
        this.kgfrom = kgfrom;
    }

    public String getKgto() {
        return kgto;
    }

    public void setKgto(String kgto) {
        this.kgto = kgto;
    }

    public ArrayList<modelProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<modelProduct> products) {
        this.products = products;
    }

    public modelProduct getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(modelProduct selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

}
