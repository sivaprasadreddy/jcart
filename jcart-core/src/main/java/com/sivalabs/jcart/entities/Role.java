package com.sivalabs.jcart.entities;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author Siva
 *
 */
@Entity
@Table(name="roles")
@Data
public class Role
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;
	@Column(length=1024)
	private String description;
		
	@ManyToMany(mappedBy="roles")
	private List<User> users;

	@ManyToMany
	  @JoinTable(
	      name="role_permission",
	      joinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")},
	      inverseJoinColumns={@JoinColumn(name="PERM_ID", referencedColumnName="ID")})
	  private List<Permission> permissions;
	
}
