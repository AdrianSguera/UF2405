package com.ceica.firstspringcomplete.service;

import com.ceica.firstspringcomplete.model.Appointment;
import com.ceica.firstspringcomplete.model.Operation;
import com.ceica.firstspringcomplete.model.TimeBand;
import com.ceica.firstspringcomplete.model.User;
import com.ceica.firstspringcomplete.repository.AppointmentRepository;
import com.ceica.firstspringcomplete.repository.OperationRepository;
import com.ceica.firstspringcomplete.repository.TimeBandRepository;
import com.ceica.firstspringcomplete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final OperationRepository operationRepository;

    private final AppointmentRepository appointmentRepository;

    private final TimeBandRepository timeBandRepository;

    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(OperationRepository operationRepository, AppointmentRepository appointmentRepository,
                              TimeBandRepository timeBandRepository, UserRepository userRepository) {
        this.operationRepository = operationRepository;
        this.appointmentRepository = appointmentRepository;
        this.timeBandRepository = timeBandRepository;
        this.userRepository = userRepository;
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public List<Appointment> getAllAppointmentsByDayCurrentMonth(Integer day) {
        return appointmentRepository.findAllByDate(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),day));
    }

    public List<Appointment> getAllAppointmentsByDayNextMonth(Integer day) {
        LocalDate currentDate = LocalDate.now();
        int nextMonthYear = currentDate.getMonthValue() == 12 ? currentDate.getYear() + 1 : currentDate.getYear();
        Month nextMonth = currentDate.getMonthValue() == 12 ? Month.JANUARY : currentDate.getMonth().plus(1);
        return appointmentRepository.findAllByDate(LocalDate.of(nextMonthYear, nextMonth, day));
    }

    public List<String> getTimeBandsByDay(Integer day, Integer month, Integer year) {
        List<Appointment> appointmentList = appointmentRepository.findAllByDate(LocalDate.of(year, month + 1, day));
        List<String> availableTimeBands = new ArrayList<>();
        if (appointmentList.isEmpty()) {
            availableTimeBands.add("10:00");
            availableTimeBands.add("10:15");
            availableTimeBands.add("10:30");
            availableTimeBands.add("10:45");
            availableTimeBands.add("11:00");
            return availableTimeBands;
        }
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        List<Integer> appointmentTimes = new ArrayList<>();
        for (Appointment appointment : appointmentList)
            appointmentTimes.add(appointment.getIdTime());
        timeBandList.removeIf(timeBand -> appointmentTimes.contains(timeBand.getId()));
        for (TimeBand timeBand : timeBandList)
            availableTimeBands.add(timeBand.getTimeBand());
        return availableTimeBands;
    }

    public void saveAppointment(String operation, String date, String timeBand) {
        Operation operationObject = operationRepository.findByName(operation);
        TimeBand timeBandObject = timeBandRepository.findByName(timeBand);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate newDate = LocalDate.parse(date, formatter);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        Appointment appointment = new Appointment();
        appointment.setDate(newDate);
        appointment.setIdOperation(operationObject.getId());
        appointment.setIdTime(timeBandObject.getId());
        appointment.setIdUser(user.getId());
        appointmentRepository.save(appointment);
    }
}
