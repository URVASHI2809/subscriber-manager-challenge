package com.interview.subscribermanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.repository.SubscriberRepository;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Page<Subscriber> getAllSubscribers(int page, int size) {
        return subscriberRepository.findAll(PageRequest.of(page, size, Sort.by("msisdn")));
    }

    public Optional<Subscriber> getSubscriberById(Integer id) {
        return subscriberRepository.findById(id);
    }

    public Subscriber saveSubscriber(Subscriber subscriber) {
        String msisdn = subscriber.getMsisdn();
        if (msisdn != null) {
            Optional<Subscriber> existing = subscriberRepository.findByMsisdn(msisdn);
            if (existing.isPresent()) {
                Subscriber found = existing.get();
                Integer foundId = found.getId();
                Integer currentId = subscriber.getId();
                if (foundId != null && !foundId.equals(currentId)) {
                    throw new IllegalArgumentException("MSISDN is already registered: " + msisdn);
                }
            }
        }

        try {
            return subscriberRepository.save(subscriber);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("MSISDN is already registered: " + msisdn, ex);
        }
    }

    public void deleteSubscriber(Integer id) {
        subscriberRepository.deleteById(id);
    }
}
