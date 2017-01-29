package br.edu.ufam.icomp.taprental.model;

import java.io.Serializable;

/**
 * Created by gabri on 28/01/2017.
 */


public class Employee implements Serializable {
    private int id;
    private String name;

    public Employee() {
        this.id = -1;
    }

    public Employee(String name) {
        this();
        this.name = name;
    }

    public Employee(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Employee.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Employee other = (Employee) obj;

        return this.id == other.id;
    }
}
