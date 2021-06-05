package com.academicsupportsystem.repositories;
import com.academicsupportsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academicsupportsystem.models.DatabaseFile;

import java.util.List;


@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
        public List<DatabaseFile> findAllByGeneratedBy(User user);
}