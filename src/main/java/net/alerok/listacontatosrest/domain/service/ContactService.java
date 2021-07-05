package net.alerok.listacontatosrest.domain.service;

import net.alerok.listacontatosrest.domain.model.Contact;
import net.alerok.listacontatosrest.domain.model.ContactField;
import net.alerok.listacontatosrest.domain.model.User;
import net.alerok.listacontatosrest.domain.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserService userService;

    @Autowired
    ContactFieldService contactFieldService;

    //Get all contacts from a user
    public List<Contact> getAllFromUser(Long userId) throws ResponseStatusException {
        Optional<List<Contact>> contacts = contactRepository.getAllFromUser(userId);
        contacts.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found"));
        return contacts.get();
    }

    //Get contact by id
    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }

    //Add a new contact
    public Contact add(Long userId, Contact contact) throws ResponseStatusException {
        contact.setUser(getUserById(userId));
        return contactRepository.save(contact);
    }

    //Edit a contact
    public Contact edit(Long userId, Long id, Contact contact) {
        contact.setUser(getUserById(userId));
        contact.setId(id);
        if (contactRepository.findById(id).isPresent()) {
            return contactRepository.save(contact);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "contact_not_found");
        }
    }

    //Delete contact
    public void delete(Long userId, Long id) {
        getUserById(userId);
        if (contactRepository.findById(id).isPresent()) {
            deleteFields(userId, id);
            contactRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "contact_not_found");
        }
    }

    //Delete all fields from contact
    public void deleteFields(Long userId, Long contactId) {
        List<ContactField> fields = contactFieldService.getAllFromContact(userId, contactId);
        fields.forEach(field -> contactFieldService.delete(userId, contactId, field.getId()));
    }

    private User getUserById(Long userId) {
        return userService.getById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found"));
    }
}
