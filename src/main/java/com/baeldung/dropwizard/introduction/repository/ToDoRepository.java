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
	}



	public ToDo findById(long id) {
		
	        return (ToDo) currentSession().get(ToDo.class, id);
	    }
	  
	   @SuppressWarnings("unchecked")
	   public List<ToDo> findAll() {
		   return sessionFactory.getCurrentSession().createNamedQuery("ToDo.findAll", ToDo.class).getResultList();
	   }

	   public void delete(ToDo toDo) {
	        currentSession().delete(toDo);
	    }

	public ToDo update(ToDo toDo) {
		// Check if the ID is valid, assuming IDs are positive.
		// This block can be adjusted based on how you want to handle new entities vs updates
		if (toDo.getId() == null || toDo.getId() <= 0) {
			throw new IllegalArgumentException("ToDo with invalid ID cannot be updated");
		}

		try {
			// Merge the state of the given entity into the current persistence context.
			ToDo mergedToDo = (ToDo) sessionFactory.getCurrentSession().merge(toDo);
			// The mergedToDo is now a managed entity, reflecting any changes made during the merge.
			// Hibernate will automatically track changes to mergedToDo and apply them to the database during flush or transaction commit.
			return mergedToDo;
		} catch (OptimisticLockException ole) {
			// Handle the optimistic lock exception
			// This exception indicates a concurrent modification problem.
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
