package org.kaapi.app.entities;

public class UserType {

	
	private String userTypeId;
	private String userTypeName;
	private boolean viewable;
	private boolean commentable;
	private boolean postable;
	private boolean deleteable;
	private boolean userable;
	private int countUsers;
	
	public String getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public boolean isViewable() {
		return viewable;
	}
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}
	public boolean isCommentable() {
		return commentable;
	}
	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}
	public boolean isPostable() {
		return postable;
	}
	public void setPostable(boolean postable) {
		this.postable = postable;
	}
	public boolean isDeleteable() {
		return deleteable;
	}
	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}
	public boolean isUserable() {
		return userable;
	}
	public void setUserable(boolean userable) {
		this.userable = userable;
	}
	public int getCountUsers() {
		return countUsers;
	}
	public void setCountUsers(int countUsers) {
		this.countUsers = countUsers;
	}
	
}
