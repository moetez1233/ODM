package com.app.FirstApp.domain.ReceivedKMS;

import com.app.FirstApp.domain.Services.HistoriqueSfImplem;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultKMS extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        String Kafka = "kafka:moetezcode?brokers=localhost:9092";
          from(Kafka)

                  .bean(BodyKMS.class,"getStatus");


    }
}
