package com.example.demo.DTO;

import com.example.demo.Entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {
    private String name;
    private String specialization;
    private Status status = Status.AVAILABLE;
}
