package net.alerok.listacontatosrest.domain;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public String check() {
        return "online";
    }

}
