package com.jdbc_test;

import com.jdbc_test.annotations.DBColumn;
import com.jdbc_test.annotations.DBTable;

@DBTable(name = "cars")
public class Car {

    @DBColumn(name = "color")
    private String color;

    public Car() {
    }

    public Car(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                '}';
    }
}
