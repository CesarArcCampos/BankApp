package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "indexError";
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

}
