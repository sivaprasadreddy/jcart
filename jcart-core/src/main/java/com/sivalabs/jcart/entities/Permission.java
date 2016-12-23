/**
 * 
 */
package com.sivalabs.jcart.entities;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Permission implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 1024)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}
