package com.sivalabs.jcart.orders;

import com.sivalabs.jcart.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siva
 *
 */
public interface OrderRepository extends JpaRepository<Order, Integer>
{
	Order findByOrderNumber(String orderNumber);
}
