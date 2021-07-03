package net.alerok.listacontatosrest.domain.repository;

import net.alerok.listacontatosrest.domain.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    @Query("SELECT c FROM mc_contact c WHERE c.user.id = :userId ORDER BY c.id")
    Optional<List<Contact>> getAllFromUser(@Param("userId") Long userId);

}
