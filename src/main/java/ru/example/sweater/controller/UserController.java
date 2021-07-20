package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.sweater.model.Role;
import ru.example.sweater.model.User;
import ru.example.sweater.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
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

    @PostMapping()
    public String userSaveEdit(@RequestParam Map<String, String> form, @RequestParam("id") User user, @RequestParam("username") String username)
    {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet())
        {
            if(roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);

        return "redirect:/user";
    }

}
