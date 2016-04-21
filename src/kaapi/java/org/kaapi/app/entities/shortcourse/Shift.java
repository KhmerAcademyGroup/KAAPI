package org.kaapi.app.entities.shortcourse;

public class Shift {
	private int id;
	private String shift;
	private String time;
	
	public Shift(){
		
	}
	
	public Shift(int id, String shift, String time) {
		this.id = id;
		this.shift = shift;
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
