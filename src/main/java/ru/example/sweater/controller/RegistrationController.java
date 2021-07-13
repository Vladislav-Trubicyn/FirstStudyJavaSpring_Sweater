package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.example.sweater.model.Message;
import ru.example.sweater.model.Role;
import ru.example.sweater.model.User;
import ru.example.sweater.service.UserRepository;
import java.util.Collections;

@Controller
public class RegistrationController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model)
    {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model)
    {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким ником занят");
            model.addAttribute("user", new User());
            return "registration";
        }
        else
        {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            model.addAttribute("message", new Message());
            return "redirect:/message";
        }
    }
}
