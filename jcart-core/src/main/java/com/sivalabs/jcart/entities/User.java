package com.sivalabs.jcart.entities;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "users")
@Data
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @NotEmpty()
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(min = 4)
    @Column(nullable = false)
    private String password;

    private String passwordResetToken;

    @ManyToMany(cascade = MERGE)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
    private List<Role> roles;

}
