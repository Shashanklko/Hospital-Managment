package com.example.demo.Service;

import com.example.demo.DTO.DoctorRequest;
import com.example.demo.DTO.DoctorResponse;
import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.Status;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponse createDoctor(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setStatus(request.getStatus() != null ? request.getStatus() : Status.AVAILABLE);

        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorResponse> responseList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            responseList.add(mapToResponse(doctor));
        }
        return responseList;
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return mapToResponse(doctor);
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        if (request.getStatus() != null) {
            doctor.setStatus(request.getStatus());
        }

        Doctor updated = doctorRepository.save(doctor);
        return mapToResponse(updated);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpecialization(doctor.getSpecialization());
        response.setStatus(doctor.getStatus());
        return response;
    }
}
