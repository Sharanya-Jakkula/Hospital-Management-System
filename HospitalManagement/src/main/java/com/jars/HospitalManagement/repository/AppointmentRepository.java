package com.jars.HospitalManagement.repository;

import com.jars.HospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}