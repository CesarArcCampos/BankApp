package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Account;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.service.AccountService;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    public boolean flag = true;
    public Date createDate;

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "indexError";
    }

    @GetMapping("/customer/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @GetMapping("/customer/page/{id}")
    public String customerPage(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        Customer customer = customerService.getCustomerByID(id);
        String message = "Welcome " + customer.getFirstname() + ".";
        model.addAttribute("message", message);
        String customerID = id.toString();
        model.addAttribute("customerID", customerID);
        return "customerPage";
    }

    @PostMapping("/customer/page/{id}")
    public String customerPage() {
        return "customerPage";
    }

    @PostMapping("/customer/save")
    public String saveNewCustomer(Customer customer) {

        if(flag) {
            customerService.createNewCustomer(customer);
            return "redirect:/login";
        } else {
            flag = false;
            customer.setCreateDate(createDate);
            customerService.createNewCustomer(customer);
            long customerId = customer.getId();
            return "redirect:/customer/page/" + customerId;
        }

    }

    @PostMapping("/login")
    public String submitLogin(String username, String password, Model model) {

        Customer customerByUsername = null;

        try {
            customerByUsername = customerService.getCustomerByUsername(username);
        } catch (UserNotFoundException exception) {
            String errorMessage = "Invalid Username";
            model.addAttribute("errorMessage", errorMessage);
            return "indexError";
        }
        if (password.equals(customerByUsername.getPassword())) {
            return "redirect:/customer/page/" + customerByUsername.getId();
        } else {
            String errorMessage = "Invalid Password";
            model.addAttribute("errorMessage", errorMessage);
            return "indexError";
        }
    }

    @GetMapping("/accountForm/{customerID}")
    public String showAccountForm(Model model, @PathVariable("customerID") Long id ) {
        model.addAttribute("account", new Account());

        String customerID = id.toString();
        model.addAttribute("customerID", customerID);

        return "account-form";
    }

    @PostMapping("/account/{customerID}")
    public String saveNewAccount(Account account, @PathVariable("customerID") Long id) throws UserNotFoundException {
        Customer customer = customerService.getCustomerByID(id);
        account.setCustomer(customer);
        accountService.createNewAccount(account);

        return "redirect:/customer/page/" + id;
    }

    @GetMapping("/checkAccounts/{customerID}")
    public String checkAccountsList(@PathVariable("customerID") Long id, Model model) {

        List<Account> accounts = accountService.getListAccounts(id);
        model.addAttribute("accounts", accounts);
        model.addAttribute("customerID", id);

        return "accountList";
    }

    @GetMapping("/customer/edit/{customerID}")
    public String showEditCustomerPage(@PathVariable("customerID") Long id, Model model) throws UserNotFoundException {
        Customer customer = customerService.getCustomerByID(id);
        model.addAttribute("customer", customer);
        createDate = customer.getCreateDate();
        flag = false;
        return "customer-form";
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

        return "redirect:/checkAccounts/" + customerID;
    }
}
