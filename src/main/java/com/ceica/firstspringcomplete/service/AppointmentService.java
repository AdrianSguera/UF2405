package com.ceica.firstspringcomplete.service;

import com.ceica.firstspringcomplete.model.Appointment;
import com.ceica.firstspringcomplete.model.Operation;
import com.ceica.firstspringcomplete.repository.AppointmentRepository;
import com.ceica.firstspringcomplete.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final OperationRepository operationRepository;

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(OperationRepository operationRepository, AppointmentRepository appointmentRepository) {
        this.operationRepository = operationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public List<Appointment> getAllAppointmentsByDayCurrentMonth(Integer day) {
        return appointmentRepository.findAllByDate(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),day));
    }

    public List<Appointment> getAllAppointmentsByDayNextMonth(Integer day) {
        return appointmentRepository.findAllByDate(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth().plus(1),day));
    }
}
