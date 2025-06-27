package it.uniroma3.siw.siwbooks.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwbooks.model.User;
import it.uniroma3.siw.siwbooks.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Still needed for encoding new passwords

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    // This 'save' method should ideally be for updating existing users,
    // or ensure password is already encoded if called for new users.
    public void save(User user){
        userRepository.save(user);
    }

    // Dedicated method for new user registration to ensure password encoding happens once
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }
}