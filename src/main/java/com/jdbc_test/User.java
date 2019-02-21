package com.jdbc_test;

import com.jdbc_test.annotations.DBColumn;
import com.jdbc_test.annotations.DBTable;

@DBTable(name = "users")
public class User {

    @DBColumn(name = "id")
    private String id;

    @DBColumn(name = "name")
    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
