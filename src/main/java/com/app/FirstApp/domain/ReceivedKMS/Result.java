package com.app.FirstApp.domain.ReceivedKMS;

public class Result {
    private String nameFile;
    private String status;
    public Result() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Result(String nameFile, String status) {
        super();
        this.nameFile = nameFile;
        this.status = status;
    }
    public String getPathFIle() {
        return nameFile;
    }
    public void setNameFIle(String nameFile) {
        this.nameFile = nameFile;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return nameFile+","+status;


    }



}