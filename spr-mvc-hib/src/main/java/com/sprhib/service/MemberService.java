package com.sprhib.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.sprhib.model.Member;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public interface MemberService {
	
	public void addMember(Member member);
	public void updateMember(Member member);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws NoResultException if member not exists with id 
	 */
	public Member getMember(int id);
	public void deleteMember(int id);
	public List<Member> getMembers();
	

}
