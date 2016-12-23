/**
 * 
 */
package com.sivalabs.jcart.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Entity
@Table(name = "payments")
@Data
public class Payment implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cc_number")
    private String ccNumber;
    private String cvv;
    private BigDecimal amount;

}
