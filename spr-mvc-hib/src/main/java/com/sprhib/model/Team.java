package com.sprhib.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

@Entity
@Table(name="teams")
public class Team extends AModel implements Comparable<Team> {
	
	public static final Team EMPTY = new Team();

	@NotNull
	@Size (min=1, max=250)
	private String name;
	
	private Integer rating;
	
	@OneToOne
	private Organization organization;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams")
	private Set<Member> members;
	
	public Team() {
		super();
	}
	
	public Team(String name) {
		super();
		this.name = name;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	/**
	 * @return the members
	 */
	public Set<Member> getMembers() {
		if (members == null) {
			members = new HashSet<>();
		}
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	@Override
	public int hashCode() {
		return this.getId() != null ? this.getId().hashCode() :
			(!StringUtils.isEmpty(name) ? this.name.hashCode() : super.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			Team test = (Team)obj;
			return (this.getId() != null && test.getId() != null) 
					? Objects.equals(this.getId(), test.getId())
					: (!StringUtils.isEmpty(this.name) && !StringUtils.isEmpty(test.name) ?  
							Objects.equals(this.name, test.name): super.equals(obj));
		}
		
		return super.equals(obj);
	}

	@Override
	public int compareTo(Team o) {
		return this.name.compareTo(o.name);
	}
}
