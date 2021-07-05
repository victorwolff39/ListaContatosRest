package net.alerok.listacontatosrest.domain.service;

import net.alerok.listacontatosrest.domain.model.ContactField;
import net.alerok.listacontatosrest.domain.repository.ContactFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ContactFieldService {

    @Autowired
    ContactFieldRepository contactFieldRepository;

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    //Get all fields from a contact
    public List<ContactField> getAllFromContact(Long userId, Long contactId) throws ResponseStatusException {
        //If user exists, check the contact
        if (userExists(userId)) {
            //If the contact does not exists, throw 404
            if (!contactExists(contactId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "contact_not_found");
            }
        //If the user does not exists, throw 404
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }
        return contactFieldRepository.getAllFromContact(contactId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "field_not_found"));
    }

    //Add new field to a contact
    public ContactField add(Long userId, Long contactId, ContactField field) throws ResponseStatusException {
        verifyUserAndContact(userId, contactId);
        field.setContact(contactService.getById(contactId).get());
        return contactFieldRepository.save(field);
    }

    //Edit a field
    public ContactField edit(Long userId, Long contactId, Long fieldId, ContactField field) throws ResponseStatusException {
        verifyUserAndContact(userId, contactId);
        if (!fieldExists(fieldId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "field_not_found");
        }
        field.setContact(contactService.getById(contactId).get());
        field.setId(fieldId);
        return contactFieldRepository.save(field);
    }

    //Delete a field
    public void delete(Long userId, Long contactId, Long fieldId) {
        verifyUserAndContact(userId, contactId);
        if (!fieldExists(fieldId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "field_not_found");
        }
        contactFieldRepository.deleteById(fieldId);
    }

    private void verifyUserAndContact(Long userId, Long contactId) {
        if (userExists(userId)) {
            if (!contactExists(contactId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "contact_not_found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }
    }

    private Boolean contactExists(Long contactId) {
        return contactService.getById(contactId).isPresent();
    }

    private Boolean userExists(Long userId) {
        return userService.getById(userId).isPresent();
    }

    private Boolean fieldExists(Long fieldId) {
        return contactFieldRepository.findById(fieldId).isPresent();
    }

}
