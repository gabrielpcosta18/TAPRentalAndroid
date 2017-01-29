package br.edu.ufam.icomp.taprental.model;

import java.io.Serializable;

/**
 * Created by gabri on 28/01/2017.
 */


public class Customer implements Serializable {
    private int id;
    private String name;

    public Customer() {
        this.id = -1;
    }

    public Customer(String name) {
        this();
        this.name = name;
    }

    public Customer(int id, String name) {
        this(name);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Customer.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Customer other = (Customer) obj;

        return this.id == other.id;
    }
}
