package com.mycompany.bankApp.service;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    private Account account;

    public void createNewAccount(Account account) {
        accountRepo.save(account);
    }

    public List<Account> getListAccounts(Long id) {
        return accountRepo.findByCustomerId(id);
    }

    public Account getAccountByID(Long id) throws UserNotFoundException {
        Optional<Account> account = accountRepo.findById(id);

        if(account.isPresent()) {
            return account.get();
        }
        throw new UserNotFoundException("It was not possible to find a Account with id: " + id);
    }

    public Customer getCustomerByAccountId(Long id) {
        return accountRepo.findByAccountId(id);
    }

    public void withdrawFromAccount(int amountToWithdraw, long id) throws UserNotFoundException {
        account = getAccountByID(id);
        int amount = account.getAmount();
        int finalAmount = amount - amountToWithdraw;
        account.setAmount(finalAmount);
        accountRepo.save(account);
    }

    public void depositToAccount(Integer amountToDeposit, Long id) throws UserNotFoundException {
        account = getAccountByID(id);
        int amount = account.getAmount();
        int finalAmount = amount + amountToDeposit;
        account.setAmount(finalAmount);
        accountRepo.save(account);
    }
}
