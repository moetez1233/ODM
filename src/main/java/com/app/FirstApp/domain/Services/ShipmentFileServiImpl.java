package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.*;

import com.app.FirstApp.domain.KafkaOperation.TestSF;
import com.app.FirstApp.domain.Repository.HistoriqSfRepo;
import com.app.FirstApp.domain.Repository.ShipmentfileRepo;
import com.app.FirstApp.domain.Repository.UserRepo;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import com.app.FirstApp.domain.SaveMethodes.VerifXML;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ShipmentFileServiImpl implements ShipmentFileService{
    private final ShipmentfileRepo shipmentfileRepo;
    private UserRepo userRepo;

    private HistoriqSfRepo historiqSfRepo;

    private HistoriqueSfImplem historiqueSfImplem;

@Autowired
    private FileUoladService fileUoladService;
    @Autowired
    private VerifXML verifXML;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private static final String topic="KMS_Verif_Recep";


    @Autowired
    public ShipmentFileServiImpl(HistoriqueSfImplem historiqueSfImplem,UserRepo userRepo,ShipmentfileRepo shipmentfileRepo,HistoriqSfRepo historiqSfRepo) {
        this.userRepo = userRepo;
        this.shipmentfileRepo=shipmentfileRepo;
        this.historiqSfRepo=historiqSfRepo;
        this.historiqueSfImplem=historiqueSfImplem;
        //this.fileUoladService=fileUoladService;
        //this.verifXML=verifXML;
    }


    @Override
    public ShipmentFile saveShipmentFile(ShipmentFile shipmentFile) {
            ShipmentFile sf = shipmentfileRepo.save(shipmentFile);
            /* add hist to sf */
            HistPrimId hsPriK = new HistPrimId(sf.getId());
            System.out.println("hsprk= " + hsPriK);
            HistoriqSF hsf = new HistoriqSF(hsPriK, StatusConstant.Status1, StatusConstant.Raison1);

            historiqueSfImplem.SaveHistoriq(hsf, shipmentFile.getName());
            /* end add hist sf */


        return sf;
    }

    @Override
    public String ValidXsdSf(String emailUser,  String nameSF){
        ShipmentFile verifExist=shipmentfileRepo.findByName(nameSF);
        String reponce="";
if(verifExist !=null) {
    reponce="Sf "+nameSF+ " deja existe avec status rejected";
}else {
    ShipmentFile sfadded = new ShipmentFile();
    sfadded.setName(nameSF);
    sfadded.setType_compteur("Gaz");
    sfadded.setStatus(StatusConstant.Status01);

    // Do processing with uploaded file data in Service layer
    ShipmentFile sfUploaded = shipmentfileRepo.save(sfadded);

    HistoriqSF hsSF = new HistoriqSF();
    hsSF.setNomStatus(StatusConstant.Status01);
    hsSF.setRaison(StatusConstant.Raison01);
    HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, nameSF);
    reponce="XML invalid: "+sfUploaded.getName()+" est ajouter avec status rejected  ";
}
return reponce;
    }


    @Override
    public ShipmentFile getShipmentFile(String name) {
        return shipmentfileRepo.findByName(name);
    }

    @Override
    public List<ShipmentFile> getSFWithStatus(String status) {
        return shipmentfileRepo.findByStatus(status);
    }

    @Override
    public List<ShipmentFile> getAllSHipmentFile() {
        return shipmentfileRepo.findAll();
    }

    @Override
    public String UploadSf(MultipartFile file, String email) throws Exception {
String reponce="";

        ShipmentFile sfSerch=shipmentfileRepo.findByName(file.getOriginalFilename());
        User userSF = userRepo.findByEmail(email);
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }

if(sfSerch !=null){
    if(sfSerch.getStatus().equals("Rejected")){
        fileUoladService.FirstUpload(file);
        String pathOfFile="F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\A\\"+file.getOriginalFilename();
        Boolean VerifXmlFile=verifXML.validateXMLSchema("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ValidXsd\\ShipmentFile.xsd",pathOfFile);
          if(VerifXmlFile){
             sfSerch.setUser(userSF);
              ShipmentFile sfUploaded = shipmentfileRepo.save(sfSerch); // pour changer user si ajouter on avant avec drag and drop
              HistoriqSF hsSF = new HistoriqSF();
               hsSF.setNomStatus(StatusConstant.Status1);
                hsSF.setRaison("SfipementFile est corriger");
                HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, sfSerch.getName());
                /* send name and pathfile to kms*/
              TestSF SfToVerif=new TestSF(file.getOriginalFilename(),pathOfFile);
              kafkaTemplate.send(topic,SfToVerif.toString());
              /* end send name and pathfile to kms*/
                reponce="ShipemntFIle est modifier au Status Uploaded";
           }else{
              sfSerch.setUser(userSF);
              ShipmentFile sfUploaded = shipmentfileRepo.save(sfSerch); // pour changer user si ajouter on avant avec drag and drop
              HistoriqSF hsSF = new HistoriqSF();
              hsSF.setNomStatus(StatusConstant.Status01);
              hsSF.setRaison("Xml Invalid ");
              HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, sfSerch.getName());
              reponce="ShipemntFIle est modifier au Status Rejected";

          }

    }else reponce="ShipmentFile "+sfSerch.getName()+" déja exist";

}else{

    fileUoladService.FirstUpload(file);
    String pathOfFile="F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\A\\"+file.getOriginalFilename();

    Boolean VerifXmlFile=verifXML.validateXMLSchema("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ValidXsd\\ShipmentFile.xsd",pathOfFile);
       if(VerifXmlFile){

           ShipmentFile sfadded = new ShipmentFile();
           sfadded.setName(file.getOriginalFilename());
           sfadded.setType_compteur("Gaz");
           sfadded.setUser(userSF);
           sfadded.setStatus(StatusConstant.Status1);

           // Do processing with uploaded file data in Service layer
           ShipmentFile sfUploaded = shipmentfileRepo.save(sfadded);

           HistoriqSF hsSF = new HistoriqSF();
           hsSF.setNomStatus(StatusConstant.Status1);
           hsSF.setRaison(StatusConstant.Raison1);
           HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, file.getOriginalFilename());
           /* send name and pathfile to kms*/
           TestSF SfToVerif=new TestSF(file.getOriginalFilename(),pathOfFile);
           kafkaTemplate.send(topic,SfToVerif.toString());
           /* end send name and pathfile to kms*/
           reponce="SHipmentFile "+file.getOriginalFilename()+" est ajouter avec status uploaded ";
       }
       else{
           ShipmentFile sfadded = new ShipmentFile();
           sfadded.setName(file.getOriginalFilename());
           sfadded.setType_compteur("Gaz");
           sfadded.setUser(userSF);
           sfadded.setStatus(StatusConstant.Status01);

           // Do processing with uploaded file data in Service layer
           ShipmentFile sfUploaded = shipmentfileRepo.save(sfadded);

           HistoriqSF hsSF = new HistoriqSF();
           hsSF.setNomStatus(StatusConstant.Status01);
           hsSF.setRaison(StatusConstant.Raison02);
           HistoriqSF hsAdded = historiqueSfImplem.SaveHistoriq(hsSF, file.getOriginalFilename());
           reponce="SHipmentFile "+file.getOriginalFilename()+" est ajouter avec status rejected";
       }
}

        return reponce;
    }

    @Override
    public void UploadSfDragDrop(ShipmentFile shipmentFile) {
        shipmentfileRepo.save(shipmentFile);
    }


}
