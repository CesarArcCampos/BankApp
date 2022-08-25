package com.mycompany.bankApp.service;

import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public void createNewAccount(Account account) {
        accountRepo.save(account);
    }

    public List<Account> getListAccounts(Long id) {
        return accountRepo.findByCustomerId(id);
    }


}
