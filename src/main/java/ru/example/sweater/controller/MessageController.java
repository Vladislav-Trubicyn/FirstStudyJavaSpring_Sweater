package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.sweater.model.User;
import ru.example.sweater.repository.MessageRepository;
import ru.example.sweater.model.Message;

@Controller
public class MessageController
{
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/message")
    public String message(@RequestParam(name = "tag", required = false) String filter, Model model)
    {
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty())
        {
            messages = messageRepository.findByTag(filter);
            model.addAttribute("messages", messages);
            model.addAttribute("message", new Message());
            model.addAttribute("filter", filter);
        }
        else
        {
            messages = messageRepository.findAll();
            model.addAttribute("messages", messages);
            model.addAttribute("message", new Message());
        }
        return "message";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal User user, @ModelAttribute("message") Message message)
    {
        message.setAuthor(user);
        messageRepository.save(message);

        return "redirect:";
    }
}
