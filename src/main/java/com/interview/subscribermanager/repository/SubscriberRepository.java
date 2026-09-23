package com.interview.subscribermanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.interview.subscribermanager.model.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Integer> {
}
