package com.app.FirstApp.domain.Services.Drag_Drop;

import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.Entity.ShipmentFile;
import com.app.FirstApp.domain.KafkaOperation.TestSF;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import com.app.FirstApp.domain.SaveMethodes.VerifXML;
import com.app.FirstApp.domain.Services.FileUoladService;
import com.app.FirstApp.domain.Services.HistoriqueSfImplem;
import com.app.FirstApp.domain.Services.ShipmentFileServiImpl;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateSF {
    @Autowired
    private ShipmentFileServiImpl shipmentFileServiImpl;
    @Autowired
    private HistoriqueSfImplem historiqueSfImplem;
    @Autowired
    private VerifXML verifXML;
    @Autowired
    private StatusConstant statusConstant;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private static final String topic="KMS_Verif_Recep";
    public String ValidContraint(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
        String nameFile=headers.get("CamelFileName").toString();
        String pathFile=headers.get("CamelFileAbsolutePath").toString();
        ShipmentFile SfVerifExit=shipmentFileServiImpl.getShipmentFile(nameFile);
        System.out.println(nameFile);
        if (SfVerifExit==null){
            Boolean VerifXmlFile=verifXML.validateXMLSchema("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ValidXsd\\ShipmentFile.xsd","F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\B\\"+nameFile);

            if(VerifXmlFile){
                /* ****************************************************** not existe et xml Valid ************* */
                ShipmentFile sfadded = new ShipmentFile();
                sfadded.setName(nameFile);
                sfadded.setType_compteur("Gaz");

                sfadded.setStatus(statusConstant.Status1);
              shipmentFileServiImpl.UploadSfDragDrop(sfadded);

                HistoriqSF hsSF = new HistoriqSF();
                hsSF.setNomStatus(statusConstant.Status1);
                hsSF.setRaison(statusConstant.Raison1);
                HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, nameFile);
                /* send name and pathfile to kms*/
                TestSF SfToVerif=new TestSF(nameFile,pathFile);
               System.out.println(SfToVerif.toString());
                kafkaTemplate.send(topic,SfToVerif.toString());
                /* end send name and pathfile to kms*/
                /* ****************************************************** end  not existe et xml Valid ************* */
                /* ******************************************************  not existe et xml InvalidValid ************* */

                return "not existe et xml Valid";
            }
            ShipmentFile sfadded = new ShipmentFile();
            sfadded.setName(nameFile);
            sfadded.setType_compteur("Gaz");

            sfadded.setStatus(statusConstant.Status01);
            shipmentFileServiImpl.UploadSfDragDrop(sfadded);

            HistoriqSF hsSF = new HistoriqSF();
            hsSF.setNomStatus(statusConstant.Status01);
            hsSF.setRaison(statusConstant.Raison02);
            HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, nameFile);
            return "not existe est Xml Invalid";

        }else if(SfVerifExit.getStatus().equals("Rejected")){
            Boolean VerifXmlFile=verifXML.validateXMLSchema("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ValidXsd\\ShipmentFile.xsd","F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\B\\"+nameFile);

            if(VerifXmlFile){
                SfVerifExit.setUser(null);
                shipmentFileServiImpl.UploadSfDragDrop(SfVerifExit);
                HistoriqSF hsSF = new HistoriqSF();
                hsSF.setNomStatus(statusConstant.Status1);
                hsSF.setRaison(statusConstant.Raison1);
                HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, nameFile);
                /* send name and pathfile to kms*/

                TestSF SfToVerif=new TestSF(nameFile,pathFile);
                //System.out.println(SfToVerif.toString());
                kafkaTemplate.send(topic,SfToVerif.toString());
                /* end send name and pathfile to kms*/
                return "existe avec status rejected et xml Valid";
            }else{
                SfVerifExit.setUser(null);
                shipmentFileServiImpl.UploadSfDragDrop(SfVerifExit);
                HistoriqSF hsSF = new HistoriqSF();
                hsSF.setNomStatus(statusConstant.Status01);
                hsSF.setRaison(statusConstant.Raison02);
                HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, nameFile);
                return "existe avec status rejected Xml Invalid";
            }


        }
        return "deja exite";

    }



}
