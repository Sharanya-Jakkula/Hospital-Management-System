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
    public Appointment createNewAppointment(Appointment appointment,Long doctorId,Long patientId){
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
        Patient patient=patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId()!=null){
            throw new IllegalArgumentException("Appointment should not have ");
        }
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        //to maintain bidirectional consistency
        patient.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);

    }

@Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId,Long doctorId){
        Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
        appointment.setDoctor(doctor); //this will automatically call the update , because it is dirty
        doctor.getAppointments().add(appointment); //bi-directional consistency
        return appointment;
}

}
