package com.app.FirstApp.domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HistoriqSF {


    @EmbeddedId
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private HistPrimId idHistorique;

    private String NomStatus;
    private String raison;
    //private Date modefication_Date=new Date();

    @ManyToOne
    @JsonIgnore
    private ShipmentFile shipmentFile;

    public HistoriqSF() {
    }

    public HistoriqSF(HistPrimId idHistorique, String nomStatus, String raison) {
        this.idHistorique = idHistorique;
        NomStatus = nomStatus;
        this.raison = raison;
    }

    public HistPrimId getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(HistPrimId idHistorique) {
        this.idHistorique = idHistorique;
    }

    public String getNomStatus() {
        return NomStatus;
    }

    public void setNomStatus(String nomStatus) {
        NomStatus = nomStatus;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public ShipmentFile getShipmentFile() {
        return shipmentFile;
    }

    public void setShipmentFile(ShipmentFile shipmentFile) {
        this.shipmentFile = shipmentFile;
    }

 /* public Date getModefication_Date() {
        return modefication_Date;
    }

    public void setModefication_Date(Date modefication_Date) {
        this.modefication_Date = modefication_Date;
    }*/

    @Override
    public String toString() {
        return "HistoriqSF{" +
                "idHistorique=" + idHistorique +
                ", NomStatus='" + NomStatus + '\'' +
                ", raison='" + raison + '\'' +
            //  ", modefication_Date=" + modefication_Date +
                ", shipmentFile=" + shipmentFile +
                '}';
    }
}
