package com.jars.HospitalManagement.service;

import com.jars.HospitalManagement.entity.Insurance;
import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.InsuranceRepository;
import com.jars.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance,Long patientId){
        Patient patient=patientRepository.findById(patientId)
                .orElseThrow(()->new EntityNotFoundException("Patient not found with Id : "+patientId));
        patient.setInsurance(insurance);
        insurance.setPatient(patient); //bi-directional consistency maintenance

        return patient;
    }
}
