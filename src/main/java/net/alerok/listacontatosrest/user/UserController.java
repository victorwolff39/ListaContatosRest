package net.alerok.listacontatosrest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/api/user/getAll")
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/api/user/getById/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/user/add")
    public User add(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(path = "/api/user/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping(path = "/api/user/edit")
    public User edit(User user) {
        return userRepository.save(user);
    }

}
