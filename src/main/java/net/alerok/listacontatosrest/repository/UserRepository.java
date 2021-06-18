package net.alerok.listacontatosrest.repository;

import net.alerok.listacontatosrest.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
