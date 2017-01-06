package com.sprhib.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.springframework.util.StringUtils;

@Entity
@Table(name="members")
public class Member extends AModel {

	public static final Member EMPTY = new Member();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Team> teams;
	
	@Transient
	private List<String> teamIds;

	@NotNull
	@Size (min=5, max=250)
	private String name;
	
    @Formula("(select count(*) from members_teams mt where mt.members_id = id)")
	int teamCount;

    public Member() {
    	super();
	}
    
	public Member(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the teams
	 */
	public Set<Team> getTeams() {
		if (teams == null) {
			teams = new HashSet<>();
		}
		return teams;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
	public int getTeamCount() {
		return teamCount;
	}
	
	@Override
	public int hashCode() {
		return this.getId() != null ? this.getId().hashCode() :
			(!StringUtils.isEmpty(name) ? this.name.hashCode() : super.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Member) {
			Member test = (Member)obj;
			return (this.getId() != null && test.getId() != null) 
					? Objects.equals(this.getId(), test.getId())
					: (!StringUtils.isEmpty(this.name) && !StringUtils.isEmpty(test.name) ?  
							Objects.equals(this.name, test.name): super.equals(obj));
		}
		
		return super.equals(obj);
	}

	public List<String> getTeamIds() {
		if (teamIds == null) {
			teamIds = new ArrayList<>();
		}
		return teamIds;
	}

	public void setTeamIds(List<String> teamIds) {
		this.teamIds = teamIds;
	}
	
}
