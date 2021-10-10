package com.MEDcury.demoMEDcury.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule {
    @JsonIgnore
    private String doctorId;

    private String doctorName;

    @JsonIgnore
    private String day;

    private double startTime;
    private double endTime;
}
