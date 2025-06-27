package it.uniroma3.siw.siwbooks.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwbooks.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByEmail(String Email);
}
