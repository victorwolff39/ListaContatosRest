package net.alerok.listacontatosrest.domain;

import net.alerok.listacontatosrest.domain.model.User;
import net.alerok.listacontatosrest.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return null;
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
        userRepository.deleteById(id);
    }

    //Verify is the id field exists and if there is already a user with the same login
    private void verifyUser(User user) {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_user_data");

        if (loginExists(user.getLogin()))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "login_already_exists");
    }

    //Verify if a user exists
    private boolean userExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    //Verify if a login already exists
    private boolean loginExists(String login) {
        List<User> users = userRepository.getAll();
        for (User user : users) {
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }

}
