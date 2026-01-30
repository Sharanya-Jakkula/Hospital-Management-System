package com.jars.HospitalManagement.service;

import com.jars.HospitalManagement.dto.PatientResponseDto;
import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    private final ModelMapper modelMapper;

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

    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}
