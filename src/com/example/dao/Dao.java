package com.example.dao;

import java.io.Serializable;
import java.util.List;


public interface Dao<T, ID extends Serializable> {

	/**
	 * returns a list of obj
	 * @return a list of object T
	 */
	List<T> list();

	/**
	 * gets the object from database by its id
	 * @param id - id of the object
	 * @return the object from the database
	 */
	T get(ID id);

	/**
	 * saves the object to the database
	 * @param object - object to be added
	 */
	void save(T object);

	/**
	 * updates the object in the database
	 * @param object - object to be updated
	 */
	void update(T object);

	/**
	 * inserts or updates the object in the database
	 * @param object - object to be merged
	 */
	void merge(T object);
	/**
	 * deletes the object in the database
	 * @param object - object to be deleted
	 */
	void delete(T object);
	
	Boolean isEmpty();
}
