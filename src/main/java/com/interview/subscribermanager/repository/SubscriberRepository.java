package com.interview.subscribermanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.interview.subscribermanager.model.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Integer>, PagingAndSortingRepository<Subscriber, Integer> {

	Optional<Subscriber> findByMsisdn(String msisdn);

}
