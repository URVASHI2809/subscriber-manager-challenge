package com.interview.subscribermanager.repository;

import java.sql.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.interview.subscribermanager.model.Subscriber;

@DataJpaTest
// Embedded H2 auto-configured for this session (no real MySQL available) — default Replace.ANY
@Rollback(false)
class SubscriberRepositoryTest {

    @Autowired
    private SubscriberRepository repo;

    @Test
    public void testAddNew() {
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        Subscriber savedSubscriber = repo.save(subscriber);
        Assertions.assertThat(savedSubscriber).isNotNull();

        subscriber = new Subscriber("34600333444", "POST500", new Date(1609459200000L));
        savedSubscriber = repo.save(subscriber);
        Assertions.assertThat(savedSubscriber).isNotNull();
        Assertions.assertThat(savedSubscriber.getId()).isGreaterThan(0);
    }
}
