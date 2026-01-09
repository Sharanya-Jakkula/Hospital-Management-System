package com.jars.HospitalManagement.repository;

import com.jars.HospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}