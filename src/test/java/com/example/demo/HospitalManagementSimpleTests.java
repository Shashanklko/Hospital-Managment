package com.example.demo;

import com.example.demo.DTO.AppointmentRequest;
import com.example.demo.DTO.AppointmentResponse;
import com.example.demo.DTO.DoctorRequest;
import com.example.demo.DTO.DoctorResponse;
import com.example.demo.DTO.PatientRequest;
import com.example.demo.DTO.PatientResponse;
import com.example.demo.Entity.AppointmentStatus;
import com.example.demo.Entity.Status;
import com.example.demo.Service.AppointmentService;
import com.example.demo.Service.DoctorService;
import com.example.demo.Service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class HospitalManagementSimpleTests {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void testSimpleBookingFlow() {
        // 1. Create Patient
        PatientRequest patientReq = new PatientRequest("John Doe", 30, "555-1234", "Male");
        PatientResponse patientRes = patientService.createPatient(patientReq);
        Assertions.assertNotNull(patientRes.getId());
        Assertions.assertEquals("John Doe", patientRes.getName());

        // 2. Create Doctor
        DoctorRequest doctorReq = new DoctorRequest("Dr. Strange", "Neuro Surgery", Status.AVAILABLE);
        DoctorResponse doctorRes = doctorService.createDoctor(doctorReq);
        Assertions.assertNotNull(doctorRes.getId());
        Assertions.assertEquals("Dr. Strange", doctorRes.getName());

        // 3. Book Appointment with NOT_CONFIRMED status
        AppointmentRequest appReq = new AppointmentRequest(
                patientRes.getId(),
                doctorRes.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                AppointmentStatus.NOT_CONFIRMED
        );
        AppointmentResponse appRes = appointmentService.bookAppointment(appReq);
        Assertions.assertNotNull(appRes.getId());
        Assertions.assertEquals(patientRes.getId(), appRes.getPatientId());
        Assertions.assertEquals(doctorRes.getId(), appRes.getDoctorId());
        Assertions.assertEquals(AppointmentStatus.NOT_CONFIRMED, appRes.getStatus());

        // 4. Confirm Appointment
        AppointmentResponse confirmedRes = appointmentService.confirmAppointment(appRes.getId());
        Assertions.assertEquals(AppointmentStatus.CONFIRMED, confirmedRes.getStatus());

        // 5. Cancel Appointment
        AppointmentResponse cancelledRes = appointmentService.cancelAppointment(appRes.getId());
        Assertions.assertEquals(AppointmentStatus.CANCELLED, cancelledRes.getStatus());
    }
}
