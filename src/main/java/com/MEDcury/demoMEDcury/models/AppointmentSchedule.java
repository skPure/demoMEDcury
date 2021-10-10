package com.MEDcury.demoMEDcury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentSchedule {
    private long id;
    private int timeTableId;
    private String pinCode;
    private String date;
    private double timeSlot;
    private String phone;
    private String doctorId;
}
