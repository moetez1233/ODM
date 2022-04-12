package com.app.FirstApp.domain.Services.ODM_KMS_Service;

import com.app.FirstApp.domain.SaveMethodes.TopicNameConstant;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ReadVerifKms extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        String KMS_ODM="kafka:KmsOdmVerification?brokers=localhost:9092";
        from(KMS_ODM)

                .bean(ChangStatSF.class,"VerifReponce")
                .to("log:firs-timer");
    }
}
