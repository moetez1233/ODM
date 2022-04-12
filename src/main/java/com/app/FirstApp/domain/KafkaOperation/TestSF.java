package com.app.FirstApp.domain.KafkaOperation;

import org.springframework.stereotype.Component;

@Component
public class TestSF {
    private String nameFile;
    private String PathFile;

    public TestSF() {
    }

    public TestSF(String nameFile, String pathFile) {
        this.nameFile = nameFile;
        PathFile = pathFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getPathFile() {
        return PathFile;
    }

    public void setPathFile(String pathFile) {
        PathFile = pathFile;
    }

    @Override
    public String toString() {
        return nameFile+","+PathFile;
    }
}
