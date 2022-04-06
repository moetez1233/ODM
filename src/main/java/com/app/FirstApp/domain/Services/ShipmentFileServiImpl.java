package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.*;
import com.app.FirstApp.domain.Repository.HistoriqSfRepo;
import com.app.FirstApp.domain.Repository.ShipmentfileRepo;
import com.app.FirstApp.domain.Repository.UserRepo;
import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentFileServiImpl implements ShipmentFileService{
    private final ShipmentfileRepo shipmentfileRepo;
    private UserRepo userRepo;

    private HistoriqSfRepo historiqSfRepo;

    private HistoriqueSfImplem historiqueSfImplem;


    @Autowired
    public ShipmentFileServiImpl(HistoriqueSfImplem historiqueSfImplem,UserRepo userRepo,ShipmentfileRepo shipmentfileRepo,HistoriqSfRepo historiqSfRepo) {
        this.userRepo = userRepo;
        this.shipmentfileRepo=shipmentfileRepo;
        this.historiqSfRepo=historiqSfRepo;
        this.historiqueSfImplem=historiqueSfImplem;
    }


    @Override
    public ShipmentFile saveShipmentFile(ShipmentFile shipmentFile, String emailUser) {

        /* ============================= end add user to sf =======================*/

            User userSF = userRepo.findByEmail(emailUser);
            UserResp returnVal = new UserResp();
        BeanUtils.copyProperties(userSF,returnVal);
            shipmentFile.setUser(userSF);
            /* ============================= end add user to sf =======================*/


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


}
