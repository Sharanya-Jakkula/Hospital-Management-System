package com.jars.HospitalManagement.service;

import com.jars.HospitalManagement.entity.Appointment;
import com.jars.HospitalManagement.entity.Doctor;
import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.AppointmentRepository;
import com.jars.HospitalManagement.repository.DoctorRepository;
import com.jars.HospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public void createNewAppointment(Appointment appointment,Long doctorId,Long patientId){
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
        Patient patient=patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId()!=null){
            throw new IllegalArgumentException("Appointment should not have ");
        }
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);


    }
}
