package com.ceica.firstspringcomplete.repository;

import com.ceica.firstspringcomplete.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByDate(LocalDate date);
}
