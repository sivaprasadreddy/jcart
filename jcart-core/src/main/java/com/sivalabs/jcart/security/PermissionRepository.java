package com.sivalabs.jcart.security;

import com.sivalabs.jcart.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siva
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>
{

}
