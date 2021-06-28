package net.alerok.listacontatosrest.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContatosRestUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public ListaContatosRestUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = true;
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //TODO: implement isAccountNonExpired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO: implement isAccountNonLocked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //TODO: implement isCredentialsNonExpired
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
