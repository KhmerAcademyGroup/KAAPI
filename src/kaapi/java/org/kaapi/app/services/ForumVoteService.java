package org.kaapi.app.services;

public interface ForumVoteService {

	public int votePlus(int userId , int commentId);
	public int vote(int userId , int commentId , int voteType);
	public int voteMinus(int userId , int commentId);
	public int unvote(int userId , int commentId);
	public int countPlus();
	public int countMinus();
	public int count(int commentId);
	public int checkUserVote(int userId , int commentId);
	public boolean selectAnswer(int commentId , int parentId);
}
