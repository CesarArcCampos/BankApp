package com.mycompany.bankApp.service;

import com.mycompany.bankApp.exceptions.UserNotFoundException;
import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public void createNewCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public Customer getCustomerByID(Long id) throws UserNotFoundException {
        Optional<Customer> customer = customerRepo.findById(id);

        if(customer.isPresent()) {
            return customer.get();
        }
        throw new UserNotFoundException("It was not possible to find a Customer with id: " + id);
    }

    public Customer getCustomerByUsername(String username) throws UserNotFoundException {
        Customer customer = customerRepo.findByUsername(username);

        if(customer != null) {
            return customer;
        }
        throw new UserNotFoundException("Invalid Username");
    }

    public void deleteCustomer(Long id) throws UserNotFoundException {
        Optional<Customer> customer = customerRepo.findById(id);

        if(customer.isPresent()) {
            customerRepo.deleteById(id);
        }
        throw new UserNotFoundException("It was not possible to find a Customer with id: " + id);
    }
}
