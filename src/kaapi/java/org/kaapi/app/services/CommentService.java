package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Comment;

public interface CommentService {
	public List<Comment> listComment(int videoid, int offset, int limit);
	public List<Comment> listComment(int offset, int limit);
	public List<Comment> listComment(String commentText, int offset, int limit);
	public List<Comment> listSuperComment(int offset, int limit);
	public boolean insert(Comment comment);
	public boolean reply(Comment comment);
	public boolean update(Comment comment);
	public boolean delete(int commentId);
	public int countComment();
	public int countComment(String commentText);
	public int countComment(int videoId);
	public int countSuperComment();
	public int countReplyComment(int videoId, int replyId);
	public Comment getComment(int commentId);
	public List<Comment> listReplyComment(int videoId, int replyId, int limit, int offset);
	
}
