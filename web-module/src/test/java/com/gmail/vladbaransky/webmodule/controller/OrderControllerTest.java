package com.gmail.vladbaransky.webmodule.controller;

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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private OrderService orderService;

    //--------------------getOrdersByPage---------------------------------------
    @Test
    void getOrdersByPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }


    @Test
    void getIOrdersWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/orders")
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getItemsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/orders")
                .contentType(MediaType.TEXT_HTML)
                .param("page", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getItems_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/orders")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrdersByPage(eq(1));
    }

    @Test
    void getItems_returnItems() throws Exception {
        List<OrderDTO> orders = getAddedOrderListDTO();
        when(orderService.getOrdersByPage(1)).thenReturn(orders);
        MvcResult result = mockMvc.perform(get("/orders")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();
        verify(orderService, times(1)).getOrdersByPage(eq(1));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("1");
        Assertions.assertThat(expectedReturnedContent).contains("NEW");
        Assertions.assertThat(expectedReturnedContent).contains("25.5");
    }

    //----------------------getOrderById-------------------------------------------------

    @Test
    void getArticleById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticleWithInvalidId_returnBadRequest() throws Exception {
        mockMvc.perform(get("/orders/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticleById_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/orders/1")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderById(eq(1L));
    }

    @Test
    void getOrderById_returnOrder() throws Exception {
        OrderDTO order = getAddedOrderDTO();
        when(orderService.getOrderById(1L)).thenReturn(order);
        MvcResult result = mockMvc.perform(get("/orders/1")
                .contentType(MediaType.TEXT_HTML)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("1");
        Assertions.assertThat(expectedReturnedContent).contains("NEW");
        Assertions.assertThat(expectedReturnedContent).contains("25.5");
    }

    //----------------------refactorPage-------------------------------------------------

    @Test
    void getRefactorPage_returnStatusOk() throws Exception {
        mockMvc.perform(post("/orders/refactor")
                .param("id", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getRefactorPageWithParam_returnPage() throws Exception {

        MvcResult result = mockMvc.perform(post("/orders/refactor")
                .param("id", String.valueOf(1L))
                .contentType(MediaType.TEXT_HTML)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("1");
    }

    //----------------------refactorStatus-------------------------------------------------

    @Test
    void RefactorStatusWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/orders/refactor/status")
                .param("status", String.valueOf(StatusEnum.NEW))
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/orders"));
    }

    @Test
    void RefactorStatusWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/orders/refactor/status")
                .param("status", "inv")
                .param("id", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void RefactorStatus_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/orders/refactor/status")
                .param("status", String.valueOf(StatusEnum.NEW))
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/orders"));
        verify(orderService, times(1)).updateStatusById(eq(idList), eq(StatusEnum.NEW));
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
