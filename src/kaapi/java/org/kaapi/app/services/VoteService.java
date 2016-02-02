package org.kaapi.app.services;

public interface VoteService {
	
	public int countVote(String videoid);
	public boolean checkVote(String videoid, String userid);
	public boolean vote(String videoid, String userid);
	public boolean unvote(String videoid, String userid);
	public boolean check(String videoid, String userid);
	
}
