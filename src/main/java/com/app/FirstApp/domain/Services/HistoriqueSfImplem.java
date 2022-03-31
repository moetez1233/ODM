package com.app.FirstApp.domain.Services;

import com.app.FirstApp.domain.Entity.HistPrimId;
import com.app.FirstApp.domain.Entity.HistoriqSF;
import com.app.FirstApp.domain.Entity.ShipmentFile;
import com.app.FirstApp.domain.Repository.HistoriqSfRepo;
import com.app.FirstApp.domain.Repository.ShipmentfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriqueSfImplem implements  HistoriqSfService{
    @Autowired
    private HistoriqSfRepo historiqSfRepo;
    @Autowired
    private ShipmentfileRepo shipmentfileRepo;
    @Override
    public HistoriqSF SaveHistoriq(HistoriqSF historique,String name) {
        ShipmentFile sf=shipmentfileRepo.findByName(name);
        sf.setStatus(historique.getNomStatus());
        HistPrimId hsPriK=new HistPrimId(sf.getId());
        System.out.println("hsprk= "+hsPriK);
        historique.setIdHistorique(hsPriK);
        historique.setShipmentFile(sf);
        return historiqSfRepo.save(historique);
    }
}
