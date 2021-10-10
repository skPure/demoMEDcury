package com.MEDcury.demoMEDcury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctors {
    private String id;
    private String name;
    private double diagnosisHours;
}
