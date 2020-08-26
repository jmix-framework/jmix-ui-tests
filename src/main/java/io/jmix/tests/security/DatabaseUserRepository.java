package io.jmix.tests.security;

import io.jmix.tests.entity.User;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("tests_UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }
}