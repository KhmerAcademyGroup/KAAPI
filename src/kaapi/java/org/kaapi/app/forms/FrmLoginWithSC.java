package org.kaapi.app.forms;

public class FrmLoginWithSC {
	
	private String scID;
	private String scType;
	private String email; // 1:WEB, 2:FB, 3:TW, 4: GM
	public String getScID() {
		return scID;
	}
	public void setScID(String scID) {
		this.scID = scID;
	}
	public String getScType() {
		return scType;
	}
	public void setScType(String scType) {
		this.scType = scType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
