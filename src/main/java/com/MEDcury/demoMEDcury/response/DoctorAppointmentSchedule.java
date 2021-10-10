package com.MEDcury.demoMEDcury.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAppointmentSchedule {
    private String doctorName;
    private List<PatientAppointmentSchedule> patientAppointmentSchedules;
}
