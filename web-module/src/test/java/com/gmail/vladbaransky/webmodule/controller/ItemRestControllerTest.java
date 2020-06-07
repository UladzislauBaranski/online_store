package com.gmail.vladbaransky.webmodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.servicemodule.ItemService;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRestController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemRestControllerTest {
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
    private ItemService itemService;

    //---------------------------getAllArticles------------------------------------
    @Test
    void getAllItems_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllItems_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void getAllItems_returnArticles() throws Exception {
        List<ItemDTO> items = getAddedItemListDTO();
        when(itemService.getAllItems()).thenReturn(items);
        MvcResult result = mockMvc.perform(get("/api/items")).andReturn();
        verify(itemService, times(1)).getAllItems();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(items);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //---------------------------getItemById------------------------------------
    @Test
    void getArticles_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticlesWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/api/items/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticles_callBusinessLogic() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItemById(eq(id));
    }

    @Test
    void getArticles_returnArticles() throws Exception {
        Long id = 1L;
        ItemDTO item = getAddedItemDTO();
        when(itemService.getItemById(id)).thenReturn(item);
        MvcResult result = mockMvc.perform(get("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(item);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //---------------------------addItems------------------------------------
    @Test
    void addItems_returnStatusOk() throws Exception {
        ItemDTO item = getItemDTO();
        String content = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addItems_callBusinessLogic() throws Exception {
        ItemDTO item = getItemDTO();
        String content = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addItems_returnItems() throws Exception {

        ItemDTO item = getItemDTO();
        String content = objectMapper.writeValueAsString(item);
        ItemDTO returnedItem = getAddedItemDTO();

        when(itemService.addItems(item)).thenReturn(returnedItem);
        MvcResult result = mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();
        verify(itemService, times(1)).addItems(eq(item));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String returnedContent = objectMapper.writeValueAsString(returnedItem);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //-----------------------------deleteArticle-----------------------------------------------------------
    @Test
    void deleteItems_returnStatusOk() throws Exception {
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItemsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/items/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteItems_callBusinessLogic() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isOk());
        verify(itemService, times(1)).deleteItemById(eq(id));
    }

    @Test
    void deleteItems_returnItems() throws Exception {
        Long id = 1L;
        Integer status = 1;
        when(itemService.deleteItemById(id)).thenReturn(status);
        MvcResult result = mockMvc.perform(delete("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        verify(itemService, times(1)).deleteItemById(eq(id));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String expectedStatus = objectMapper.writeValueAsString(status);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(expectedStatus);
    }
    //-------------------------------------------------------------------------------

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
}
