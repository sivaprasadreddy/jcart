package com.sivalabs.jcart.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Siva
 *
 */
@Entity
@Table(name="addresses")
@Data
public class Address implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	
}
