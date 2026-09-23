package com.interview.subscribermanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.repository.SubscriberRepository;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public List<Subscriber> getAllSubscribers() {
        List<Subscriber> subscribers = new ArrayList<>();
        subscriberRepository.findAll().forEach(subscribers::add);
        return subscribers;
    }

    public Optional<Subscriber> getSubscriberById(Integer id) {
        return subscriberRepository.findById(id);
    }

    public Subscriber saveSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public void deleteSubscriber(Integer id) {
        subscriberRepository.deleteById(id);
    }
}
