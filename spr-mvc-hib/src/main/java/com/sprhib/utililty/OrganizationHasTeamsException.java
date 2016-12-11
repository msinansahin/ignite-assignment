package com.sprhib.utililty;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public class OrganizationHasTeamsException extends BaseError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer organizationId;

	public OrganizationHasTeamsException(Integer organizationId) {
		super();
		this.organizationId	 = organizationId;
	}

	/**
	 * @return the organizationId
	 */
	public Integer getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String getLabelCode() {
		return "exp.organization.has.teams";
	}

}
