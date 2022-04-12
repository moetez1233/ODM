package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class FileUoladService {
    @Autowired
    private HistoriqueSfImplem historiqueSfImplem;
    public void FirstUpload(MultipartFile file) throws IOException {
        file.transferTo(new File("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\A\\"+file.getOriginalFilename()));
    }

    /*public void transferSF() throws Exception {
        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\A")

                        //.setBody()
                        .to("file://F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\c");
            }
        });

            context.start();
    }*/

}
