package com.sprhib.utililty;

/**
 * Used for home page actions or any other <a> in html
 * 
 * @author mehmetsinan.sahin
 *
 */
public class Action {

	private String url;
	private String label;
	
	public Action(String url, String label) {
		super();
		this.url = url;
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	
}
