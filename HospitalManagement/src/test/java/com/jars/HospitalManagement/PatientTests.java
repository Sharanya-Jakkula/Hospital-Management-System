package com.jars.HospitalManagement;

import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.PatientRepository;
import com.jars.HospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientRepository(){

        List<Patient> patientList=patientRepository.findAll();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods(){
        Patient patient1= patientService.getPatientById(0L);
        System.out.println(patient1);
    }
}
