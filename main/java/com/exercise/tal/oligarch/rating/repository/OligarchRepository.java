package com.exercise.tal.oligarch.rating.repository;

import com.exercise.tal.oligarch.rating.model.Oligarch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OligarchRepository extends JpaRepository<Oligarch, Long> {
    @Query("SELECT COUNT(o) + 1 FROM Oligarch o WHERE o.assetsValue > ?1")
    long findRankByAssetsValue(BigDecimal assetsValue);
}