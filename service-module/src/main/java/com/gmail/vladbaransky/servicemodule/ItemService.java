package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.servicemodule.model.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getItemsByPage(int page);

    ItemDTO getItemById(Long id);

    List<Integer> deleteItemByIdList(List<Long> ids);

    Integer deleteItemById(Long id);

    List<ItemDTO> getAllItems();

    ItemDTO addItems(ItemDTO itemDTO);
}
