package br.edu.ufam.icomp.taprental.model;

import java.io.Serializable;

/**
 * Created by gabri on 28/01/2017.
 */


public class Product implements Serializable {
    private int id;
    private String title;
    private String description;
    private String type;
    private int maxPeriodRent;
    private int totalInStock;
    private float price;
    private float productPrice;

    public Product() {
        this.id = -1;
    }

    public Product(String title, String description, String type, int maxPeriodRent, int totalInStock, float price, float productPrice) {
        this();
        this.title = title;
        this.description = description;
        this.setType(type);
        this.setMaxPeriodRent(maxPeriodRent);
        this.totalInStock = totalInStock;
        this.price = price;
        this.productPrice = productPrice;
    }

    public Product(int id, String title, String description, String type, int maxPeriodRent, int totalInStock, float price, float productPrice) {
        this(title, description, type, maxPeriodRent,totalInStock, price, productPrice);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalInStock() {
        return totalInStock;
    }

    public void setTotalInStock(int totalInStock) {
        this.totalInStock = totalInStock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float value) {
        this.price = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxPeriodRent() {
        return maxPeriodRent;
    }

    public void setMaxPeriodRent(int maxPeriodRent) {
        this.maxPeriodRent = maxPeriodRent;
    }

    public String toString() {
        return this.title + "/" + this.type + " $" + Float.toString(this.getPrice());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Product.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Product other = (Product) obj;

        return this.id == other.id;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }
}
