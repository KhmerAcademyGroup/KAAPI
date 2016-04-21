package org.kaapi.app.entities.shortcourse;

public class FrmShortCourse {
	private int id;
	private int shiftId;
	private int courseId;
	private int typeId;
	private int kaUserId;
	private String kauserId;
	private int generation;
	
	private FrmStudent frmStudent;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShiftId() {
		return shiftId;
	}
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	public int getCourseId() {	
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getKaUserId() {
		return kaUserId;
	}
	public void setKaUserId(int kaUserId) {
		this.kaUserId = kaUserId;
	}
	public FrmStudent getFrmStudent() {
		return frmStudent;
	}
	public void setFrmStudent(FrmStudent frmStudent) {
		this.frmStudent = frmStudent;
	}
	public String getKauserId() {
		return kauserId;
	}
	public void setKauserId(String userId) {
		this.kauserId = userId;
	}
	public int getGeneration() {
		return generation;
	}
	public void setGeneration(int generation) {
		this.generation = generation;
	}
}
