package com.jars.HospitalManagement.service;

import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

//    private final EntityManager entityManager;

    @Transactional
    public Patient getPatientById(Long id){
        Patient p1= patientRepository.findById(id).orElseThrow();
       Patient p2= patientRepository.findById(id).orElseThrow();
        System.out.println(p1==p2);
        p1.setName("bunny");
//        patientRepository.save(p1); //no - need - updates automatically
       return p1;
    }
}
