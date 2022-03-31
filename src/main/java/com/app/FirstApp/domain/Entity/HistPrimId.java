package com.app.FirstApp.domain.Entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
@Embeddable
public class HistPrimId implements Serializable {
    private Long id_sf;
    private Date modefication_sf=new Date();


    public HistPrimId() {
    }

    public HistPrimId(Long id_sf) {
        this.id_sf = id_sf;
    }

    public Long getId_sf() {
        return id_sf;
    }

    public void setId_sf(Long id_sf) {
        this.id_sf = id_sf;
    }

    public Date getModefication_sf() {
        return modefication_sf;
    }

    public void setModefication_sf(Date modefication_sf) {
        this.modefication_sf = modefication_sf;
    }



}
