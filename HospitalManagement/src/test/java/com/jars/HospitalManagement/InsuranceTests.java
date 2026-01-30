package com.jars.HospitalManagement;

import com.jars.HospitalManagement.entity.Appointment;
import com.jars.HospitalManagement.entity.Insurance;
import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.service.AppointmentService;
import com.jars.HospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTests {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testCreateAppointment(){
        //transient state
        Appointment appointment=Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,1,12,14,20,20))
                .reason("Cancer")
                .build();

        var newAppointment=appointmentService.createNewAppointment(appointment,1L,2L);
        System.out.println(newAppointment);

       var updatedAppointment= appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(),3L);
        System.out.println(updatedAppointment);

    }


    @Test
    public void testInsurance(){
        Insurance insurance=Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030,12,12))
                .build();

       Patient patient= insuranceService.assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);

       var newPatient= insuranceService.disassociateInsuranceFromPatient(patient.getId());
        System.out.println(newPatient);
    }
}
