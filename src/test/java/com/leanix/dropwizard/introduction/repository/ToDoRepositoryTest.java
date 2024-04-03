package com.leanix.dropwizard.introduction.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.leanix.dropwizard.introduction.domain.ToDo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ToDoRepositoryTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private ToDoRepository toDoRepository;
    @Mock
    private Query<ToDo> toDoQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        toDoRepository = new ToDoRepository(sessionFactory);

        when(session.createNamedQuery("ToDo.findAll", ToDo.class)).thenReturn(toDoQuery);

        toDoRepository = new ToDoRepository(sessionFactory);

    }

    @Test
    public void testFindToDoById() {
        // Arrange
        long toDoId = 1L;
        ToDo expectedToDo = new ToDo();
        when(session.get(ToDo.class, toDoId)).thenReturn(expectedToDo);

        // Act
        ToDo resultToDo = toDoRepository.findById(toDoId);

        // Assert
        assertNotNull(resultToDo);
        assertEquals(expectedToDo, resultToDo);
    }
    @Test
    public void testFindAllToDos() {
        List<ToDo> expectedToDos = Arrays.asList(new ToDo(), new ToDo());
        when(toDoQuery.getResultList()).thenReturn(expectedToDos);

        // Act
        List<ToDo> resultToDos = toDoRepository.findAll();

        // Assert
        assertNotNull(resultToDos);
        assertEquals(2, resultToDos.size());
    }

    @Test
    public void testInsertToDo() {
        ToDo mockToDo = new ToDo();
        mockToDo.setId(1L); 
        when(session.get(ToDo.class, mockToDo.getId())).thenReturn(mockToDo);

        ToDo insertedToDo = toDoRepository.insert(mockToDo); // Insert the mockToDo itself

        assertNotNull("The inserted ToDo should not be null", insertedToDo);
        assertEquals("The ID of the inserted ToDo should be 1", Long.valueOf(1L), insertedToDo.getId());

      
        verify(session).saveOrUpdate(mockToDo);
     
        verifyNoMoreInteractions(session);
    }

    @Test
    public void testUpdateToDo() {
        // Arrange
        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setTitle("Updated Title");

        Session mockSession = mock(Session.class);
        when(sessionFactory.getCurrentSession()).thenReturn(mockSession);
        when(mockSession.get(ToDo.class, 1L)).thenReturn(toDo);
        when(mockSession.merge(any(ToDo.class))).thenReturn(toDo);

        // Act
        ToDo result = toDoRepository.update(toDo);

        // Assert
        assertNotNull( "Updated ToDo should not be null.",result);
        assertEquals("Updated Title", result.getTitle());

        // Verify interactions
        verify(mockSession).merge(toDo);
    }



    @Test
    public void testDeleteToDoById() {
        Long toDoId = 1L;

        ToDo mockToDo = new ToDo();
        mockToDo.setId(toDoId);
        when(session.get(ToDo.class, toDoId)).thenReturn(mockToDo);
        doNothing().when(session).delete(mockToDo);

        boolean result = toDoRepository.deleteById(toDoId);
        assertTrue(result);
        verify(session).delete(mockToDo);
    }
}
