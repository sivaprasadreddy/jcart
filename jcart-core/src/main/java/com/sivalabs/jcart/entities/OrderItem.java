/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Siva
 * @author rajakolli
 * Using @Data annotation to generate Setters and getters
 *
 */
@Entity
@Table(name="order_items")
@Data
public class OrderItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	private BigDecimal price;
	private int quantity;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	public BigDecimal getSubTotal()
	{
		return product.getPrice().multiply(new BigDecimal(quantity));
	}
	
}
