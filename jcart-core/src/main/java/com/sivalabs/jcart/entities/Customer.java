/**
 * 
 */
package com.sivalabs.jcart.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "customers")
@Data
public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "firstname", nullable = false)
    @NotEmpty
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    private String phone;

}
