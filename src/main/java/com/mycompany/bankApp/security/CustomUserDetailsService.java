package com.mycompany.bankApp.security;

import com.mycompany.bankApp.model.Customer;
import com.mycompany.bankApp.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepo.findByUsername(username);

        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found.");
        }

        return new CustomUserDetails(customer);
    }
}
