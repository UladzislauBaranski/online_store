package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import com.gmail.vladbaransky.servicemodule.OrderService;

import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.VALUE_IF_DO_NOT_SELECTED;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrdersByPage(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page, Model model) {
        List<OrderDTO> orders = orderService.getOrdersByPage(page);
        model.addAttribute("orders", orders);
        return "orders_page";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
       OrderDTO order= orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order_page";
    }

    @PostMapping("/refactor")
    public String refactorStatus(@RequestParam(defaultValue = VALUE_IF_DO_NOT_SELECTED) String id, Model model) {
        model.addAttribute("id", id);
        return "refactor_page_status";
    }

    @PostMapping("/refactor/status")
    public String refactorStatus(@RequestParam(value = "status") StatusEnum status,
                               @RequestParam(value = "id") List<Long> id) {
        orderService.updateStatusById(id, status);
        return "redirect:/orders";
    }
}

