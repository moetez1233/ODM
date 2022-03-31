package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.ShipmentFile;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShipmentFileService {
    String saveShipmentFile(ShipmentFile shipmentFile, String emailUser);
    String addUserToShipmentFile(String emailUser,String nameSF);
    ShipmentFile getShipmentFile(String name);




}
