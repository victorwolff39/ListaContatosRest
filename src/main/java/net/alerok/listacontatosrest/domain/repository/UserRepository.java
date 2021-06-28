package net.alerok.listacontatosrest.domain.repository;

import net.alerok.listacontatosrest.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT m FROM mc_user m ORDER BY m.id")
    List<User> getAll();

    @Query("SELECT m FROM mc_user m WHERE m.username = :username")
    Optional<User> getUserByUsername(@Param("username") String username);

}
