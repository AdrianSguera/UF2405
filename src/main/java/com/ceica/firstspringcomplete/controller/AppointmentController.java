package com.ceica.firstspringcomplete.controller;

import com.ceica.firstspringcomplete.model.Operation;
import com.ceica.firstspringcomplete.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointment")
    public String getAppointment(Model model){
        model.addAttribute("operationNames", getOperationNames());
        return "appointment";
    }

    @GetMapping("/newAppointment")
    public String confirmAppointment(@RequestParam String operation, @RequestParam String date, @RequestParam String timeBand) {
        appointmentService.saveAppointment(operation, date, timeBand);
        return "home";
    }

    private List<String> getOperationNames(){
        List<Operation> operationList = appointmentService.getAllOperations();
        List<String> operationNames = new ArrayList<>();
        for (Operation operation : operationList){
            operationNames.add(operation.getName());
        }
        return operationNames;
    }
}
