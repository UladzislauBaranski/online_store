package com.gmail.vladbaransky.servicemodule.util.converter;

import com.gmail.vladbaransky.repositorymodule.model.Item;
import com.gmail.vladbaransky.repositorymodule.model.Order;
import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.model.ItemDTO;
import com.gmail.vladbaransky.servicemodule.model.OrderDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static Order getObjectFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDate(orderDTO.getDate());
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setStatus(orderDTO.getStatus());
        order.setNumberOfItems(orderDTO.getNumberOfItems());
        order.setTotalPrice(orderDTO.getTotalPrice());
        if (orderDTO.getItems() != null) {
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < orderDTO.getItems().size(); i++) {
                Item item = new Item();
                item.setId(orderDTO.getItems().get(i).getId());
                item.setTitle(orderDTO.getItems().get(i).getTitle());
                item.setUniqueNumber(orderDTO.getItems().get(i).getUniqueNumber());
                item.setPrice(orderDTO.getItems().get(i).getPrice());
                item.setSummary(orderDTO.getItems().get(i).getSummary());
                items.add(item);
            }
            order.setItems(items);
        }
        if (orderDTO.getUserDTO() != null) {
            User user = new User();
            user.setId(orderDTO.getUserDTO().getId());
            user.setName(orderDTO.getUserDTO().getName());
            user.setSurname(orderDTO.getUserDTO().getSurname());
            user.setPatronymic(orderDTO.getUserDTO().getPatronymic());
            user.setUsername(orderDTO.getUserDTO().getUsername());
            user.setPassword(orderDTO.getUserDTO().getPassword());
            user.setRole(orderDTO.getUserDTO().getRole());
            user.setAddress(orderDTO.getUserDTO().getAddress());
            user.setTelephone(orderDTO.getUserDTO().getTelephone());
            user.setExist(orderDTO.getUserDTO().getExist());
            order.setUser(user);
        }
        return order;
    }

    public static OrderDTO getDTOFromObject(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDate(order.getDate());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setNumberOfItems(order.getNumberOfItems());
        orderDTO.setTotalPrice(order.getTotalPrice());
        if (order.getItems() != null) {
            List<ItemDTO> itemDTOList = new ArrayList<>();
            for (int i = 0; i < order.getItems().size(); i++) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId(order.getItems().get(i).getId());
                itemDTO.setTitle(order.getItems().get(i).getTitle());
                itemDTO.setUniqueNumber(order.getItems().get(i).getUniqueNumber());
                itemDTO.setPrice(order.getItems().get(i).getPrice());
                itemDTO.setSummary(order.getItems().get(i).getSummary());
                itemDTOList.add(itemDTO);
            }
            orderDTO.setItems(itemDTOList);
        }

        if (order.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(order.getUser().getId());
            userDTO.setName(order.getUser().getName());
            userDTO.setSurname(order.getUser().getSurname());
            userDTO.setPatronymic(order.getUser().getPatronymic());
            userDTO.setUsername(order.getUser().getUsername());
            userDTO.setPassword(order.getUser().getPassword());
            userDTO.setRole(order.getUser().getRole());
            userDTO.setAddress(order.getUser().getAddress());
            userDTO.setTelephone(order.getUser().getTelephone());
            userDTO.setExist(order.getUser().getExist());
            orderDTO.setUserDTO(userDTO);
        }
        return orderDTO;
    }
}
