package com.sprhib.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.MemberDAO;
import com.sprhib.model.Member;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public void addMember(Member member) {
		memberDAO.addMember(member);
	}

	@Override
	public void updateMember(Member member) {
		memberDAO.updateMember(member);
	}

	@Override
	public Member getMember(int id) {
		Member member = memberDAO.getMember(id);
		if (member != null) {
			Hibernate.initialize(member.getTeams());
		}
		return member;
	}
	
	@Override
	public void deleteMember(int id) {
		memberDAO.deleteMember(id);
	}

	@Override
	public List<Member> getMembers() {
		return memberDAO.getMembers();
	}


}
