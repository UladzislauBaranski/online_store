package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.OrderRepository;
import com.gmail.vladbaransky.repositorymodule.model.Order;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.OrderService;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import com.gmail.vladbaransky.servicemodule.util.converter.OrderConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final int OBJECT_BY_PAGE = 10;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> getOrdersByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<Order> returnedOrders = orderRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return returnedOrders.stream()
                .map(OrderConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.getAllObject();
        return orders.stream()
                .map(OrderConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Integer> updateStatusById(List<Long> id, StatusEnum status) {
        return orderRepository.updateStatusById(id, status);
    }

    @Transactional
    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order order = OrderConverter.getObjectFromDTO(orderDTO);
        Order returnedOrder = orderRepository.updateObject(order);
        return OrderConverter.getDTOFromObject(returnedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.getObjectById(id);
        return OrderConverter.getDTOFromObject(order);
    }
}
