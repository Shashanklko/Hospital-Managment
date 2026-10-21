package com.example.demo.Service;

import com.example.demo.DTO.PatientRequest;
import com.example.demo.DTO.PatientResponse;
import com.example.demo.Entity.Patient;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setContactInfo(request.getContactInfo());
        patient.setGender(request.getGender());

        Patient saved = patientRepository.save(patient);
        return mapToResponse(saved);
    }

    public List<PatientResponse> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponse> responseList = new ArrayList<>();
        for (Patient patient : patients) {
            responseList.add(mapToResponse(patient));
        }
        return responseList;
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToResponse(patient);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setContactInfo(request.getContactInfo());
        patient.setGender(request.getGender());

        Patient updated = patientRepository.save(patient);
        return mapToResponse(updated);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    private PatientResponse mapToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setName(patient.getName());
        response.setAge(patient.getAge());
        response.setContactInfo(patient.getContactInfo());
        response.setGender(patient.getGender());
        return response;
    }
}
