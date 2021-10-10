package com.MEDcury.demoMEDcury.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointmentSchedule {
    private String patientName;
    private String date;
    private double startTime;
    private double endTime;
}
