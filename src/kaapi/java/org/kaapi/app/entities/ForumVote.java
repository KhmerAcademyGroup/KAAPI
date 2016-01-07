package org.kaapi.app.entities;

public class ForumVote {

	private String userId;
	private String commentId;
	private int voteType;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public int getVoteType() {
		return voteType;
	}
	public void setVoteType(int voteType) {
		this.voteType = voteType;
	}
	
}
