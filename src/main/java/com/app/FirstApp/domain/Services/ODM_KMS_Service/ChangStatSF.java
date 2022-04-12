package com.app.FirstApp.domain.Services.ODM_KMS_Service;

import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import com.app.FirstApp.domain.Services.HistoriqueSfImplem;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangStatSF {
    @Autowired
    private StatusConstant statusConstant;
    @Autowired
    private HistoriqueSfImplem historiqueSfImplem;
    public String VerifReponce(Exchange exchange){
        String body=exchange.getIn().getBody(String.class);
        HistoriqSF hsSF = new HistoriqSF();
        hsSF.setNomStatus(statusConstant.Status2);
        hsSF.setRaison(statusConstant.Raison2);
        HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, body);
        return body;
    }
}
