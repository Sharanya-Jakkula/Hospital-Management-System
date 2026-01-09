package com.jars.HospitalManagement.repository;

import com.jars.HospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}