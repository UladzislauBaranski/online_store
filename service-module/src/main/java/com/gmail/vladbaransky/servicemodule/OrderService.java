package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getOrdersByPage(int page);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    List<Integer> updateStatusById(List<Long> id, StatusEnum status);

    OrderDTO updateOrder(OrderDTO orderDTO);
}
