package com.ceica.firstspringcomplete.controller;

import com.ceica.firstspringcomplete.model.Appointment;
import com.ceica.firstspringcomplete.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestAppointment {

    private final AppointmentService appointmentService;

    @Autowired
    public RestAppointment(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/available_days_current")
    public List<Integer> getAvailableDaysCurrent() {
        List<Integer> availableDaysCurrent = new ArrayList<>();
        Month currentMonth = LocalDate.now().getMonth();
        for (int i = 1; i <= currentMonth.length(false); i++) {
            List<Appointment> appointmentList = appointmentService.getAllAppointmentsByDayCurrentMonth(i);
            if (appointmentList != null && appointmentList.size() < 5) {
                availableDaysCurrent.add(i);
            }
        }
        return availableDaysCurrent;
    }

    @GetMapping("/available_days_next")
    public List<Integer> getAvailableDaysNext() {
        List<Integer> availableDaysNext = new ArrayList<>();
        Month currentMonth = LocalDate.now().getMonth().plus(1);
        for (int i = 1; i <= currentMonth.length(true); i++) {
            List<Appointment> appointmentList = appointmentService.getAllAppointmentsByDayNextMonth(i);
            if (appointmentList != null && appointmentList.size() < 5) {
                availableDaysNext.add(i);
            }
        }
        return availableDaysNext;
    }

    @GetMapping("/timeBandsByDay")
    public List<String> getTimeBandsByDay(@RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year){
        return appointmentService.getTimeBandsByDay(day, month, year);
    }
}
