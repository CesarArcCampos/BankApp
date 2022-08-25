package com.mycompany.bankApp.repository;

import com.mycompany.bankApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(Long id);
}
