package com.iuin.component.test.testcontainers.samples.demo1.repos;

import com.iuin.component.test.testcontainers.samples.demo1.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void findAllReturnsJohnDoe() { // as defined in tc-initscript.sql
        var owners = ownerRepository.findAll();
        assertThat(owners.size()).isOne();
        assertThat(owners.get(0).getFirstName()).isEqualTo("John");
        assertThat(owners.get(0).getLastName()).isEqualTo("Doe");
    }
}

