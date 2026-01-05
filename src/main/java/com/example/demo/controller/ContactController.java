package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Contact;
import com.example.demo.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "https://auth-frontend4.vercel.app/")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    // POST new contact
    @PostMapping
    public ResponseEntity<?> submitContact(@RequestBody Contact contact) {
        if(contact.getName() == null || contact.getEmail() == null || contact.getMessage() == null) {
            return ResponseEntity.badRequest().body("Please fill in all required fields.");
        }
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(savedContact);
    }

    // GET all contacts (optional, admin)
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
