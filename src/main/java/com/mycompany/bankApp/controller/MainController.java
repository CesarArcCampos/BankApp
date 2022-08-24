package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

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
            return "redirect:/customer/page";
        } else {
            String errorMessage = "Invalid Password";
            model.addAttribute("errorMessage", errorMessage);
            return "indexError";
        }
    }
}
