package org.epo.cms.edfs.services.dossierpersistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {
    
	@Autowired
	private SessionFactory sessionFactory;

	protected Class<? extends E> daoType;

	/**
	 * By defining this class as abstract, we prevent Spring from creating
	 * instance of this class If not defined as abstract,
	 * getClass().getGenericSuperClass() would return Object. There would be
	 * exception because Object class does not hava constructor with parameters.
	 */
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class) pt.getActualTypeArguments()[0];
	}

	/**
	 * This method is used to get current session from session factory. This
	 * method is made protected so that it can not be used from any other
	 * package.
	 * 
	 * @return  Session instance
	 */
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	
	/**
	 * This method is used to create new data in database as per given entity.
	 */
	@Override
	public void add(E entity) throws DAOException {
		currentSession().save(entity);
	}

	/**
	 * This method is used to save or update data in database as per given
	 * entity.
	 */
	@Override
	public void saveOrUpdate(E entity) throws DAOException {
		currentSession().saveOrUpdate(entity);
	}

	/**
	 * This method is used to update data in database as per given entity.
	 */
	@Override
	public void update(E entity) throws DAOException {
		currentSession().saveOrUpdate(entity);
	}

	/**
	 * This method is used to remove data in database as per given entity.
	 */
	@Override
	public void remove(E entity) throws DAOException {
		currentSession().delete(entity);
	}

	/**
	 * This method is used to find data in database as per given entity.
	 */
	@Override
	public E find(K key) throws DAOException {
		return (E) currentSession().get(daoType, key);
	}

	/**
	 * This method is used to fetch all data in table mapped to given entity.
	 */
	@Override
	public List<E> getAll() throws DAOException {
		return currentSession().createCriteria(daoType).list();
	}	
    
}