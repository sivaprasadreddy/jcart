/**
 * 
 */
package com.sivalabs.jcart.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.Order;

/**
 * @author Siva
 *
 */
public interface OrderRepository extends JpaRepository<Order, Integer>
{
	Order findByOrderNumber(String orderNumber);
}
