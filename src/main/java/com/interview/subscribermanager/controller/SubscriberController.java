package com.interview.subscribermanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.service.SubscriberService;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        return new ResponseEntity<>(subscribers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable Integer id) {
        Optional<Subscriber> subscriber = subscriberService.getSubscriberById(id);
        return subscriber.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {
        Subscriber savedSubscriber = subscriberService.saveSubscriber(subscriber);
        return new ResponseEntity<>(savedSubscriber, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Integer id, @RequestBody Subscriber subscriber) {
        Optional<Subscriber> existingSubscriber = subscriberService.getSubscriberById(id);
        if (existingSubscriber.isPresent()) {
            subscriber.setId(id);
            Subscriber updatedSubscriber = subscriberService.saveSubscriber(subscriber);
            return new ResponseEntity<>(updatedSubscriber, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Integer id) {
        subscriberService.deleteSubscriber(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
