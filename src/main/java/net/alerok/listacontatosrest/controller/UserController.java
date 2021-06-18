package net.alerok.listacontatosrest.controller;

import net.alerok.listacontatosrest.model.User;
import net.alerok.listacontatosrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/api/user/getUserById/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/user/createUser")
    public User save(@RequestBody User user) {
        return repository.save(user);
    }

}
