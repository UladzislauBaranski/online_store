package com.gmail.vladbaransky.servicemodule.util.converter;

import com.gmail.vladbaransky.repositorymodule.model.Item;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;

public class ItemConverter {
    public static Item getObjectFromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setTitle(itemDTO.getTitle());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setPrice(itemDTO.getPrice());
        item.setSummary(itemDTO.getSummary());
        return item;
    }

    public static ItemDTO getDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setTitle(item.getTitle());
        itemDTO.setUniqueNumber(item.getUniqueNumber());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setSummary(item.getSummary());
        return itemDTO;
    }
}
