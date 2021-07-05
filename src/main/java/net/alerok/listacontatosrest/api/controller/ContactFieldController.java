package net.alerok.listacontatosrest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.alerok.listacontatosrest.domain.model.ContactField;
import net.alerok.listacontatosrest.domain.service.ContactFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "REST API Contact Fields")
public class ContactFieldController {

    @Autowired
    private ContactFieldService contactFieldService;

    //Get all fields from a contact
    @GetMapping(path = "/{userId}/contacts/{contactId}/fields")
    @ApiOperation(value = "Get all fieds from a contact")
    public ResponseEntity<?> getAllFromContact(@PathVariable Long userId, @PathVariable Long contactId) {
        List<ContactField> fields;
        try {
            fields = contactFieldService.getAllFromContact(userId, contactId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(fields);
    }

    //Add new field to a contact
    @PostMapping(path = "/{userId}/contacts/{contactId}/fields")
    @ApiOperation(value = "Add a field to a contact")
    public ResponseEntity<?> add(@PathVariable Long userId, @PathVariable Long contactId, @RequestBody ContactField field) {
        try {
            return ResponseEntity.ok().body(contactFieldService.add(userId, contactId, field));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Edit a field
    @PutMapping(path = "/{userId}/contacts/{contactId}/fields/{id}")
    @ApiOperation(value = "Edit a field.")
    public ResponseEntity<?> edit(@PathVariable Long userId, @PathVariable Long contactId, @PathVariable Long id, @RequestBody ContactField field) {
        try {
            return ResponseEntity.ok().body(contactFieldService.edit(userId, contactId, id, field));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Delete a contact
    @DeleteMapping(path = "/{userId}/contacts/{contactId}/fields/{id}")
    @ApiOperation(value = "Delete a field.")
    public ResponseEntity<?> delete(@PathVariable Long userId, @PathVariable Long contactId, @PathVariable Long id) {
        try {
            contactFieldService.delete(userId, contactId, id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
