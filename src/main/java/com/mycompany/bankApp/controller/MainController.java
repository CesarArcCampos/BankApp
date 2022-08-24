package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/customer/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @GetMapping("/customer/page")
    public String customerPage() {
        return "customerPage";
    }

    @PostMapping("/customer/save")
    public String saveNewCustomer(Customer customer) {
        customerService.createNewCustomer(customer);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String submitLogin(String username, String password) {

        Customer customerByUsername = null;
        try {
            customerByUsername = customerService.getCustomerByUsername(username);
        } catch (UserNotFoundException exception) {
            return "redirect:/login";
        }
        if (password.equals(customerByUsername.getPassword())) {
            return "redirect:/customer/page";
        } else {
            return "redirect:/login";
        }
    }
}
