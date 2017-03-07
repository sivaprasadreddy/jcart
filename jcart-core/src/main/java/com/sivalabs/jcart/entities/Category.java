/**
 * 
 */
package com.sivalabs.jcart.entities;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "categories")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
public class Category implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(name = "disp_order")
    private Integer displayOrder;

    private boolean disabled;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

}
