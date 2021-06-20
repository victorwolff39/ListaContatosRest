package net.alerok.listacontatosrest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Get users
    @GetMapping()
    public Iterable<User> getAll() {
        return userRepository.getAll();
    }

    //Get specific users
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //Add a new user
    @PostMapping()
    public User add(@RequestBody User user) {
        verifyUser(user);
        return userRepository.save(user);
    }

    //Edit a user
    @PutMapping(path = "/{id}")
    public User edit(@PathVariable("id") Long id, @RequestBody User user) {
        if (!userExists(id))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user_not_found");
        verifyUser(user);
        user.setId(id);
        return userRepository.save(user);
    }

    //Delete a user
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (!userExists(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user_not_found");
        }
        userRepository.deleteById(id);
    }

    //Verify is the id field exists and if there is already a user with the same login
    public void verifyUser(User user) {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "invalid_user_data");

        if (loginExists(user.getLogin()))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "login_already_exists");
    }

    //Verify if a user exists
    public boolean userExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    //Verify if a login already exists
    public boolean loginExists(String login) {
        List<User> users = userRepository.getAll();
        for (User user : users) {
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }

}
