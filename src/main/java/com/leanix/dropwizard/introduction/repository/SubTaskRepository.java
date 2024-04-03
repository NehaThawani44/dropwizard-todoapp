package com.leanix.dropwizard.introduction.repository;

import com.leanix.dropwizard.introduction.domain.SubTask;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.OptimisticLockException;
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
        return sessionFactory.getCurrentSession().get(SubTask.class, id);
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

   /* public SubTask update(SubTask subTask) {
        LOGGER.info("Updating SubTask with ID: {}", subTask.getId());
        if (subTask.getId() == null || subTask.getId() <= 0) {
            LOGGER.error("Attempted to update SubTask with invalid ID: {}", subTask.getId());
            throw new IllegalArgumentException("SubTask with invalid ID cannot be updated");
        }

        try {
            SubTask mergedSubTask = (SubTask) sessionFactory.getCurrentSession().merge(subTask);
            LOGGER.info("SubTask with ID: {} updated successfully", mergedSubTask.getId());
            return mergedSubTask;
        } catch (OptimisticLockException ole) {
            LOGGER.error("Update failed for SubTask with ID: {} due to concurrent modification", subTask.getId(), ole);
            throw new RuntimeException("Update failed due to concurrent modification", ole);
        }
    }*/
    public boolean deleteById(long id) {
        SubTask subTask = findById(id);
        if (subTask != null) {
            delete(subTask);
        }
        return false;
    }


}
