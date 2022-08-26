package com.mycompany.bankApp.service;

import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.model.Log;
import com.mycompany.bankApp.repository.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    @Autowired
    private LogRepo logRepo;


    public void newLog(String action, Integer amount, Log log, Account account) {
        log.setAction(action);
        log.setAmount(amount);
        log.setAccount(account);
        logRepo.save(log);
    }

    public List<Log> getLogsByAccountId(Long accountId) {

        return logRepo.findLogsByAccountId(accountId);
    }
}
