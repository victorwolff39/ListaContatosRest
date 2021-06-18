package net.alerok.listacontatosrest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "mc_user")
public class User {

    @Id
    public Long id;

    @Column(nullable = false, length = 250)
    public String name;

    @Column(nullable = false, length = 10)
    public String login;

    @Column(nullable = false, length = 250)
    public String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
