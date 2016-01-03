package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;

public interface ForumCommentService {
	
	public List<ForumComment> listAllQuestion(Pagination pagination);
	public int countQuestion();
	public List<ForumComment> listQuestionByUserid(int userid , Pagination pagination);
	public int countQuestionByUserid(int userid);
	public List<ForumComment> listQuestionByCategoryId(int cateid , Pagination pagination);
	public int countQuestionByCategoryId(int cateid);
	public List<ForumComment> listQuestionByTitle(String title , Pagination pagination );
	public int countQuestionByTitle(String title  );
	
	public List<ForumComment> listAnswerByQuestionId(int parendId, Pagination pagination);
	public int countAnswerByQuestionId(int parentId);
	public int countComment();
	public ForumComment getQuestionById(int commentId);
	
	public boolean inserForumComment(ForumComment forumcomment);
	public boolean insertAnswer(ForumComment forumcomment);
	public boolean insetQuestion(ForumComment forumcomment);
	public boolean deleteComment(int commentId);
	
	public String[] getAllTags();
}