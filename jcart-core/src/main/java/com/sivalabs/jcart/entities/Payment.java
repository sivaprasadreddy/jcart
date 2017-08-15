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
@Table(name="payments")
@Data
public class Payment implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="cc_number")
	private String ccNumber;
	private String cvv;
	private BigDecimal amount;
		
}
