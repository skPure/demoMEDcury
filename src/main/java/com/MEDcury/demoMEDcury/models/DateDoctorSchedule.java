package com.MEDcury.demoMEDcury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateDoctorSchedule {
    private String date;
    private String day;
    private List<DoctorSchedule> doctorSchedulesList;
}
