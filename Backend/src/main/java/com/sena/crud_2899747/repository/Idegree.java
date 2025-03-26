package com.sena.crud_2899747.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_2899747.model.degree;

public interface Idegree extends JpaRepository<degree, Integer> {
    @Query("SELECT d FROM degree d WHERE d.status != false")
    List<degree> getListDegreeActive();

    @Query("SELECT d FROM degree d WHERE d.name LIKE %?1%")
    List<degree> getListDegreeForName(String filter);

    /*
     * C
     * R
     * U
     * D
     */
}