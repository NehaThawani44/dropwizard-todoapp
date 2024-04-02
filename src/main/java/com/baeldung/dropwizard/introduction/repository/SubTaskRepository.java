package com.baeldung.dropwizard.introduction.repository;

import com.baeldung.dropwizard.introduction.domain.SubTask;
import com.baeldung.dropwizard.introduction.domain.ToDo;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

public class SubTaskRepository extends AbstractDAO<SubTask> {
    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubTaskRepository.class);


    public SubTaskRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public SubTask findById(long id) {
        // Use get() method to fetch SubTask by its ID
        return (SubTask) currentSession().get(SubTask.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<SubTask> findAll() {
        // Use createCriteria() to fetch all SubTask instances
        return sessionFactory.getCurrentSession().createNamedQuery("SubTask.findAll", SubTask.class).getResultList();
    }



    public void delete(SubTask subTask) {
        // Use the current session to delete a SubTask
        sessionFactory.getCurrentSession().delete(subTask);
    }

    public void update(SubTask subTask) {
        // Use saveOrUpdate() to update a SubTask instance
        currentSession().saveOrUpdate(subTask);

    }

    public SubTask insert(@Valid SubTask subTask) {
        // Persist a new SubTask to the database
        return persist(subTask);
    }


    public void deleteById(long id) {
        // Find SubTask by ID and delete it
        SubTask subTask = findById(id);
        if (subTask != null) {
            delete(subTask);
        }
    }


}
