/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sivalabs.jcart.entities;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Entity
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String sku;
    
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    
    @Getter
    @Setter
    private String description;
    
    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal price = new BigDecimal("0.0");
    
    @Getter
    @Setter
    private String imageUrl;
    
    @Getter
    @Setter
    private boolean disabled;
    
    @Getter
    @Setter
    @Temporal(TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn = new Date();

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

}
