package com.mycompany.bankApp.repository;

import com.mycompany.bankApp.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LogRepo extends JpaRepository<Log, Long> {

    @Query(value = "select * from log where account_id = :id", nativeQuery = true)
    List<Log> findLogsByAccountId(@Param("id") Long id);
}
