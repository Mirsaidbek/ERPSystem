package dev.said.service;

import dev.said.domains.User;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.repository.UserRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(CreateAuthUserDTO dto) {

        User.builder()
                .firstName("sdass")
                .build();

        return null;
    }
}
