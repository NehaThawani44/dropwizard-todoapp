package com.baeldung.dropwizard.introduction.repository;

import com.baeldung.dropwizard.introduction.domain.ToDo;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.OptimisticLockException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

public class ToDoRepository extends AbstractDAO<ToDo> {
	private final SessionFactory sessionFactory;
	private static final Logger LOGGER = LoggerFactory.getLogger(ToDoRepository.class);
	public ToDoRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
		LOGGER.info("ToDoRepository initialized");
	}



	public ToDo findById(long id) {

	        return (ToDo) sessionFactory.getCurrentSession().get(ToDo.class, id);
	    }
	  
	   @SuppressWarnings("unchecked")
	   public List<ToDo> findAll() {
		   return sessionFactory.getCurrentSession().createNamedQuery("ToDo.findAll", ToDo.class).getResultList();
	   }

	   public void delete(ToDo toDo) {
		   sessionFactory.getCurrentSession().delete(toDo);
	    }

	public ToDo update(ToDo toDo) {
		LOGGER.info("Updating ToDo with ID: {}", toDo.getId());
		if (toDo.getId() == null || toDo.getId() <= 0) {
			LOGGER.error("Attempted to update ToDo with invalid ID: {}", toDo.getId());
			throw new IllegalArgumentException("ToDo with invalid ID cannot be updated");
		}

		try {
			ToDo mergedToDo = (ToDo) sessionFactory.getCurrentSession().merge(toDo);
			LOGGER.info("ToDo with ID: {} updated successfully", mergedToDo.getId());
			return mergedToDo;
		} catch (OptimisticLockException ole) {
			LOGGER.error("Update failed for ToDo with ID: {} due to concurrent modification", toDo.getId(), ole);
			throw new RuntimeException("Update failed due to concurrent modification", ole);
		}
	}
	    public ToDo insert(@Valid ToDo toDo) {
	        return persist(toDo);
	    }

	public void deleteById(long id) {
		ToDo toDo = findById(id);
		if (toDo != null) {
			delete(toDo);
		}
	}
}
