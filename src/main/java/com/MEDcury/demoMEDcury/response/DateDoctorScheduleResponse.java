package com.MEDcury.demoMEDcury.response;
import com.MEDcury.demoMEDcury.models.DateDoctorSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateDoctorScheduleResponse {
    private String dateSearch;
    private List<DateDoctorSchedule> dateDoctorScheduleList;
}
