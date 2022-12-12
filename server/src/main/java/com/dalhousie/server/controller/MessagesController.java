package com.dalhousie.server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dalhousie.server.model.Messages;
import com.dalhousie.server.persistence.MessagesRepository;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    
    @Autowired
    private MessagesRepository messagesRepository;
    
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createMessage(@RequestBody Messages messages) {
        if(messagesRepository.save(messages) > 0) {
            return new ResponseEntity<>("Message created successfully", HttpStatus.CREATED);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public List<Messages> getAllMessages(){
        return messagesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messages> get(@PathVariable Integer id) {
        return messagesRepository.getById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer id, @RequestBody Messages messages) {
        return messagesRepository.getById(id)
        .map(savedMessage -> {
            savedMessage.setId(messages.getId());
            savedMessage.setUserId(messages.getUserId());
            savedMessage.setContent(messages.getContent());
            savedMessage.setRead(messages.isRead());

            messagesRepository.update(savedMessage);
            return new ResponseEntity<>("Message updated successfully", HttpStatus.OK);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        messagesRepository.deleteById(id);
        return new ResponseEntity<>("Message deleted successfully", HttpStatus.OK);
    }
}