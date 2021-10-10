package com.MEDcury.demoMEDcury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimeTable {
    private int id;
    private String doctorId;
    private String day;
    private double startTimeSlot;
    private double endTimeSlot;
}
