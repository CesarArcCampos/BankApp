package com.mycompany.bankApp.controller;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.security.CustomUserDetails;
import com.mycompany.bankApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public boolean flag = true;
    public Date createDate;

    @GetMapping("/customer/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @GetMapping("/customer/page/{id}")
    public String customerPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws UserNotFoundException {
        //Customer customer = customerService.getCustomerByID(id);
        //String message = "Welcome " + customer.getFirstname() + ".";
        //model.addAttribute("message", message);
        //String customerID = id.toString();
        //model.addAttribute("customerID", customerID);

        System.out.println("*********************************");
        System.out.println("Entrou");
        System.out.println("*********************************");

        //Customer customer = userDetails.getCustomer();
        //String customerID = String.valueOf(customer.getId());
        //model.addAttribute("customerID", customerID);

        return "customerPage";
    }

    @PostMapping("/customer/page/{id}")
    public String customerPage() {
        return "customerPage";
    }

    @PostMapping("/customer/save")
    public String saveNewCustomer(Customer customer) {

        if(flag) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(customer.getPassword());
            customer.setPassword(passwordEncoded);
            customerService.createNewCustomer(customer);
            return "redirect:/login";
        } else {

            flag = false;
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(customer.getPassword());
            customer.setPassword(passwordEncoded);
            customer.setCreateDate(createDate);
            customerService.createNewCustomer(customer);
            long customerId = customer.getId();
            return "redirect:/customer/page/" + customerId;
        }
    }

    @GetMapping("/customer/edit/")
    public String showEditCustomerPage(@AuthenticationPrincipal CustomUserDetails user, Model model) throws UserNotFoundException {
        Customer customer = user.getCustomer();
        model.addAttribute("customer", customer);
        createDate = customer.getCreateDate();
        flag = false;
        return "customer-form";
    }
}
