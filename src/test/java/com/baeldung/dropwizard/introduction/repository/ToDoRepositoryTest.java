package com.baeldung.dropwizard.introduction.repository;


import com.baeldung.dropwizard.introduction.domain.ToDo;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
public class ToDoRepositoryTest {
/*
        private SessionFactory sessionFactory;
        private ToDoRepository todoRepository;

        @Before
        public void setUp() {
            // Configure Hibernate, assuming an in-memory H2 database for testing
            sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
            todoRepository = new ToDoRepository(sessionFactory);
        }

        @Test
        public void testInsertAndFindById() {
            ToDo todo = new ToDo();
            todo.setTitle("Test Todo");
            todo.setDescription("This is a test todo item.");

            // Insert
            ToDo savedTodo = todoRepository.insert(todo);

            // Find
            ToDo foundTodo = todoRepository.findById(savedTodo.getId());

            assertNotNull(foundTodo);
            assertEquals("Test Todo", foundTodo.getTitle());
            assertEquals("This is a test todo item.", foundTodo.getDescription());
        }

        @Test
        public void testFindAll() {
            // Assuming your DB is seeded or you add a few todos here

            List<ToDo> todos = todoRepository.findAll();
            assertTrue(todos.size() > 0);
        }

        // Add more tests for update, delete, etc.*/
  }

