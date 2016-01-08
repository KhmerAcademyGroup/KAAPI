package org.kaapi.app.services;

import org.kaapi.app.forms.FrmSelectAnswer;
import org.kaapi.app.forms.FrmVote;

public interface ForumVoteService {

	public int votePlus(FrmVote vote);
	public int vote(String userId , String commentId , int voteType);
	public int voteMinus(FrmVote vote);
	public int unvote(FrmVote vote);
	public int countPlus();
	public int countMinus();
	public int count(String commentId);
	public int checkUserVote(FrmVote vote);
	public boolean selectAnswer(FrmSelectAnswer selectAnswer);
}
