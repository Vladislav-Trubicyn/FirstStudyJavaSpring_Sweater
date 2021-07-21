package ru.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.sweater.model.User;
import ru.example.sweater.repository.MessageRepository;
import ru.example.sweater.model.Message;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Set;

@Controller
public class MainController
{
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();

        session = request.getSession(false);
        if(session != null)
        {
            session.invalidate();
        }

        for(Cookie cookie : request.getCookies())
        {
            cookie.setMaxAge(0);
        }

        return "redirect:/login";
    }

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

    @GetMapping("/user-messages")
    public String showMyMessage(@AuthenticationPrincipal User user, Model model)
    {
        Iterable<Message> messages = messageRepository.findByAuthorId(user.getId());
        //Set<Message> messages = user.getMessages();
        model.addAttribute("messages", messages);
        return "messagelist";
    }

    @GetMapping("/editmessage/{id}")
    public String editMyMessage(@PathVariable("id") Message message, Model model)
    {
        model.addAttribute("message", message);
        return "editmessage";
    }

}
