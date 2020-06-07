package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.ItemService;
import com.gmail.vladbaransky.servicemodule.OrderService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.VALUE_IF_DO_NOT_SELECTED;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;
    private final OrderService orderService;

    public ItemController(ItemService itemService, UserService userService, OrderService orderService) {
        this.itemService = itemService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getItemList(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page, Model model) {
        List<ItemDTO> items = itemService.getItemsByPage(page);
        model.addAttribute("items", items);
        return "items_page";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        ItemDTO item = itemService.getItemById(id);
        model.addAttribute("item", item);
        return "item_page";
    }

    @PostMapping("/delete")
    public String deleteItems(@RequestParam(defaultValue = VALUE_IF_DO_NOT_SELECTED) List<Long> id) {
        itemService.deleteItemByIdList(id);
        return "redirect:/items";
    }

    @PostMapping
    public String addOrder(@RequestParam(value = "id") Long id,
                           @RequestParam(name = "number") Long number) {
        OrderDTO orderDTO = new OrderDTO();
        LocalDate date = LocalDate.now();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        UserDTO user = userService.getProfileCurrentUser();
        orderDTO.setUserDTO(user);
        ItemDTO item = itemService.getItemById(id);
        itemDTOList.add(item);
        orderDTO.setItems(itemDTOList);
        orderDTO.setOrderNumber(orderDTO.getUserDTO().getId() + item.getId());
        orderDTO.setStatus(StatusEnum.NEW);
        orderDTO.setDate(date);
        orderDTO.setNumberOfItems(number);
        orderDTO.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(number)));
        orderService.updateOrder(orderDTO);
        return "redirect:/orders";
    }
}
