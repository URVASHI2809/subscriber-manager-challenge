package com.interview.subscribermanager.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.model.ErrorResponse;
import com.interview.subscribermanager.service.SubscriberService;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping
    public ResponseEntity<Page<Subscriber>> getAllSubscribers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Subscriber> result = subscriberService.getAllSubscribers(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubscriberById(@PathVariable Integer id) {
        Optional<Subscriber> subscriber = subscriberService.getSubscriberById(id);
        if (subscriber.isPresent()) {
            return new ResponseEntity<>(subscriber.get(), HttpStatus.OK);
        }
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                "Subscriber not found with id: " + id);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {
        Subscriber savedSubscriber = subscriberService.saveSubscriber(subscriber);
        return new ResponseEntity<>(savedSubscriber, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscriber(@PathVariable Integer id, @RequestBody Subscriber subscriber) {
        Optional<Subscriber> existingSubscriber = subscriberService.getSubscriberById(id);
        if (existingSubscriber.isPresent()) {
            subscriber.setId(id);
            Subscriber updatedSubscriber = subscriberService.saveSubscriber(subscriber);
            return new ResponseEntity<>(updatedSubscriber, HttpStatus.OK);
        }
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                "Subscriber not found with id: " + id);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Integer id) {
        subscriberService.deleteSubscriber(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse err = new ErrorResponse(HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
