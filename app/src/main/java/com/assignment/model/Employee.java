package com.assignment.model;

import android.graphics.Bitmap;

/**
 * Created by prabhu on 24/1/18.
 */

public class Employee {
    private int id;
    private String name;
    private String age;
    private String address;

    public Employee(int id, String name, String age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
