/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * @author Siva
 * @author rajakolli
 * Using @Data annotation to generate Setters and getters
 *
 */
@Entity
@Table(name="categories")
@Data
public class Category
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;
	@Column(length=1024)
	private String description;
	@Column(name="disp_order")
	private Integer displayOrder;
	private boolean disabled;
	@OneToMany(mappedBy="category")
	private Set<Product> products;
	
}
