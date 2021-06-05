package com.academicsupportsystem.repositories;

import com.academicsupportsystem.models.User;
import org.springframework.data.repository.CrudRepository;

import com.academicsupportsystem.models.Appointment;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{

    public List<Appointment> findAllBySetBy(User user);
    public List<Appointment> findAllBySetFor(User user);
	
}

