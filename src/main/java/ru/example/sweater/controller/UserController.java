package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.sweater.model.Role;
import ru.example.sweater.model.User;
import ru.example.sweater.repository.UserRepository;

import java.util.Iterator;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String userList(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "userlist";
    }

    @GetMapping("/{id}")
    public String userEditForm(@PathVariable("id") User user, Model model)
    {
        model.addAttribute("usr", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PostMapping("/edit")
    public String userSaveEdit()
    {
        return "userlist";
    }

}
