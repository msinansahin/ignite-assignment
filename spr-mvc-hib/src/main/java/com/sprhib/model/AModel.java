package com.sprhib.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AModel {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO) 
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return object has an id
	 */
	public boolean isNew () {
		return id == null;
	}
}
