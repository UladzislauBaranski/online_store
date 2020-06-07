package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.ItemRepository;
import com.gmail.vladbaransky.repositorymodule.model.Item;
import com.gmail.vladbaransky.servicemodule.impl.ItemServiceImpl;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    private ItemService itemService;
    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup() {
        this.itemService = new ItemServiceImpl(itemRepository);
    }

    @Test
    public void getItemsByPage_returnItems() {
        List<Item> items = getAddedItemList();

        when(itemRepository.getObjectByPage(0, 10)).thenReturn(items);
        List<ItemDTO> returnedItems = itemService.getItemsByPage(1);
        verify(itemRepository, times(1)).getObjectByPage(0, 10);
        for (int i = 0; i < items.size(); i++) {
            getAssertion(returnedItems.get(i), items.get(i));
        }
    }

    @Test
    public void deleteItemById_returnStatus() {
        Long id = 1L;
        Integer status = 1;
        when(itemRepository.deleteObjectById(id)).thenReturn(status);
        Integer returnedStatus = itemService.deleteItemById(id);
        verify(itemRepository, times(1)).deleteObjectById(id);
        assertThat(returnedStatus).isNotNull();
        assertThat(returnedStatus).isEqualTo(status);
    }

    @Test
    public void deleteItemsByIdList_returnStatus() {
        List<Long> idList = getIdList();
        List<Integer> status = getStatus();
        when(itemRepository.deleteObjectByIdList(idList)).thenReturn(status);
        List<Integer> returnedStatus = itemService.deleteItemByIdList(idList);
        verify(itemRepository, times(1)).deleteObjectByIdList(idList);
        assertThat(returnedStatus).isNotNull();
        assertThat(returnedStatus).isEqualTo(status);
    }

    @Test
    public void getAllItems_returnItems() {
        List<Item> items = getAddedItemList();

        when(itemRepository.getAllObject()).thenReturn(items);
        List<ItemDTO> returnedItems = itemService.getAllItems();
        verify(itemRepository, times(1)).getAllObject();
        for (int i = 0; i < items.size(); i++) {
            getAssertion(returnedItems.get(i), items.get(i));
        }
    }

    @Test
    public void getItemById_returnItem() {
        Long id = 1L;
        Item item = getAddedItem();

        when(itemRepository.getObjectById(id)).thenReturn(item);
        ItemDTO returnedItem = itemService.getItemById(id);
        verify(itemRepository, times(1)).getObjectById(id);

        getAssertion(returnedItem, item);
    }

    @Test
    public void addItem_returnItem() {
        Item item = getItem();
        Item addedItem = getAddedItem();
        ItemDTO itemDTO = getItemDTO();

        when(itemRepository.addObject(item)).thenReturn(addedItem);
        ItemDTO returnedItem = itemService.addItems(itemDTO);
        verify(itemRepository, times(1)).addObject(item);

        getAssertion(returnedItem, addedItem);
    }

    private Item getItem() {
        Item item = new Item();
        item.setTitle("title");
        item.setUniqueNumber(111L);
        item.setPrice(new BigDecimal(25.5));
        item.setSummary("summary");
        return item;
    }

    private Item getAddedItem() {
        Item item = new Item();
        item.setId(1L);
        item.setTitle("title");
        item.setUniqueNumber(111L);
        item.setPrice(new BigDecimal(25.5));
        item.setSummary("summary");
        return item;
    }

    private ItemDTO getItemDTO() {
        ItemDTO itemDTO= new ItemDTO();
        itemDTO.setTitle("title");
        itemDTO.setUniqueNumber(111L);
        itemDTO.setPrice(new BigDecimal(25.5));
        itemDTO.setSummary("summary");
        return itemDTO;
    }

    private List<Item> getAddedItemList() {
        List<Item> items = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Item item = getItem();
            item.setId(i);
            items.add(item);
        }
        return items;
    }

    private void getAssertion(ItemDTO returnedItem, Item item) {
        assertThat(returnedItem).isNotNull();
        assertThat(returnedItem.getId()).isEqualTo(item.getId());
        assertThat(returnedItem.getTitle()).isEqualTo(item.getTitle());
        assertThat(returnedItem.getUniqueNumber()).isEqualTo(item.getUniqueNumber());
        assertThat(returnedItem.getPrice()).isEqualTo(item.getPrice());
        assertThat(returnedItem.getSummary()).isEqualTo(item.getSummary());
    }
}
