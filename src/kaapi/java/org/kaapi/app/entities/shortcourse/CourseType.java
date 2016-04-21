package org.kaapi.app.entities.shortcourse;

public class CourseType {
	private int id;
	private String type;
	public String getType() {
		return type;
	}
	public CourseType(int id, String type) {
		this.id = id;
		this.type = type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
