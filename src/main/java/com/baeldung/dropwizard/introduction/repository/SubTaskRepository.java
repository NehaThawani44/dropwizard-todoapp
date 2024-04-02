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
        return (SubTask) sessionFactory.getCurrentSession().get(SubTask.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<SubTask> findAll() {

        return sessionFactory.getCurrentSession().createNamedQuery("SubTask.findAll", SubTask.class).getResultList();
    }



    public void delete(SubTask subTask) {
        sessionFactory.getCurrentSession().delete(subTask);
    }


    public SubTask insert(@Valid SubTask subTask) {
        return persist(subTask);
    }


    public void deleteById(long id) {
        SubTask subTask = findById(id);
        if (subTask != null) {
            delete(subTask);
        }
    }


}
