package com.sivalabs.jcart.orders;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private OrderRepository orderRepository;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param orderRepository
     */
    public OrderService(OrderRepository orderRepository)
    {
        super();
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order)
    {
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
        Sort sort = new Sort(DESC, "createdOn");
        return orderRepository.findAll(sort);
    }

    public Order updateOrder(Order order)
    {
        Order persistOrder = getOrder(order.getOrderNumber());
        persistOrder.setStatus(order.getStatus());
        Order savedOrder = orderRepository.save(persistOrder);
        log.info("Updated Order with Order Number : {}", savedOrder.getOrderNumber());
        return savedOrder;
    }

}
