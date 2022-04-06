package com.app.FirstApp.domain.Repository;


import com.app.FirstApp.domain.Entity.ShipmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentfileRepo extends JpaRepository<ShipmentFile,Long> {
    ShipmentFile findByName(String name);
    List<ShipmentFile> findByStatus(String status);


}
