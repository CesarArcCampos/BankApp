package com.mycompany.bankApp.repository;

import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(Long id);

    @Query("SELECT A.customer FROM Account A WHERE A.id=:id")
    Customer findByAccountId(@Param("id") Long id);
}
