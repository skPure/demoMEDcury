package com.MEDcury.demoMEDcury.controller;

import com.MEDcury.demoMEDcury.models.*;
import com.MEDcury.demoMEDcury.request.BookAppointment;
import com.MEDcury.demoMEDcury.response.DateDoctorScheduleResponse;
import com.MEDcury.demoMEDcury.response.DoctorAppointmentSchedule;
import com.MEDcury.demoMEDcury.response.PatientAppointmentSchedule;
import lombok.var;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    //Create doctors information
    private List<Doctors> doctorsList = new ArrayList<>();
    private Doctors doctor1 = new Doctors("001","หมอ ก",1.0);
    private Doctors doctor2 = new Doctors("002","หมอ ข",0.5);

    //Create patients information
    private List<Patients> patientsList = new ArrayList<>();
    private Patients patient1 = new Patients("111111","นายกร","0810000001");
    private Patients patient2 = new Patients("222222","นายนก","0810000002");
    private Patients patient3 = new Patients("333333","นายตูน","0810000003");
    private Patients patient4 = new Patients("444444","นายหมาย","0810000004");

    //Create doctorTimeTable information
    private List<DoctorTimeTable> doctorTimeTablesList = new ArrayList<>();
    private DoctorTimeTable doctor1TimeTable1 = new DoctorTimeTable(1,"001","MONDAY", 8, 12);
    private DoctorTimeTable doctor1TimeTable2 = new DoctorTimeTable(2,"001","WEDNESDAY", 8, 12);
    private DoctorTimeTable doctor1TimeTable3 = new DoctorTimeTable(2,"001","FRIDAY", 8, 12);

    private DoctorTimeTable doctor2TimeTable1 = new DoctorTimeTable(3,"002","TUESDAY", 13, 15);
    private DoctorTimeTable doctor2TimeTable2 = new DoctorTimeTable(4,"002","THURSDAY", 13, 15);

    private List<AppointmentSchedule> appointmentScheduleList = new ArrayList<>();

    public AppointmentController(List<Doctors> doctorsList
            , List<Patients> patientsList
            , List<DoctorTimeTable> doctorTimeTableList){
        this.doctorsList = doctorsList;
        doctorsList.add(doctor1);
        doctorsList.add(doctor2);

        this.patientsList = patientsList;
        patientsList.add(patient1);
        patientsList.add(patient2);
        patientsList.add(patient3);
        patientsList.add(patient4);

        this.doctorTimeTablesList = doctorTimeTableList;
        doctorTimeTableList.add(doctor1TimeTable1);
        doctorTimeTableList.add(doctor1TimeTable2);
        doctorTimeTableList.add(doctor1TimeTable3);
        doctorTimeTableList.add(doctor2TimeTable1);
        doctorTimeTableList.add(doctor2TimeTable2);
    }

    @GetMapping("doctorSchedule")
    public DateDoctorScheduleResponse getDoctorSchedule(@RequestParam(value = "startDate") String startDate
            , @RequestParam(value = "endDate") String endDate) throws Exception{
        //Generate doctor schedule on a week
        List<DoctorSchedule> doctorScheduleList = new ArrayList<>();

        var doctors = new ArrayList<>(doctorsList);
        for (var doctor : doctors) {
            var doctorTimeTables = doctorTimeTablesList
                    .stream()
                    .filter(d -> d.getDoctorId().equals(doctor.getId()))
                    .collect(Collectors.toList());

            for (var doctorTime : doctorTimeTables) {
                for(double hour = doctorTime.getStartTimeSlot();
                    hour < doctorTime.getEndTimeSlot();
                    hour = (hour + doctor.getDiagnosisHours())){

                    DoctorSchedule doctorSchedule = new DoctorSchedule(
                            doctor.getId(),
                            doctor.getName(),
                            doctorTime.getDay(),
                            hour,
                            hour + doctor.getDiagnosisHours()
                            );

                    doctorScheduleList.add(doctorSchedule);
                }
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDateCon = LocalDate.parse(startDate, formatter);
        LocalDate endDateCon = LocalDate.parse(endDate, formatter);

        List<DateDoctorSchedule> dateDoctorScheduleList = new ArrayList<>();
        for (LocalDate localDate = startDateCon; endDateCon.compareTo(localDate) >= 0; localDate = localDate.plusDays(1))
        {
            String dayOfWeek = localDate.getDayOfWeek().toString();

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateDoctorSchedule dateDoctorSchedule = new DateDoctorSchedule();
            dateDoctorSchedule.setDate(dateFormat.format(date));
            dateDoctorSchedule.setDay(dayOfWeek);

            var doctorSchedules = doctorScheduleList
                    .stream()
                    .filter(d -> d.getDay().equals(dayOfWeek))
                    .collect(Collectors.toList());

             if(doctorSchedules.size() > 0)
             {
                 var listAppointmentSchedules = appointmentScheduleList
                         .stream().filter(a -> a.getDate().equals(dateFormat.format(date)))
                         .collect(Collectors.toList());

                 for (var appointmentSchedules : listAppointmentSchedules) {
                     doctorSchedules.removeIf(d -> d.getStartTime() == appointmentSchedules.getTimeSlot());
                 }
             }

            dateDoctorSchedule.setDoctorSchedulesList(doctorSchedules);
            dateDoctorScheduleList.add(dateDoctorSchedule);
        }

        DateDoctorScheduleResponse dateDoctorScheduleResponse = new DateDoctorScheduleResponse();
        dateDoctorScheduleResponse.setDateSearch("Schedule from " + startDate + " to " + endDate);
        dateDoctorScheduleResponse.setDateDoctorScheduleList(dateDoctorScheduleList);

        return dateDoctorScheduleResponse;
    }

    @PostMapping("appointmentSchedule")
    public String bookingAppointment(@RequestBody BookAppointment bookAppointment){
        long lastId = (long) appointmentScheduleList.size() + 1;

        AppointmentSchedule appointmentSchedule = new AppointmentSchedule(
                lastId,
                bookAppointment.getTimeTableId(),
                bookAppointment.getPinCode(),
                bookAppointment.getDate(),
                bookAppointment.getTimeSlot(),
                bookAppointment.getPhone(),
                bookAppointment.getDoctorId()
        );

        appointmentScheduleList.add(appointmentSchedule);

        return "Your booking success.";
    }

    @DeleteMapping("appointmentSchedule/{appointmentScheduleId}")
    public String cancelAppointment(@PathVariable int appointmentScheduleId){
        appointmentScheduleList.removeIf(r -> r.getId() == appointmentScheduleId);

        return "Your booking canceled.";
    }

    @GetMapping("doctorAppointmentSchedule/{doctorId}")
    public DoctorAppointmentSchedule getDoctorAppointmentScheduleLists(@PathVariable String doctorId){
        var doctors = doctorsList
                .stream()
                .filter(d -> d.getId().equals(doctorId)).findFirst().orElse(null);

        List<PatientAppointmentSchedule> patientAppointmentScheduleList = new ArrayList<>();
        var appointmentSchedules = appointmentScheduleList
                .stream().filter(a -> a.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());

        for (var appSchedule : appointmentSchedules) {
            String patientName = patientsList.stream()
                    .filter(p -> p.getPinCode().equals(appSchedule.getPinCode()))
                    .map(Patients::getName).findFirst().orElse(null);

            PatientAppointmentSchedule patientAppointmentSchedule = new PatientAppointmentSchedule(
                    patientName,
                    appSchedule.getDate(),
                    appSchedule.getTimeSlot(),
                    appSchedule.getTimeSlot() + doctors.getDiagnosisHours()
            );

            patientAppointmentScheduleList.add(patientAppointmentSchedule);
        }

        DoctorAppointmentSchedule doctorAppointmentSchedule = new DoctorAppointmentSchedule();
        doctorAppointmentSchedule.setDoctorName(doctors.getName());
        doctorAppointmentSchedule.setPatientAppointmentSchedules(patientAppointmentScheduleList);

        return doctorAppointmentSchedule;
    }
}
