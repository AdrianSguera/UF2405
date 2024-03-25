package com.ceica.firstspringcomplete.controller;

import com.ceica.firstspringcomplete.model.Appointment;
import com.ceica.firstspringcomplete.model.Operation;
import com.ceica.firstspringcomplete.repository.OperationRepository;
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
        model.addAttribute("availableDays", getAvailableDays());
        return "appointment";
    }

    @PostMapping("/appointment")
    public String postAppointment(@RequestParam String operation){
        System.out.println(operation);
        return "appointment";
    }

    private List<String> getOperationNames(){
        List<Operation> operationList = appointmentService.getAllOperations();
        List<String> operationNames = new ArrayList<>();
        for (Operation operation : operationList){
            operationNames.add(operation.getName());
        }
        return operationNames;
    }

    private List<Integer> getAvailableDays(){
        List<Integer> availableDays = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            List<Appointment> appointmentList = appointmentService.getAllAppointmentsByDay(i);
            if (appointmentList.size()<5)
                availableDays.add(i);
        }
        return availableDays;
    }
}
