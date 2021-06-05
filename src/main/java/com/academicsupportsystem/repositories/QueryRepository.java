package com.academicsupportsystem.repositories;

import com.academicsupportsystem.models.User;
import org.springframework.data.repository.CrudRepository;

import com.academicsupportsystem.models.Query;

import java.util.List;

public interface QueryRepository extends CrudRepository<Query, Integer>{
    public List<Query> findAllBySender(User user);
    public List<Query> findAllByReceiver(User user);
}
