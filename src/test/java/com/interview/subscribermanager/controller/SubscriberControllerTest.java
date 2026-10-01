package com.interview.subscribermanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.model.ErrorResponse;
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
        Page<Subscriber> page = new PageImpl<>(subscribers);
        when(subscriberService.getAllSubscribers(0, 10)).thenReturn(page);

        ResponseEntity<Page<Subscriber>> response = subscriberController.getAllSubscribers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void testGetSubscriberByIdFound() {
        int id = 1;
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.of(subscriber));
        ResponseEntity<?> response = subscriberController.getSubscriberById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriber, response.getBody());
    }

    @Test
    void testGetSubscriberByIdNotFound() {
        int id = 1;
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = subscriberController.getSubscriberById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
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
        ResponseEntity<?> response = subscriberController.updateSubscriber(id, subscriber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriber, response.getBody());
    }

    @Test
    void testUpdateSubscriberNotFound() {
        int id = 1;
        Subscriber subscriber = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        when(subscriberService.getSubscriberById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = subscriberController.updateSubscriber(id, subscriber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void testDeleteSubscriber() {
        int id = 1;
        ResponseEntity<Void> response = subscriberController.deleteSubscriber(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subscriberService, times(1)).deleteSubscriber(id);
    }

    @Test
    void testHandleDuplicateMsisdnReturnsConflict() {
        IllegalArgumentException ex = new IllegalArgumentException("MSISDN is already registered: 12345");
        ResponseEntity<ErrorResponse> response = subscriberController.handleIllegalArgument(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals("Conflict", response.getBody().getError());
        assertEquals("MSISDN is already registered: 12345", response.getBody().getMessage());
    }
}
