package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.HistPrimId;
import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.Entity.ShipmentFile;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Repository.HistoriqSfRepo;
import com.app.FirstApp.domain.Repository.ShipmentfileRepo;
import com.app.FirstApp.domain.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public String saveShipmentFile(ShipmentFile shipmentFile, String emailUser) {
        /*ShipmentFile VerifExistSf=shipmentfileRepo.findByName(shipmentFile.getName());
if(VerifExistSf !=null)throw new RuntimeException(shipmentFile.getName() +" est déja exist");*/
        /* ============================= end add user to sf =======================*/

            User userSF = userRepo.findByEmail(emailUser);
            shipmentFile.setUser(userSF);
            /* ============================= end add user to sf =======================*/


            ShipmentFile sf = shipmentfileRepo.save(shipmentFile);
            /* add hist to sf */
            HistPrimId hsPriK = new HistPrimId(sf.getId());
            System.out.println("hsprk= " + hsPriK);
            HistoriqSF hsf = new HistoriqSF(hsPriK, "To treat", "First Upload");

            historiqueSfImplem.SaveHistoriq(hsf, shipmentFile.getName());
            /* end add hist sf */


        return "ShipmentFile " + shipmentFile.getName() + " est ajouter ";
    }

    @Override
    public String addUserToShipmentFile(String emailUser, String nameSF) {

        User userSF=userRepo.findByEmail(emailUser);
        System.out.println("UserSf : "+userSF.toString());
        ShipmentFile sf=shipmentfileRepo.findByName(nameSF);
        System.out.println("sf : "+sf.toString());
        sf.setUser(userSF);
        shipmentfileRepo.save(sf);
        return "sf add";

    }

    @Override
    public ShipmentFile getShipmentFile(String name) {
        return shipmentfileRepo.findByName(name);
    }


}
