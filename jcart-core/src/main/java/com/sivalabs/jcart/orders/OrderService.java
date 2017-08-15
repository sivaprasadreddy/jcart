package com.sivalabs.jcart.orders;

import com.sivalabs.jcart.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Siva
 *
 */
@Service
@Transactional
@Slf4j
public class OrderService
{
    private OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order createOrder(Order order)
	{
		//order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderNumber(String.valueOf(System.currentTimeMillis()));
		Order savedOrder = orderRepository.save(order);
		log.info("New order created. Order Number : {}", savedOrder.getOrderNumber());
		return savedOrder;
	}
	
	public Order getOrder(String orderNumber)
	{
		return orderRepository.findByOrderNumber(orderNumber);
	}

	public List<Order> getAllOrders()
	{
		Sort sort = new Sort(Direction.DESC, "createdOn");
		return orderRepository.findAll(sort);
	}

	public Order updateOrder(Order order) {
		Order o = getOrder(order.getOrderNumber());
		o.setStatus(order.getStatus());
		Order savedOrder = orderRepository.save(o);
		return savedOrder;
	}
	
	
}
