package net.alerok.listacontatosrest.domain.repository;

import net.alerok.listacontatosrest.domain.model.ContactField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ContactFieldRepository extends CrudRepository<ContactField, Long> {

    @Query("SELECT f FROM mc_contactfield f WHERE f.contact.id = :contactId ORDER BY f.id")
    Optional<List<ContactField>> getAllFromContact(@Param("contactId") Long contactId);

    @Transactional
    @Modifying
    @Override
    @Query("DELETE FROM mc_contactfield f WHERE f.id = :fieldId")
    void deleteById(@Param("fieldId") Long fieldId);
}
