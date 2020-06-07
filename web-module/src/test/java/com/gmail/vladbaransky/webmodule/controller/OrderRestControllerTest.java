package com.gmail.vladbaransky.webmodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.OrderService;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderRestController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    //---------------------------getAllOrders------------------------------------
    @Test
    void getAllOrders_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrders_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getAllOrders_returnOrders() throws Exception {
        List<OrderDTO> orders = getAddedOrderListDTO();
        when(orderService.getAllOrders()).thenReturn(orders);
        MvcResult result = mockMvc.perform(get("/api/orders")).andReturn();
        verify(orderService, times(1)).getAllOrders();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(orders);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //---------------------------getOrderById------------------------------------
    @Test
    void getOrder_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/api/orders/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOrder_callBusinessLogic() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderById(eq(id));
    }

    @Test
    void getOrder_returnOrder() throws Exception {
        Long id = 1L;
        OrderDTO order = getAddedOrderDTO();
        when(orderService.getOrderById(id)).thenReturn(order);
        MvcResult result = mockMvc.perform(get("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        verify(orderService, times(1)).getOrderById(eq(id));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(order);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //--------------------------------------------------------------------------------
    private OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(1L);
        orderDTO.setDate(LocalDate.now());
        orderDTO.setStatus(StatusEnum.NEW);
        orderDTO.setNumberOfItems(1L);
        orderDTO.setTotalPrice(new BigDecimal(25.5));
        return orderDTO;
    }

    private OrderDTO getAddedOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderNumber(1L);
        orderDTO.setDate(LocalDate.now());
        orderDTO.setStatus(StatusEnum.NEW);
        orderDTO.setNumberOfItems(1L);
        orderDTO.setTotalPrice(new BigDecimal(25.5));
        orderDTO.setUserDTO(getAddedUserDTO());
        return orderDTO;
    }

    private List<OrderDTO> getAddedOrderListDTO() {
        List<OrderDTO> orders = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            OrderDTO order = getOrderDTO();
            order.setId(i);
            orders.add(order);
        }
        return orders;
    }

    private UserDTO getAddedUserDTO() {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setUsername("username");
        user.setRole(RoleEnum.SALE_USER);
        return user;
    }
}
