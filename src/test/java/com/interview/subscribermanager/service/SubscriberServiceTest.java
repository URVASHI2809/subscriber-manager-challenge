package com.interview.subscribermanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.interview.subscribermanager.model.Subscriber;
import com.interview.subscribermanager.repository.SubscriberRepository;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    @Test
    void testGetAllSubscribersPaged() {
        Subscriber s1 = new Subscriber("34600111222", "PRE100", new Date(1577836800000L));
        Subscriber s2 = new Subscriber("34600333444", "POST500", new Date(1609459200000L));
        Page<Subscriber> page = new PageImpl<>(Arrays.asList(s1, s2));

        when(subscriberRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Subscriber> result = subscriberService.getAllSubscribers(0, 2);

        assertEquals(page, result);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(subscriberRepository).findAll(captor.capture());
        Pageable used = captor.getValue();
        assertEquals(0, used.getPageNumber());
        assertEquals(2, used.getPageSize());
    }

    @Test
    void testSaveSubscriberThrowsOnDuplicateMsisdn() {
        Subscriber existing = new Subscriber("12345", "PRE", new Date(0));
        existing.setId(1);

        when(subscriberRepository.findByMsisdn("12345")).thenReturn(Optional.of(existing));

        Subscriber toSave = new Subscriber("12345", "PRE", new Date(0));
        toSave.setId(2);

        assertThrows(IllegalArgumentException.class, () -> subscriberService.saveSubscriber(toSave));

        verify(subscriberRepository, never()).save(any());
    }

    @Test
    void testSaveSubscriberSucceedsWhenMsisdnUnique() {
        Subscriber toSave = new Subscriber("99999", "PRE", new Date(0));

        when(subscriberRepository.findByMsisdn("99999")).thenReturn(Optional.empty());
        Subscriber saved = new Subscriber("99999", "PRE", new Date(0));
        saved.setId(10);
        when(subscriberRepository.save(toSave)).thenReturn(saved);

        Subscriber result = subscriberService.saveSubscriber(toSave);

        assertEquals(saved, result);
        verify(subscriberRepository).save(toSave);
    }
}
