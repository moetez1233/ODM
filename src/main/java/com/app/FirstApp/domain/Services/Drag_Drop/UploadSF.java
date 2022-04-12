package com.app.FirstApp.domain.Services.Drag_Drop;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UploadSF extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file://F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\B")
                .bean(ValidateSF.class,"ValidContraint")
                .to("log:first-timer");
    }

}
