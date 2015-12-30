package org.kaapi.app.services;

import java.util.ArrayList;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;


public interface PlayListService {
	public ArrayList<Playlist> list(Pagination pagin , Playlist dto);
	public ArrayList<Playlist> listVideoInPlaylist(int playlistid , Pagination pagin );
	public String getPlaylistName(int playlistid);
	public ArrayList<Playlist> listVideo(int playlistid);
	public ArrayList<Playlist> listplaylistname(Playlist dto);
	public ArrayList<Playlist> listplaylistbyPublicView(boolean publicview);
	public ArrayList<Playlist> listplaylistbyAdmin(boolean publicview);
	public ArrayList<Playlist> listplaylistdetail(int userid);
	public ArrayList<Playlist> listplaylistdetail(int userid , int playlistid);
	public Playlist get(int playlistid);
	public Playlist getPlaylistForUpdate(int playlistid);
	public boolean addVideoToPlst(int pid , int vid );
	public boolean insert(Playlist dto);
	public boolean update(Playlist dto);
	public boolean delete(int playlistid);
	public int count(String keyword);
	public int countUserPlaylist(String keyword, int userid);
	public int countvideos(int playlistid);
	public ArrayList<Playlist> recommendPlaylist();
	public boolean deleteVideoFromPlaylist(int playlistid , int vid);
	public boolean updateThumbnail(int vid , int pid);
	public boolean updateThumbnailToDefault(int pid);
}
