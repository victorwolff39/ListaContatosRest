package net.alerok.listacontatosrest.domain.service;

import net.alerok.listacontatosrest.domain.model.Contact;
import net.alerok.listacontatosrest.domain.model.ListaContatosRestUserDetails;
import net.alerok.listacontatosrest.domain.model.User;
import net.alerok.listacontatosrest.domain.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    //Get all contacts from a user
    public List<Contact> getAllFromUser(Long userId) throws ResponseStatusException {
        Optional<List<Contact>> contacts = contactRepository.getAllFromUser(userId);
        contacts.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found"));
        return contacts.get();
    }

    //Add a new contact
    public Contact add(Long userId, Contact contact) throws ResponseStatusException {
        User user = userService.getById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found"));
        contact.setUser(user);
        return contactRepository.save(contact);
    }

}
