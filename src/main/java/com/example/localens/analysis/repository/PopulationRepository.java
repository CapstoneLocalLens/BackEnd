package com.example.localens.analysis.repository;

import com.example.localens.analysis.domain.Hourly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopulationRepository extends JpaRepository<Hourly, Long> {
}

