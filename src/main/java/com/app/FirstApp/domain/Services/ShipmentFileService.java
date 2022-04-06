package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.ShipmentFile;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShipmentFileService {
    ShipmentFile saveShipmentFile(ShipmentFile shipmentFile, String emailUser);

    ShipmentFile getShipmentFile(String name);
    List<ShipmentFile> getSFWithStatus(String status);
    List<ShipmentFile> getAllSHipmentFile();



}
