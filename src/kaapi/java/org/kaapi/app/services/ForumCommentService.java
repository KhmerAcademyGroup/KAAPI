package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddAnswer;
import org.kaapi.app.forms.FrmAddQuestion;
import org.kaapi.app.forms.FrmUpdateAnswer;
import org.kaapi.app.forms.FrmUpdateQuestion;

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
	public int countAnswer();
	public ForumComment getQuestionById(String commentId);
	public ForumComment getSelectedAnswerByQuestionId(String parentId);

	public List<ForumComment> listQuestionByTag(String tag, Pagination pagination);
	public int countQuestionByTag(String tag);

	
	
	
	public boolean insertAnswer(FrmAddAnswer addAnswer);
	public boolean deleteAnswer(String answerId);
	public boolean updateAnswer(FrmUpdateAnswer updateAnswer);
	
	public boolean insetQuestion(FrmAddQuestion addQuestion);
	public boolean deleteQuestion(String questionId);
	public boolean updateQuestion(FrmUpdateQuestion updateQuestion);
	
	public String[] getAllTags();
}