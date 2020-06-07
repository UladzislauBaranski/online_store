package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.ItemRepository;
import com.gmail.vladbaransky.repositorymodule.model.Item;
import com.gmail.vladbaransky.servicemodule.ItemService;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import com.gmail.vladbaransky.servicemodule.util.converter.ItemConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private static final int OBJECT_BY_PAGE = 10;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public List<ItemDTO> getItemsByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<Item> items = itemRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return items.stream()
                .map(ItemConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Integer> deleteItemByIdList(List<Long> ids) {
        return itemRepository.deleteObjectByIdList(ids);
    }

    @Transactional
    @Override
    public Integer deleteItemById(Long id) {
        return itemRepository.deleteObjectById(id);
    }

    @Transactional
    @Override
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.getObjectById(id);
        return ItemConverter.getDTOFromObject(item);
    }

    @Transactional
    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.getAllObject();
        return items.stream()
                .map(ItemConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ItemDTO addItems(ItemDTO itemDTO) {
        Item item = ItemConverter.getObjectFromDTO(itemDTO);
        Item returnedItem = itemRepository.addObject(item);
        return ItemConverter.getDTOFromObject(returnedItem);
    }
}

