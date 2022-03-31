package com.app.FirstApp.domain.Repository;


import com.app.FirstApp.domain.Entity.ShipmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentfileRepo extends JpaRepository<ShipmentFile,Long> {
    ShipmentFile findByName(String name);


}
