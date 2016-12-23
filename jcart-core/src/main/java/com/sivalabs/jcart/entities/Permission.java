/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "permissions")
@Data
public class Permission
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(length = 1024)
    private String description;
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}
