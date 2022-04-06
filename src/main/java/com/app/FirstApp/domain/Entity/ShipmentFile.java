package com.app.FirstApp.domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class ShipmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;
    private String type_compteur;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_of_id")
    @JsonIgnoreProperties(value = "password")
    private User user;

    @OneToMany(mappedBy = "shipmentFile",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<HistoriqSF> historiques=new HashSet<>();


    public ShipmentFile() {
    }

    public ShipmentFile(String name, String type_compteur, String status, User user) {
        this.name = name;
        this.type_compteur = type_compteur;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_compteur() {
        return type_compteur;
    }

    public void setType_compteur(String type_compteur) {
        this.type_compteur = type_compteur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<HistoriqSF> getHistoriques() {
        return historiques;
    }

    public void setHistoriques(Set<HistoriqSF> historiques) {
        this.historiques = historiques;
    }

    @Override
    public String toString() {
        return "ShipmentFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type_compteur='" + type_compteur + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user +

                '}';
    }
}
