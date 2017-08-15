package com.sivalabs.jcart.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Siva
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
