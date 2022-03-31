package com.app.FirstApp.domain.Entity;

public class DataAddHisToSf {
    private Long id;
    private String name;

    public DataAddHisToSf() {
    }

    public DataAddHisToSf(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
