package net.alerok.listacontatosrest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.alerok.listacontatosrest.domain.model.Contact;
import net.alerok.listacontatosrest.domain.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "REST API Contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Get user contacts
    @GetMapping(path = "/{userId}")
    @ApiOperation(value = "Get all contacts from a specified user")
    public ResponseEntity<List<Contact>> getAllFromUser(@PathVariable("userId") Long userId) {
        List<Contact> contacts;
        try {
            contacts = contactService.getAllFromUser(userId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(contacts);
    }

    //Add a new contact to a user
    @PostMapping(path = "/{userId}")
    @ApiOperation(value = "Create a new contact to a user.")
    public ResponseEntity<Contact> add(@PathVariable Long userId, @RequestBody Contact contact) {
        try {
            return ResponseEntity.ok().body(contactService.add(userId, contact));
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
