package com.ceica.firstspringcomplete.controller;

import com.ceica.firstspringcomplete.model.Appointment;
import com.ceica.firstspringcomplete.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private List<Integer> getAvailableDaysCurrent(){
        List<Integer> availableDaysCurrent = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            List<Appointment> appointmentList = appointmentService.getAllAppointmentsByDayCurrentMonth(i);
            if (appointmentList.size()<5)
                availableDaysCurrent.add(i);
        }
        return availableDaysCurrent;
    }

    @GetMapping("/available_days_next")
    private List<Integer> getAvailableDaysNext(){
        List<Integer> availableDaysNext = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            List<Appointment> appointmentList = appointmentService.getAllAppointmentsByDayNextMonth(i);
            if (appointmentList.size()<5)
                availableDaysNext.add(i);
        }
        return availableDaysNext;
    }
}
