package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.dto.EventConverter;
import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.exception.EventNotFoundException;
import dk.kea.onav2ndproject_rest.repository.DepartmentRepository;
import dk.kea.onav2ndproject_rest.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class EventServiceTest {

    @Mock
    private EventRepository mockedEventRepository;

    EventService eventService;

    @Autowired
    EventConverter eventConverter;

    @Mock
    private DepartmentRepository mockedDepartmentRepository;

    @Mock
    private DepartmentService mockedDepartmentService;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        Event e1 = new Event();
        e1.setId(1);
        e1.setName("Event1");

        Event e2 = new Event(
                2,
                "Event2"
        );
        List<Event> eventList = new ArrayList<>();

        eventList.add(e1);
        eventList.add(e2);

        Page<Event> eventPage = new PageImpl<>(eventList);

        Department department1 = new Department();
        department1.setId(1);
        department1.setName("IT");

        Department department2 = new Department();
        department2.setId(2);
        department2.setName("Økonomi");

        Mockito.when(mockedEventRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(eventPage);
        Mockito.when(mockedEventRepository.findById(1)).thenReturn(Optional.of(e1));
        Mockito.when(mockedEventRepository.findById(42)).thenReturn(Optional.empty());
        Mockito.when(mockedDepartmentRepository.findById(1)).thenReturn(Optional.of(department1));
        Mockito.when(mockedDepartmentRepository.findById(2)).thenReturn(Optional.of(department2));
        Mockito.when(mockedDepartmentService.findById(1)).thenReturn(Optional.of(department1));
        Mockito.when(mockedDepartmentService.findById(2)).thenReturn(Optional.of(department2));
        doThrow(new EventNotFoundException("Event not found with id: 42")).when(mockedEventRepository).deleteById(42);

        Mockito.when(mockedEventRepository.save(ArgumentMatchers.any(Event.class))).thenAnswer(new Answer<Event>() {
            @Override
            public Event answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments.length > 0 && arguments[0] instanceof Event) {
                    Event eventToSave = (Event) arguments[0];
                    if (eventToSave.getId()==0) {
                        eventToSave.setId(3);
                    }
                    return eventToSave;
                } else {
                    throw new IllegalArgumentException("Invalid argument type");
                }
            }
        });

        eventService = new EventService(mockedEventRepository, eventConverter, mockedDepartmentService);
    }

    @Test
    void getAllEvents() {
        Page<EventDTO> eventDTOList = eventService.getAllEvents(Pageable.unpaged());
        assertEquals("Event1", eventDTOList.get().findFirst().get().name());
        assertEquals("Event2", eventDTOList.get().skip(1).findFirst().get().name());
    }

    @Test
    void getEventById() {
        EventDTO eventDTO = eventService.getEventById(1);
        assertEquals("Event1", eventDTO.name());
        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(42));
    }

    @Test
    void createEvent() {
        Event event = new Event(0, "Event3");
        event.setDepartments(new HashSet<>(Arrays.asList(mockedDepartmentService.findById(1).get(), mockedDepartmentService.findById(2).get())));
        EventDTO resultEventDTO = eventService.createEvent(eventConverter.toDTO(event));
        assertEquals(3, resultEventDTO.id());
    }

    @Test
    void updateEvent() {
        Event event = new Event(1, "UpdatedEvent");
        event.setDepartments(new HashSet<>(Arrays.asList(mockedDepartmentService.findById(1).get(), mockedDepartmentService.findById(2).get())));
        EventDTO resultEventDTO = eventService.updateEvent(1, eventConverter.toDTO(event));
        assertEquals(1, resultEventDTO.id());
        assertEquals("UpdatedEvent", resultEventDTO.name());
        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(42, resultEventDTO));
    }

    @Test
    void deleteEventById() {
        assertThrows(EventNotFoundException.class, () -> eventService.deleteEventById(42));
    }
}