package com.app.FirstApp.domain.Entity;

import lombok.Data;


public class DataAddSf {
    private String email;
    private String name;

    public DataAddSf() {
    }

    public DataAddSf(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
