package com.sena.crud_2899747.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_2899747.model.course;

public interface Icourse extends JpaRepository<course, Integer> {
    @Query("SELECT c FROM course c WHERE c.status != false")
    List<course> getListCourseActive();
    
    List<course> findByNameContainingIgnoreCase(String name);

    /*
     * C
     * R
     * U
     * D
     */
}
