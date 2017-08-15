package com.sivalabs.jcart.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Siva
 *
 */
@Entity
@Table(name="permissions")
@Data
public class Permission
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false, unique=true)
	private String name;
	@Column(length=1024)
	private String description;
	@ManyToMany(mappedBy="permissions")
	private List<Role> roles;

}
