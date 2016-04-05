/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author Siva
 *
 */
@Entity
@Table(name="orders")
public class Order implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false, unique=true)
	private String orderNumber;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
	private Set<OrderItem> items;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="cust_id")
	private Customer customer;
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="delivery_addr_id")
	private Address deliveryAddress;
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="billing_addr_id")
	private Address billingAddress;
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="payment_id")
	private Payment payment;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;
	
	public Order()
	{
		this.items = new HashSet<OrderItem>();
		this.status = OrderStatus.NEW;
		this.createdOn = new Date();
	}
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public Set<OrderItem> getItems()
	{
		return items;
	}
	public void setItems(Set<OrderItem> items)
	{
		this.items = items;
	}
	public Customer getCustomer()
	{
		return customer;
	}
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	public Address getDeliveryAddress()
	{
		return deliveryAddress;
	}
	public void setDeliveryAddress(Address deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}
	public Payment getPayment()
	{
		return payment;
	}
	public void setPayment(Payment payment)
	{
		this.payment = payment;
	}
	public OrderStatus getStatus()
	{
		return status;
	}
	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}
	public Date getCreatedOn()
	{
		return createdOn;
	}
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	
	public BigDecimal getTotalAmount()
	{
		BigDecimal amount = new BigDecimal("0.0");
		for (OrderItem item : items)
		{
			amount = amount.add(item.getSubTotal());
		}
		return amount;
	}
	
}
