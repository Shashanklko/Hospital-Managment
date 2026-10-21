Hospital Appointment System

Project Overview: Design a system for managing hospital appointments, including patients, doctors, and bookings. Comparable to e-commerce with customers, products, and orders.

Entities:

1\.	Patient (e.g., id, name, age, contactInfo).

2\.	Doctor (e.g., id, name, specialty, availability).

3\.	Appointment (e.g., id, date, time, status, patientId, doctorId).

Monolithic Phase:

•	Start with Spring Boot project including Web, JPA, H2.

•	Define entities: Patient, Doctor, Appointment.

•	Create repositories: PatientRepository, DoctorRepository, AppointmentRepository.

•	Develop services: PatientService, DoctorService, AppointmentService for booking logic.

•	Build controllers: PatientController, DoctorController, AppointmentController (e.g., POST /appointments).

•	Implement DTOs for API responses.

•	Use application.properties for H2 config.

•	Test via Postman collections.

•	Structure packages and integrate Actuator.

