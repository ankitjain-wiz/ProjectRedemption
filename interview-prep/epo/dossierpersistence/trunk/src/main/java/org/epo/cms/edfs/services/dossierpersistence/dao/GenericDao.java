package org.epo.cms.edfs.services.dossierpersistence.dao;

import java.util.List;

import org.epo.cms.edfs.services.dossierpersistence.exception.DAOException;



public interface GenericDao<E, K> {

	public void add(E entity) throws DAOException;

	public void saveOrUpdate(E entity) throws DAOException;

	public void update(E entity) throws DAOException;

	public void remove(E entity) throws DAOException;

	public E find(K key) throws DAOException;

	public List<E> getAll() throws DAOException;

}