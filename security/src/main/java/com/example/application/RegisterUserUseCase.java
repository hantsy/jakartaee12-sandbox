package com.example.application;

import com.example.domain.model.RoleType;
import com.example.domain.model.UserAccount;
import com.example.domain.repository.UserAccountRepository;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@UseCase
public class RegisterUserUseCase {

    @Inject
    private UserAccountRepository repository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public UserAccount register(String username, String password, RoleType role) {
        return repository.save(new UserAccount(username, passwordHash.generate(password.toCharArray()), role));
    }
}
