package net.alerok.listacontatosrest.api.controller;

import net.alerok.listacontatosrest.domain.UserService;
import net.alerok.listacontatosrest.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get users
    @GetMapping()
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    //Get specific users
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    //Add a new user
    @PostMapping()
    public User add(@RequestBody User user) {
        return userService.add(user);
    }

    //Edit a user
    @PutMapping(path = "/{id}")
    public User edit(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.edit(id, user);
    }

    //Delete a user
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
