package org.kaapi.app.services;

import java.sql.ResultSet;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;


public interface PlayListService {
	public ResultSet list(Pagination pagin , Playlist dto);
	public ResultSet listVideoInPlaylist(int playlistid , Pagination pagin );
	public String getPlaylistName(int playlistid);
	public ResultSet listVideo(int playlistid);
	public ResultSet listplaylistname(Playlist dto);
	public ResultSet listplaylistbyPublicView(boolean publicview);
	public ResultSet listplaylistbyAdmin(boolean publicview);
	public ResultSet listplaylistdetail(int userid);
	public ResultSet listplaylistdetail(int userid , int playlistid);
	public Playlist get(int playlistid);
	public Playlist getPlaylistForUpdate(int playlistid);
	public boolean addVideoToPlst(int pid , int vid );
	public boolean insert(Playlist dto);
	public boolean update(Playlist dto);
	public boolean delete(int playlistid);
	public int count(String keyword);
	public int countUserPlaylist(String keyword, int userid);
	public int countvideos(int playlistid);
	public ResultSet recommendPlaylist();
	public boolean deleteVideoFromPlaylist(int playlistid , int vid);
	public boolean updateThumbnail(int vid , int pid);
	public boolean updateThumbnailToDefault(int pid);
}
