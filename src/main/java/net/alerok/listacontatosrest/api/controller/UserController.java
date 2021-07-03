package net.alerok.listacontatosrest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.alerok.listacontatosrest.domain.service.UserService;
import net.alerok.listacontatosrest.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "REST API Users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    //Get users
    @GetMapping()
    @ApiOperation(value = "Get all registered users.")
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    //Get specific users
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get information about the specified user.")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return userService.getById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //Add a new user
    @PostMapping()
    @ApiOperation(value = "Create a new user.")
    public ResponseEntity<User> add(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.add(user));
    }

    //Edit a user
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Edit the specified user.")
    public ResponseEntity<User> edit(@PathVariable("id") Long id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.edit(id, user));
    }

    //Delete a user
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete the specified user")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
