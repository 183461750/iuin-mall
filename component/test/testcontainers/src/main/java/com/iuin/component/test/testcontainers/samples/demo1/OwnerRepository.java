package com.iuin.component.test.testcontainers.samples.demo1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}