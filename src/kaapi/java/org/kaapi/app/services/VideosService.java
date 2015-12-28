package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Video;

public interface VideosService {
	public List<Video> listVideo(int offset, int limit);
	public List<Video> listVideo(boolean status, int offset, int limit);
	public List<Video> listVideo(String videoName, int offset, int limit);
	public List<Video> listVideo(String videoName, boolean status, int offset, int limit);
	public List<Video> listVideo(int userId, int offset, int limit);
	public List<Video> listVideo(int userId, boolean status, int offset, int limit);
	public List<Video> listVideo(int userId, String VideoName, int offset, int limit);
	public List<Video> listVideo(int userId, String VideoName, boolean status, int offset, int limit);
	public List<Video> getRelateVideo(String categoryName, int limit);
	public List<Video> categoryVideo(int categoryid, int offset, int limit);
	public List<Video> categoryVideo(int categoryid, boolean status, int offset, int limit);
	public List<Video> topVoteAndRecent(int limit);
	public Video getVideo(int videoId, boolean viewCount);
	public int insert(Video video);
	public boolean update(Video video);
	public boolean delete(int videoId);
	public boolean toggleVideo(int videoId);
	public boolean insertVideoToCategory(int videoId, int categoryId);
	public boolean removeVideoFromCategory(int videoId);
	public int countVideo();
	public int countVideo(boolean status);
	public int countVideo(String videoName);
	public int countVideo(String videoName, boolean status);
	public int countVideo(int userId);
	public int countVideo(int userId, boolean status);
	public int countVideo(int userId, String videoName);
	public int countVideo(int userId, String videoName, boolean status);
	public int countUser();
	public int countPlaylist();
	public int countCategoryVideo(int categoryId);
	public int countCategoryVideo(int categoryId, boolean status);
	public int countForum();
	public List<Video> topVote(int limit);
	public List<Video> recentVideo(int limit);
}
