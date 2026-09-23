package com.interview.subscribermanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.service.SubscriberService;

class SubscriberControllerTest {

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private SubscriberController subscriberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSubscribers() {
        List<Subscriber> subscribers = Arrays.asList(
                new Subscriber("34600111222", "PRE100", new Date(1577836800000L)),
                new Subscriber("34600333444", "POST500", new Date(1609459200000L)));
        when(subscriberService.getAllSubscribers()).thenReturn(subscribers);

        ResponseEntity<List<Subscriber>> response = subscriberController.getAllSubscribers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscribers, response.getBody());
    }

    @Test
    void testGetSubscriberByIdFound() {
        int id = 1;
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.of(subscriber));

        ResponseEntity<Subscriber> response = subscriberController.getSubscriberById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriber, response.getBody());
    }

    @Test
    void testGetSubscriberByIdNotFound() {
        int id = 1;
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.empty());

        ResponseEntity<Subscriber> response = subscriberController.getSubscriberById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateSubscriber() {
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.saveSubscriber(subscriber)).thenReturn(subscriber);

        ResponseEntity<Subscriber> response = subscriberController.createSubscriber(subscriber);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(subscriber, response.getBody());
    }

    @Test
    void testUpdateSubscriberFound() {
        int id = 1;
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.of(subscriber));
        when(subscriberService.saveSubscriber(subscriber)).thenReturn(subscriber);

        ResponseEntity<Subscriber> response = subscriberController.updateSubscriber(id, subscriber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriber, response.getBody());
    }

    @Test
    void testUpdateSubscriberNotFound() {
        int id = 1;
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.empty());

        ResponseEntity<Subscriber> response = subscriberController.updateSubscriber(id, subscriber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteSubscriber() {
        int id = 1;
        ResponseEntity<Void> response = subscriberController.deleteSubscriber(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subscriberService, times(1)).deleteSubscriber(id);
    }
}
