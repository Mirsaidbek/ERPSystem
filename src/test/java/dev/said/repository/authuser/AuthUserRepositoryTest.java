package dev.said.repository.authuser;

import dev.said.domains.AuthUser;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import dev.said.repository.AuthUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class AuthUserRepositoryTest {

    @Autowired
    private AuthUserRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }


    @Test
    void itShouldFindAuthUserByUsername() {

        String username = "said";
        AuthUser authUser = AuthUser.childBuilder()
                .username(username)
                .password("$2a$10$XUiwTFxeJsFDWLiH7FAOS.Djuigco0TGUxZumBbtDtRfpKdZN.d0O")
                .language(Language.UZBEK)
                .role(Role.ADMIN)
                .active(Active.ACTIVE)
                .build();

        repository.save(authUser);

        Optional<AuthUser> byUsername = repository.findByUsername(username);

        AuthUser authUser1 = byUsername.orElseGet(() -> null);

        assertThat(authUser1).isNotNull();


    }

    @Test
    void itShouldNotFindAuthUserByUsername() {

        String username = "said";
        AuthUser authUser = AuthUser.childBuilder()
                .username(username)
                .password("$2a$10$XUiwTFxeJsFDWLiH7FAOS.Djuigco0TGUxZumBbtDtRfpKdZN.d0O")
                .language(Language.UZBEK)
                .role(Role.ADMIN)
                .active(Active.ACTIVE)
                .build();

        repository.save(authUser);

        Optional<AuthUser> byUsername = repository.findByUsername("userna1215me");
        AuthUser authUser1 = byUsername.orElseGet(() -> null);

        assertThat(authUser1).isNull();


    }
}