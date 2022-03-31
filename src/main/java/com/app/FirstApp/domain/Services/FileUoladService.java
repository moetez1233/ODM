package com.app.FirstApp.domain.Services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUoladService {
    public void upladFileInRepo(MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\test1\\SaveUpload\\"+file.getOriginalFilename()));
    }

}
