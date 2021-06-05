package com.academicsupportsystem.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.academicsupportsystem.models.User;
import com.academicsupportsystem.models.UserRole;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	public List<User> findByRole(UserRole role);
}
