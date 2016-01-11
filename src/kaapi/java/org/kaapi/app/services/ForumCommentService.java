package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddAnswer;
import org.kaapi.app.forms.FrmAddQuestion;

public interface ForumCommentService {
	
	public List<ForumComment> listAllQuestion(Pagination pagination);
	public int countQuestion();
	public List<ForumComment> listQuestionByUserid(String userid , Pagination pagination);
	public int countQuestionByUserid(String userid);
	public List<ForumComment> listQuestionByCategoryId(String cateid , Pagination pagination);
	public int countQuestionByCategoryId(String cateid);
	public List<ForumComment> listQuestionByTitle(String title , Pagination pagination );
	public int countQuestionByTitle(String title  );
	
	public List<ForumComment> listAnswerByQuestionId(String parentId, Pagination pagination);
	public int countAnswerByQuestionId(String parentId);
	public int countComment();
	public ForumComment getQuestionById(String commentId);
	
	public boolean insertAnswer(FrmAddAnswer addAnswer);
	public boolean insetQuestion(FrmAddQuestion addQuestion);
	public boolean deleteComment(String commentId);
	
	public String[] getAllTags();
}