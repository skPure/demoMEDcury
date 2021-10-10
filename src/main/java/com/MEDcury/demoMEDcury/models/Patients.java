package com.MEDcury.demoMEDcury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patients {
    private String pinCode;
    private String name;
    private String phone;
}
