package br.edu.ufam.icomp.taprental.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabri on 28/01/2017.
 */


public class Rental implements Serializable {
    private int id;
    private Employee employeeRent;
    private Employee employeeRestitution;
    private Customer customer;
    private Product product;
    private boolean wasDeveloped;
    private String rentDate;

    public Rental() {
        this.id = -1;
        this.wasDeveloped = false;
    }

    public Rental(int id) {
        this.id = id;
    }

    public Rental(Employee employeeRent, Customer customer, Product product) {
        this();
        this.employeeRent = employeeRent;
        this.customer = customer;
        this.product = product;
        this.rentDate = new SimpleDateFormat().format(new Date());
    }

    public Rental(int id, Employee employeeRent, Customer customer, Product product, String date) {
        this(employeeRent, customer, product);
        this.id = id;
        this.rentDate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployeeRent() {
        return employeeRent;
    }

    public void setEmployeeRent(Employee employeeRent) {
        this.employeeRent = employeeRent;
    }

    public Employee getEmployeeRestitution() {
        return employeeRestitution;
    }

    public void setEmployeeRestitution(Employee employeeRestitution) {
        this.employeeRestitution = employeeRestitution;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isWasDeveloped() {
        return wasDeveloped;
    }

    public void setWasDeveloped(boolean wasDeveloped) {
        this.wasDeveloped = wasDeveloped;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }
}
