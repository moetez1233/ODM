package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.ShipmentFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShipmentFileService {
    ShipmentFile saveShipmentFile(ShipmentFile shipmentFile);
    String ValidXsdSf(String emailUser, String nameSF) ;

    ShipmentFile getShipmentFile(String name);
    List<ShipmentFile> getSFWithStatus(String status);
    List<ShipmentFile> getAllSHipmentFile();

    String UploadSf(MultipartFile file ,String email) throws Exception;


    /* Save SF Drag && Drop **/
    void UploadSfDragDrop(ShipmentFile shipmentFile);



}
