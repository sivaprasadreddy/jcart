package com.sivalabs.jcart.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Siva
 *
 */
@Entity
@Table(name="categories")
@Data
@EqualsAndHashCode(exclude = "products")
public class Category
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
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
