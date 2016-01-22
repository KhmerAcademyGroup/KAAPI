package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Pagination;

public interface CommentService {
	public List<Comment> listSuperCommentOnVideo(String videoid, Pagination page);
	public List<Comment> listReplyCommentOnVideo(String videoId);
	public List<Comment> listCommentOnVideo(String videoid, Pagination page);
	public List<Comment> listComment(Pagination page);
	public List<Comment> listComment(String commentText, Pagination page);
	public List<Comment> listSuperComment(Pagination page);
	public boolean insert(Comment comment);
	public boolean reply(Comment comment);
	public boolean update(Comment comment);
	public boolean delete(String commentId);
	public int countComment();
	public int countComment(String commentText);
	public int countCommentOnVideo(String videoId);
	public int countSuperCommentOnVideo(String videoId);
	public int countSuperComment();
	public int countReplyComment(String videoId, String replyId);
	public Comment getComment(String commentId);
	public List<Comment> listReplyComment(String videoId, String replyId, Pagination page);
	
}
