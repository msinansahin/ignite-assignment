package com.sprhib.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sprhib.model.Member;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@Repository
public class MemberDAOImpl extends BaseDAO implements MemberDAO {

	@Override
	public void addMember(Member member) {
		addModel(member);
	}

	@Override
	public void updateMember(Member member) {
		Member memToUpdate = getMember(member.getId());
		memToUpdate.setName(member.getName());
		memToUpdate.setTeams(member.getTeams());
		updateModel(memToUpdate);
	}

	@Override
	public Member getMember(int id) {
		return getCurrentSession().get(Member.class, id);
	}

	@Override
	public void deleteMember(int id) {
		deleteModel(Member.class, id);
	}

	@Override
	public List<Member> getMembers() {
		return getModels(Member.class);
	}

}
