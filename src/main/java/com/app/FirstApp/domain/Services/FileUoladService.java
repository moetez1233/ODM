package com.app.FirstApp.domain.Services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUoladService {
    public void FirstUpload(MultipartFile file) throws IOException {
        file.transferTo(new File("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ShipmentFiles\\"+file.getOriginalFilename()));
    }
    public void SendToKms(MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\test1\\kms"+file.getOriginalFilename()));
    }

}
