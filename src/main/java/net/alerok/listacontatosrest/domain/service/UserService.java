package net.alerok.listacontatosrest.domain.service;

import net.alerok.listacontatosrest.domain.model.Contact;
import net.alerok.listacontatosrest.domain.model.ListaContatosRestUserDetails;
import net.alerok.listacontatosrest.domain.model.User;
import net.alerok.listacontatosrest.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return user.map(ListaContatosRestUserDetails::new).get();
    }

    //Get user by username
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    //Get users
    public Iterable<User> getAll() {
        return userRepository.getAll();
    }

    //Get a specific user by id
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    //Add a new user
    public User add(User user) {
        verifyUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //Edit a user
    public User edit(Long id, User user) {
        if (!userExists(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        verifyUser(user);
        user.setId(id);
        return userRepository.save(user);
    }

    //Delete a user
    public void delete(Long id) {
        if (!userExists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }
        deleteContacts(id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    //Delete all contacts from user
    void deleteContacts(Long userId) {
        List<Contact> contacts = contactService.getAllFromUser(userId);
        contacts.forEach(contact->contactService.delete(userId, contact.getId()));
    }

    //Verify is the id field exists and if there is already a user with the same username
    private void verifyUser(User user) {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_user_data");

        if (usernameExists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "username_already_exists");
    }

    //Verify if a user exists
    private boolean userExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    //Verify if a username already exists
    private boolean usernameExists(String username) {
        List<User> users = userRepository.getAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

}
