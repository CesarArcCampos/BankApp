package com.mycompany.bankApp.repository;

import com.mycompany.bankApp.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepo extends JpaRepository<Log, Long> {
    
}
