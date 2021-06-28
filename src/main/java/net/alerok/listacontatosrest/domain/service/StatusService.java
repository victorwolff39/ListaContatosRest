package net.alerok.listacontatosrest.domain.service;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public String check() {
        return "online";
    }

}
