package com.greenlend.greenlend.controller;

import com.greenlend.greenlend.dto.ChatDTO;
import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.service.ChatService;
import com.greenlend.greenlend.service.LendOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class ChatController {

    private final ChatService chatService;
    private final LendOfferService offerService;

    @Autowired
    public ChatController(ChatService chatService, LendOfferService offerService) {
        this.chatService = chatService;
        this.offerService = offerService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostFilter("filterObject.chat.interested.username == authentication.name or filterObject.chat.offer.owner.username == authentication.name")
    public List<ChatMessage> getMessagesAfter(@PathVariable("id") Chat chatId, @RequestParam("timestamp") Long timestamp) {
        return chatService.getMessagesAfter(chatId, timestamp);
    }

    @PostMapping("/{id}")
    public String sendMessage(@PathVariable("id") Long chat, @RequestBody String message) {
        return chatService.sendMessage(message, chat);
    }

    @GetMapping("/{id}/members")
    @PostFilter("filterObject.username == authentication.name")
    public List<GLUser> getMembersInChat(@PathVariable("id") Long chat) {
        return chatService.getMembersInChat(chat);
    }

    @GetMapping(value = "/overview")
    public List<ChatDTO> getChats() {
        return chatService.getChats().stream().map(chatService::convertToDTO).collect(Collectors.toList());
    }
}
