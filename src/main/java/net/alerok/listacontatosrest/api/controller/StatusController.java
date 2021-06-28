package net.alerok.listacontatosrest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.alerok.listacontatosrest.domain.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/status")
@Api(value = "REST API Test")
@CrossOrigin(origins = "*")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    @ApiOperation(value = "Checks if the API is running.")
    public String check() {
        return statusService.check();
    }

}
