package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.sweater.service.MessageRepository;
import ru.example.sweater.model.Message;

@Controller
public class MessageController
{
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/message")
    public String message(Model model)
    {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());
        return "message";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("message") Message message, Model model)
    {
        Iterable<Message> messages;
        if(message.getTag() != null && !message.getTag().isEmpty())
        {
            messages = messageRepository.findByTag(message.getTag());
            model.addAttribute("messages", messages);
            model.addAttribute("message", new Message());
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
    public String create(@ModelAttribute("message") Message message)
    {
        messageRepository.save(message);

        return "redirect:";
    }
}
