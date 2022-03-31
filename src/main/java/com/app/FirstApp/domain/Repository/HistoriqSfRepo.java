package com.app.FirstApp.domain.Repository;

import com.app.FirstApp.domain.Entity.HistoriqSF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface HistoriqSfRepo extends JpaRepository<HistoriqSF,Long> {
    List<HistoriqSF> findSfByIdHistorique(Long id);
}
