/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;
    @Column(length = 1024)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "PERM_ID", referencedColumnName = "ID") })
    private List<Permission> permissions;

}
