package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.CategoryVideo;
import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;

public interface VideoService {
	
	public List<Video> listVideo(int offset, int limit);	
	public List<Video> listVideo(String videoName, int offset, int limit);
	public List<Video> listVideo(int userId, int offset, int limit);
	public List<Video> listVideo(int userId, String VideoName, int offset, int limit);
	public List<Video> getRelateVideo(String categoryName, int offset, int limit);
	public List<CategoryVideo> categoryVideo(int categoryid, int offset, int limit);
	public List<Comment> listComment(int videoid, int offset, int limit);
	public List<Playlist> listPlaylist(int videoId, int offset, int limit);
	public List<Video> topvVoteAndRecent(int offset, int limit);
	public Video getVideo(int videoId, boolean viewCount);
	public boolean insert(Video video);
	public boolean update(Video video);
	public boolean delete(int videoId);
	public boolean insertVideoToCategory(int videoId, int categoryId);
	public boolean removeVideoFromCategory(int videoId);
	public boolean removeVideoFromCategory(int videoId, int categoryId);
	public boolean insertVideoToPlaylist(int videoId, int playlistId, int index);
	public boolean removeVideoFromPlaylist(int videoId, int playlistId);
	public boolean updateVideoIndex(int videoId, int playlistId, int index);
	public int countVideo();
	public int countVideo(String videoName);
	public int countUser();
	public int countPlaylist();
	public int countPlaylist(int videoId);
	public int countUserVideo(int userId);
	public int countCategoryVideo(int categoryId);
	public int countVotePlus(int videoId);
	public int countVoteMinus(int videoId);
	public int countComment(int videoId);
	public int countForum();
	
}
