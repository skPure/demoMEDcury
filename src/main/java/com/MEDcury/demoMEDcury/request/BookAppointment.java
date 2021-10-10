package com.MEDcury.demoMEDcury.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAppointment {
    private String pinCode;
    private int timeTableId;
    private String date;
    private Double timeSlot;
    private String phone;
    private String doctorId;
}
