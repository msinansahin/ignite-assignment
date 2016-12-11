package com.sprhib.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

@Entity
@Table(name="organizations")
public class Organization extends AModel implements Comparable<Organization> {
	
	public static final Organization EMPTY = new Organization();

	@OneToMany (mappedBy = "organization")
	private Set<Team> teams;
	
	@Size (min=5, max=250)
	private String name;

	public Organization() {
		super();
	}
	
	public Organization(String name) {
		super();
		this.name = name;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Organization o) {
		return Objects.toString(this.name, "").compareTo(Objects.toString(o.name, ""));
	}
	
	@Override
	public int hashCode() {
		return this.getId() != null ? this.getId().hashCode() :
			(!StringUtils.isEmpty(name) ? this.name.hashCode() : super.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Organization) {
			Organization test = (Organization)obj;
			return (this.getId() != null && test.getId() != null) 
					? Objects.equals(this.getId(), test.getId())
					: (!StringUtils.isEmpty(this.name) && !StringUtils.isEmpty(test.name) ?  
							Objects.equals(this.name, test.name): super.equals(obj));
		}
		
		return super.equals(obj);
	}
	
}
