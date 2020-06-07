package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.OrderRepository;
import com.gmail.vladbaransky.repositorymodule.model.Order;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.impl.OrderServiceImpl;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        this.orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    public void getOrdersByPage_returnOrders() {
        List<Order> orders = getAddedOrderList();
        when(orderRepository.getObjectByPage(0, 10)).thenReturn(orders);
        List<OrderDTO> returnedOrders = orderService.getOrdersByPage(1);
        verify(orderRepository, times(1)).getObjectByPage(0, 10);
        for (int i = 0; i < returnedOrders.size(); i++) {
            getAssertion(returnedOrders.get(i), orders.get(i));
        }
    }

    @Test
    public void getAllOrders_returnOrders() {
        List<Order> orders = getAddedOrderList();
        when(orderRepository.getAllObject()).thenReturn(orders);
        List<OrderDTO> returnedOrders = orderService.getAllOrders();
        verify(orderRepository, times(1)).getAllObject();
        for (int i = 0; i < returnedOrders.size(); i++) {
            getAssertion(returnedOrders.get(i), orders.get(i));
        }
    }

    @Test
    public void getOrderById_returnOrder() {
        Order order = getAddedOrder();
        Long id = order.getId();
        when(orderRepository.getObjectById(id)).thenReturn(order);
        OrderDTO returnedOrder = orderService.getOrderById(id);
        verify(orderRepository, times(1)).getObjectById(id);
        getAssertion(returnedOrder, order);
    }

 /*   @Test
    public void updateOrder_returnOrder() {
        Order order = getOrder();
        Order addedOrder = getAddedOrder();
        OrderDTO orderDTO = getOrderDTO();
        when(orderRepository.updateObject(order)).thenReturn(addedOrder);
        OrderDTO returnedOrderDTO = orderService.updateOrder(orderDTO);
        verify(orderRepository, times(1)).updateObject(order);
        getAssertion(returnedOrderDTO,addedOrder);
    }*/

    @Test
    public void updateStatusById_returnListResult() {
        List<Long> idList=getIdList();
        List<Integer> status = getStatus();
        when(orderRepository.updateStatusById(idList, StatusEnum.NEW)).thenReturn(status);
        List<Integer> returnedStatus = orderService.updateStatusById(idList, StatusEnum.NEW);
        verify(orderRepository, times(1)).updateStatusById(idList, StatusEnum.NEW);
        for (int i = 0; i < idList.size(); i++) {
            assertThat(returnedStatus.get(i)).isNotNull();
            assertThat(returnedStatus.get(i)).isEqualTo(status.get(i));
        }
    }

    private Order getOrder() {
        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setOrderNumber(1L);
        order.setStatus(StatusEnum.NEW);
        order.setNumberOfItems(1L);
        order.setTotalPrice(new BigDecimal(25.5));
        return order;
    }

    private Order getAddedOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setDate(LocalDate.now());
        order.setOrderNumber(1L);
        order.setStatus(StatusEnum.NEW);
        order.setNumberOfItems(1L);
        order.setTotalPrice(new BigDecimal(25.5));
        return order;
    }

    private OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDate(LocalDate.now());
        orderDTO.setOrderNumber(1L);
        orderDTO.setStatus(StatusEnum.NEW);
        orderDTO.setNumberOfItems(1L);
        orderDTO.setTotalPrice(new BigDecimal(25.5));
        return orderDTO;
    }

    private List<Order> getAddedOrderList() {
        List<Order> orders = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Order order = getOrder();
            order.setId(i);
            orders.add(order);
        }
        return orders;
    }

    private void getAssertion(OrderDTO returnedOrder, Order order) {
        assertThat(returnedOrder).isNotNull();
        assertThat(returnedOrder.getId()).isEqualTo(order.getId());
        assertThat(returnedOrder.getDate()).isEqualTo(order.getDate());
        assertThat(returnedOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(returnedOrder.getNumberOfItems()).isEqualTo(order.getNumberOfItems());
        assertThat(returnedOrder.getTotalPrice()).isEqualTo(order.getTotalPrice());
    }
}
