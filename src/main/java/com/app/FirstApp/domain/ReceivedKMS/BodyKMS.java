package com.app.FirstApp.domain.ReceivedKMS;

import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import com.app.FirstApp.domain.Services.HistoriqueSfImplem;
import org.apache.camel.Exchange;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BodyKMS {
    @Autowired
    private HistoriqueSfImplem historiqueSfImplem;
    public Object getStatus(Exchange exchange) {
        String KmsBody= exchange.getIn().getBody(String.class);
        String name=KmsBody.split(",")[0];
        String status=KmsBody.split(",")[1];


        System.out.println(status);
        System.out.println(name);
        if(status.equals("Ok")){
            HistoriqSF hsSF=new HistoriqSF();
            hsSF.setNomStatus(StatusConstant.Status3);
            hsSF.setRaison(StatusConstant.Raison3);
            HistoriqSF hsAdded =historiqueSfImplem.SaveHistoriq(hsSF,name);
            System.out.println(hsAdded);
        }else if(status.equals("Ko")){
            HistoriqSF hsSF=new HistoriqSF();
            hsSF.setNomStatus(StatusConstant.Status4);
            hsSF.setRaison(StatusConstant.Raison4);
            HistoriqSF hsAdded =historiqueSfImplem.SaveHistoriq(hsSF,name);
            System.out.println(hsAdded);
        }
        return KmsBody;
    }
}
