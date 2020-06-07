package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.servicemodule.ItemService;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemRestController {
   private final ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemDTO getItemsById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    public ItemDTO addItems(@RequestBody ItemDTO item) {
        return itemService.addItems(item);
    }

    @DeleteMapping("/{id}")
    public Integer deleteItems(@PathVariable Long id) {
        return itemService.deleteItemById(id);
    }
}
