package com.jars.HospitalManagement;

import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.entity.dto.BloodGroupCountResponseEntity;
import com.jars.HospitalManagement.entity.type.BloodGroupType;
import com.jars.HospitalManagement.repository.PatientRepository;
import com.jars.HospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
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
//        Patient patient1= patientService.getPatientById(0L);

//        System.out.println(patient1);
//        Patient patient=patientRepository.findByName("Sharanya Jakkula");
//        System.out.println(patient);

//        List<Patient> patientList=patientRepository.findByBirthDateOrEmail(LocalDate.of(1990,5,12),"sharanya.j@gmail.com");
//        List<Patient> patientList=patientRepository.findByNameContainingOrderByIdDesc("ja");
//        List<Patient> patientList=patientRepository.findByBloodGroup(BloodGroupType.A_POSITIVE);
//        List<Patient> patientList=patientRepository.findByBornAfterDate(LocalDate.of(1940,1,1));
//    for(Patient patient:patientList){
//        System.out.println(patient);
//    }
//        List<Object[]> bloodGroupList=patientRepository.countEachBloodGroupType();
//        for(Object[] objects:bloodGroupList){
//            System.out.println(objects[0]+" "+objects[1]);
//        }

        //same column name as in database
        Page<Patient> list=patientRepository.findAllPatients(PageRequest.of(0,2, Sort.by("birth_date","name")));
        for(Patient patient:list){
            System.out.println(patient);
        }


//        int rowsUpdated=patientRepository.updateNameWithId("Anil sharma",51L);
//        System.out.println(rowsUpdated);

//        List<BloodGroupCountResponseEntity> bloodGroupList=patientRepository.countEachBloodGroupType();
//        for(BloodGroupCountResponseEntity bloodGroupCountResponseEntity:bloodGroupList){
//            System.out.println(bloodGroupCountResponseEntity);
//        }


    }
}
