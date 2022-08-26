package com.mycompany.bankApp.repository;

import com.mycompany.bankApp.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LogRepo extends JpaRepository<Log, Long> {

    @Query("SELECT * FROM Log l WHERE l.account=:id")
    List<Log> findLogsByAccountId(@Param("id") Long id);
}
