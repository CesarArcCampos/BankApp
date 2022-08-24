package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/customer/save")
    public String saveNewCustomer(Customer customer) {
        customerService.createNewCustomer(customer);
        return "redirect:/login";
    }
}
