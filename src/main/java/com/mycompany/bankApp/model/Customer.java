package com.mycompany.bankApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 45)
    private String firstname;

    @Column(nullable = false, length = 45)
    private String lastname;

    @Email
    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(nullable = false, length = 9, unique = true)
    private long NIF;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 45, unique = true)
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}
