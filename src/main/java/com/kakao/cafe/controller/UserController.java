package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UpdateUserForm;
import com.kakao.cafe.domain.dto.UserForm;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/join")
    public String create(@Valid UserForm userForm) {
        userService.join(userForm);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{index}")
    public String profile(@PathVariable("index") int index, Model model) {
        UserForm userForm = userService.findOneUser(index - 1);
        model.addAttribute("user", userForm);
        return "user/profile";
    }

    @GetMapping("/{index}/update")
    public String createUpdateForm(@PathVariable("index") int index, Model model) {
        UserForm userForm = userService.findOneUser(index - 1);
        model.addAttribute("user", userForm);
        model.addAttribute("index", index);
        return "user/updateForm";
    }

    @PutMapping("/{index}/update")
    public String updateUser(@PathVariable("index") int index, @Valid UpdateUserForm updateUserForm) {
        userService.update(updateUserForm, index - 1);
        return "redirect:/users";
    }
}
