package com.modesteam.urutau.dao;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the methods common to DAO classes 
 */
public abstract class GenericDAO<Entity> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	/* Used to logger when he register a exception */
	private static final String EXCEPTION_MESSAGE = "When use some method, "+ GenericDAO.class.getSimpleName() 
			+" was thrown this message";

	private final Class<?> entityClass;
	
	private EntityManager entityManager;
	
	@Inject
    private DaoHelper daoHelper;

	/**
	 * 
	 */
	public GenericDAO() {
		this.entityClass = (Class<?>) ((ParameterizedType) getClass().
				getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Gets a Entity by a field with certain value
	 * 
	 * @param field Column name into database
	 * @param value Simple data
	 * @return Object correspondent to Entity defined by DAO
	 */
    public Entity get(String field, Object value) {
        Entity result = null;

        if(daoHelper.isValidParameter(value)) {
            try {
                final String sql = daoHelper.getSelectQuery(entityClass, field);
                Query query = entityManager.createQuery(sql);
                query.setParameter("value", value);

                result = (Entity) query.getSingleResult();
            } catch (NonUniqueResultException exception) {
                throw new NonUniqueResultException("Get more than one results when get a field");
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter has been passed " +
                    "to get method in GenericDAO");
        }

        return result;
    }
		
	/**
	 * Creates a new instance of User into database
	 * 
	 * @return true if operation do not throw any exception
	 */
	public boolean create(final Entity entity) {
		boolean objectCreated = false;
		
		try {
			entityManager.persist(entity);
			objectCreated = true;
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		}
		
		return objectCreated;
	}	
	
	
	/**
	 * Removes everything
	 * 
	 * @param Entity to remove
	 * @return false if any error occurs
	 */
	public boolean destroy(final Entity entity) {
		boolean objectDestroyed = false;
		
		try {
			entityManager.remove(entity);
			objectDestroyed = true;
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		} 
		
		return objectDestroyed;
	}	
	
	/**
	 * Update an entity
	 * 
	 * @param entity to be merge with database
	 * @return false if any error occurs
	 */
	public Entity update(final Entity entity) {
		Entity entityUpdated = null;
		
		try {
			entityUpdated = entityManager.merge(entity);
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		}
		
		return entityUpdated; 
	}
	
	/**
	 * Reloads object from database, for work of this 
	 * method it is needed that Entity have a  filled primary key 
	 */
	public void refresh(final Entity entity) {
		getSession().refresh(entity);
	}

	/**
	 * @param entityManager
	 */
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	private Session getSession() {
        return entityManager.unwrap(Session.class);
    }	
}
