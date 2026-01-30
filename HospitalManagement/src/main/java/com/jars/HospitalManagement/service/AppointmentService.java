package com.jars.HospitalManagement.service;

import com.jars.HospitalManagement.dto.AppointmentResponseDto;
import com.jars.HospitalManagement.dto.CreateAppointmentRequestDto;
import com.jars.HospitalManagement.entity.Appointment;
import com.jars.HospitalManagement.entity.Doctor;
import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.repository.AppointmentRepository;
import com.jars.HospitalManagement.repository.DoctorRepository;
import com.jars.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

//    @Transactional
//    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId) {
//        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
//        Patient patient = patientRepository.findById(patientId).orElseThrow();
//
//        if (appointment.getId() != null) {
//            throw new IllegalArgumentException("Appointment should not have ");
//        }
//        appointment.setPatient(patient);
//        appointment.setDoctor(doctor);
//
//        //to maintain bidirectional consistency
//        patient.getAppointments().add(appointment);
//
//        return appointmentRepository.save(appointment);
//
//    }

    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        Long patientId = createAppointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Appointment appointment = Appointment.builder()
                .reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }


    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        appointment.setDoctor(doctor); //this will automatically call the update , because it is dirty
        doctor.getAppointments().add(appointment); //bi-directional consistency
        return appointment;
    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
