package com.sprhib.utililty;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public class TeamHasMemberException extends BaseError  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer teamId;

	public TeamHasMemberException(Integer teamId) {
		super();
		this.teamId = teamId;
	}

	@Override
	public String getLabelCode() {
		return "exp.team.has.members";
	}

	/**
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

}
