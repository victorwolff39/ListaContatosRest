package net.alerok.listacontatosrest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.alerok.listacontatosrest.domain.model.AuthenticationRequest;
import net.alerok.listacontatosrest.domain.service.AuthenticationService;
import net.alerok.listacontatosrest.domain.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "REST API Authentication")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ApiOperation(value = "Authenticate user with username and password.")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

}