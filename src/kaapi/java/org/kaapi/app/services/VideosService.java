package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;

public interface VideosService {
	public List<Video> listVideo(Pagination page);
	public List<Video> listVideo(boolean status, Pagination page);
	public List<Video> listVideo(String videoName, Pagination page);
	public List<Video> listVideo(String videoName, boolean status, Pagination page);
	public List<Video> listVideoUser(String userId, Pagination page);
	public List<Video> listVideoUser(String userId, boolean status, Pagination page);
	public List<Video> listVideo(String userId, String VideoName, Pagination page);
	public List<Video> listVideo(String userId, String VideoName, boolean status, Pagination page);
	public List<Video> getRelateVideo(String categoryName, int limit);
	public List<Video> categoryVideo(String categoryid, Pagination page);
	public List<Video> categoryVideo(String categoryid, boolean status, Pagination page);
	public List<Video> topVoteAndRecent(int limit);
	public Video getVideo(String videoId, boolean viewCount);
	public Video getVideoWithStatus(String videoId, boolean viewCount, boolean status);
	public int insert(Video video);
	public boolean update(Video video);
	public boolean delete(String videoId);
	public boolean toggleVideo(String videoId);
	public boolean insertVideoToCategory(String videoId, String categoryId);
	public boolean removeVideoFromCategory(String videoId);
	public int countVideo();
	public int countVideo(boolean status);
	public int countVideo(String videoName);
	public int countVideo(String videoName, boolean status);
	public int countVideoUser(String userId);
	public int countVideoUser(String userId, boolean status);
	public int countVideo(String userId, String videoName);
	public int countVideo(String userId, String videoName, boolean status);
	public int countUser();
	public int countPlaylist();
	public int countCourse();
	public int countCategoryVideo(String categoryId);
	public int countCategoryVideo(String categoryId, boolean status);
	public int countForum();
	public List<Video> topVote(int limit);
	public List<Video> recentVideo(int limit);
	public List<Playlist> listMainCategory();
	public List<Playlist> listPlaylist();
	public String getVideoId(int playlistId);
}
