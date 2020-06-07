package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.VALUE_IF_DO_NOT_SELECTED;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
                           Model model) {
        List<UserDTO> users = userService.getUserByPage(page);

        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUsersById(id));
        return "user";
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "add_users";
    }

    @PostMapping
    public String addUsers(
            @ModelAttribute(name = "user") @Valid UserDTO user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "add_users";
        } else {
            String hashedPassword = userService.getBCryptPassword();
            user.setPassword(hashedPassword);
            userService.addUser(user);
        }
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUsers(@RequestParam(name = "ids", defaultValue = VALUE_IF_DO_NOT_SELECTED) List<Long> ids) {
        userService.deleteById(ids);
        return "redirect:/users";
    }

    @PostMapping("/refactor/password")
    public String refactorPassword(@RequestParam List<Long> ids) {
        userService.updatePasswordById(ids);
        return "redirect:/users";
    }

    @PostMapping("/refactor")
    public String refactorRolePage(@RequestParam(defaultValue = VALUE_IF_DO_NOT_SELECTED) String ids, Model model) {
        model.addAttribute("id", ids);
        return "refactor_page";
    }

    @PostMapping("/refactor/role")
    public String refactorRole(@RequestParam(value = "role") RoleEnum role,
                               @RequestParam(value = "id") List<Long> id) {
        userService.updateRoleById(id, role);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        UserDTO user = userService.getProfileCurrentUser();
        model.addAttribute("user", user);
        return "profile_page";
    }
}
