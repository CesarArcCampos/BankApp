package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.model.Log;
import com.mycompany.bankApp.service.AccountService;
import com.mycompany.bankApp.service.CustomerService;
import com.mycompany.bankApp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Autowired
    LogService logService;

    @PostMapping("/account/{customerID}")
    public String saveNewAccount(Account account, @PathVariable("customerID") Long id) throws UserNotFoundException {
        Customer customer = customerService.getCustomerByID(id);
        account.setCustomer(customer);
        accountService.createNewAccount(account);

        int amount = account.getAmount();

        logService.newLog("Create Account", amount, new Log(), account);

        return "redirect:/customer/page/" + id;
    }

    @GetMapping("/checkAccounts/{customerID}")
    public String checkAccountsList(@PathVariable("customerID") Long id, Model model) {

        List<Account> accounts = accountService.getListAccounts(id);
        model.addAttribute("accounts", accounts);
        model.addAttribute("customerID", id);

        return "accountList";
    }

    @GetMapping("/account/withdraw/{customerID}/{accountID}")
    public String showWithdrawPage(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model) throws UserNotFoundException {
        Account account = accountService.getAccountByID(id);
        model.addAttribute("account", account);
        model.addAttribute("customerID", customerID);

        return "withdraw-form";
    }

    @PostMapping("/account/withdraw/{customerID}/{accountID}")
    public String withdrawFromAccount(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model, Integer amount) throws UserNotFoundException {

        accountService.withdrawFromAccount(amount, id);
        model.addAttribute("customerID", customerID);

        Account account = accountService.getAccountByID(id);
        logService.newLog("Withdraw", - amount, new Log(), account);

        return "redirect:/checkAccounts/" + customerID;
    }

    @GetMapping("/account/deposit/{customerID}/{accountID}")
    public String showDepositPage(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model) throws UserNotFoundException {
        Account account = accountService.getAccountByID(id);
        model.addAttribute("account", account);
        model.addAttribute("customerID", customerID);
        return "deposit-form";
    }

    @PostMapping("/account/deposit/{customerID}/{accountID}")
    public String depositToAccount(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model, Integer amount) throws UserNotFoundException {

        accountService.depositToAccount(amount, id);
        model.addAttribute("customerID", customerID);

        Account account = accountService.getAccountByID(id);
        logService.newLog("Deposit", amount, new Log(), account);

        return "redirect:/checkAccounts/" + customerID;
    }

    @GetMapping("/account/transfer/{customerID}/{accountID}")
    public String showTransferPage(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model) throws UserNotFoundException {
        Account account = accountService.getAccountByID(id);
        model.addAttribute("account", account);
        model.addAttribute("customerID", customerID);

        return "transfer-form";
    }

    @PostMapping("/account/transfer/{customerID}/{accountID}")
    public String transferToAccount(@PathVariable("accountID") Long idOrigin, @PathVariable("customerID") Long customerID, Model model, Integer amount, Long id) throws UserNotFoundException {

        accountService.transferToAccount(amount, idOrigin, id);
        model.addAttribute("customerID", customerID);

        Account account = accountService.getAccountByID(idOrigin);
        logService.newLog("Transfer", - amount, new Log(), account);

        Account accountTarget = accountService.getAccountByID(id);
        logService.newLog("Transfer", amount, new Log(), accountTarget);

        return "redirect:/checkAccounts/" + customerID;
    }

    @GetMapping("/account/logs/{customerID}/{accountID}")
    public String showLogsPage(@PathVariable("accountID") Long id, @PathVariable("customerID") Long customerID, Model model) throws UserNotFoundException {
        List<Log> logs = logService.getLogsByAccountId(id);
        model.addAttribute("logs", logs);

        int balance = accountService.getAccountByID(id).getAmount();
        String message = "Balance: " + balance;

        model.addAttribute("message", message);

        model.addAttribute("customerID", customerID);
        return "logs";
    }

    @GetMapping("/accountForm/{customerID}")
    public String showAccountForm(Model model, @PathVariable("customerID") Long id ) {
        model.addAttribute("account", new Account());

        String customerID = id.toString();
        model.addAttribute("customerID", customerID);

        return "account-form";
    }
}
