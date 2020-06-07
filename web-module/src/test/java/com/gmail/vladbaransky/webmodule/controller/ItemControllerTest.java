package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.ItemService;
import com.gmail.vladbaransky.servicemodule.OrderService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    //-----------------------getItemList--------------------------------
    @Test
    void getItems_returnStatusOk() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    void getItemsWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/items")
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getItemsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/items")
                .contentType(MediaType.TEXT_HTML)
                .param("page", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getItems_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/items")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItemsByPage(eq(1));
    }

    @Test
    void getItems_returnItems() throws Exception {
        List<ItemDTO> items = getAddedItemListDTO();
        when(itemService.getItemsByPage(1)).thenReturn(items);
        MvcResult result = mockMvc.perform(get("/items")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("1");
        Assertions.assertThat(expectedReturnedContent).contains("title");
        Assertions.assertThat(expectedReturnedContent).contains("25.5");
    }

    //------------------------getItemById---------------------------------------------

    @Test
    void getItemById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getItemWithInvalidId_returnBadRequest() throws Exception {
        mockMvc.perform(get("/items/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getItemById_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItemById(eq(1L));
    }

    @Test
    void getItemById_returnArticles() throws Exception {
        ItemDTO item = getAddedItemDTO();
        when(itemService.getItemById(1L)).thenReturn(item);
        MvcResult result = mockMvc.perform(get("/items/1")
                .contentType(MediaType.TEXT_HTML)).andReturn();
        verify(itemService, times(1)).getItemById(eq(1L));
        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("title");
        Assertions.assertThat(expectedReturnedContent).contains("25.5");
    }

    //-----------------------------deleteItems-----------------------------------------------------------

    @Test
    void deleteItems_returnStatusOk() throws Exception {
        mockMvc.perform(post("/items/delete")
                .param("id", "1"))
                .andExpect(redirectedUrl("/items"));
    }

    @Test
    void deleteItemsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/items/delete")
                .param("id", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteItems_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/items/delete")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/items"));
        verify(itemService, times(1)).deleteItemByIdList(eq(idList));
    }

    //---------------------------addOrder------------------------------------
    @Test
    void addOrderWithParam_returnStatusOk() throws Exception {
        UserDTO user = getAddedUserDTO();
        ItemDTO item = getAddedItemDTO();
        when(userService.getProfileCurrentUser()).thenReturn(user);
        when(itemService.getItemById(1L)).thenReturn(item);
        mockMvc.perform(post("/items")
                .param("id", String.valueOf(1L))
                .param("number", String.valueOf(1L)))
                .andExpect(redirectedUrl("/orders"));
    }

    @Test
    void getArticlesWithInvalidParam_returnBadRequest() throws Exception {
        UserDTO user = getAddedUserDTO();
        ItemDTO item = getAddedItemDTO();
        when(userService.getProfileCurrentUser()).thenReturn(user);
        when(itemService.getItemById(1L)).thenReturn(item);
        mockMvc.perform(post("/items")
                .param("id", "inv")
                .param("number", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticles_callBusinessLogic() throws Exception {
        UserDTO user = getAddedUserDTO();
        ItemDTO item = getAddedItemDTO();
        OrderDTO order = getAddedOrderDTO();
        when(userService.getProfileCurrentUser()).thenReturn(user);
        when(itemService.getItemById(1L)).thenReturn(item);
        mockMvc.perform(post("/items")
                .param("id", String.valueOf(1L))
                .param("number", String.valueOf(1L)))
                .andExpect(redirectedUrl("/orders"));
        verify(userService, times(1)).getProfileCurrentUser();
        verify(itemService, times(1)).getItemById(eq(1L));
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
        orderDTO.setItems(getAddedItemListDTO());
        return orderDTO;
    }

    private ItemDTO getItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitle("title");
        itemDTO.setUniqueNumber(1L);
        itemDTO.setPrice(new BigDecimal(25.5));
        itemDTO.setSummary("summary");
        return itemDTO;
    }

    private ItemDTO getAddedItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        itemDTO.setTitle("title");
        itemDTO.setUniqueNumber(1L);
        itemDTO.setPrice(new BigDecimal(25.5));
        itemDTO.setSummary("summary");
        return itemDTO;
    }

    private List<ItemDTO> getAddedItemListDTO() {
        List<ItemDTO> items = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            ItemDTO item = getItemDTO();
            item.setId(i);
            items.add(item);
        }
        return items;
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
