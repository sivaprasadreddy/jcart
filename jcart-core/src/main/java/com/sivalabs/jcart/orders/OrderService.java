/**
 * 
 */
package com.sivalabs.jcart.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */

@Slf4j
@Service
@Transactional
public class OrderService
{
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order)
    {
        // order.setOrderNumber(UUID.randomUUID().toString());
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

    public Order updateOrder(Order order)
    {
        Order o = getOrder(order.getOrderNumber());
        o.setStatus(order.getStatus());
        Order savedOrder = orderRepository.save(o);
        log.info("Updated Order with Order Number : {}", savedOrder.getOrderNumber());
        return savedOrder;
    }

}
