package net.alerok.listacontatosrest.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT m FROM mc_user m ORDER BY m.id")
    List<User> getAll();

}
