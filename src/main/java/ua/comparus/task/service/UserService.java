package ua.comparus.task.service;

import org.springframework.stereotype.Service;
import ua.comparus.task.model.User;
import ua.comparus.task.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(String username, String name, String surname) {
        return userRepository.fetchUsers(username, name, surname);
    }
}
