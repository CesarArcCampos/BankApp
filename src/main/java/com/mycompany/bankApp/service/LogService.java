package com.mycompany.bankApp.service;

import com.mycompany.bankApp.model.Log;
import com.mycompany.bankApp.repository.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepo logRepo;

    public void newLog(String action, Log log) {
        log.setAction(action);
        logRepo.save(log);
    }

    public List<Log> getLogsByAccountId(Long accountId) {

        return logRepo.findLogsByAccountId(accountId);
    }
}
