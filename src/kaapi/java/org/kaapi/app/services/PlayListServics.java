package org.kaapi.app.services;

import java.sql.Array;
import java.util.ArrayList;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.PlaylistDetail;
import org.kaapi.app.entities.Video;
import org.kaapi.app.forms.FrmCreatePlaylist;
import org.kaapi.app.forms.FrmUpdatePlaylist;


public interface PlayListServics {
	public ArrayList<Playlist> UserPlayList(String userid);
	public ArrayList<Playlist> UserPlayList(String userid, Pagination pagin);
	public ArrayList<Playlist> listUserPlayList(String userid);
	public ArrayList<Playlist> listPlayListByMainCategory(String categoryid);
	public ArrayList<Playlist> searchPlayList(String kesearch);
	public int countVideoInPlayList(int playlisid);
	public ArrayList<Playlist> litsMainElearning();
	public ArrayList<Playlist> listMainPlaylist();
	public ArrayList<Playlist> listAllPlaylist( Pagination pagin);
	public ArrayList<Playlist> list(Pagination pagin , Playlist dto);
	public ArrayList<Video> listVideoInPlaylist(String playlistid , Pagination pagin );
	public ArrayList<Video> listVideoInPlaylist(String playlistid );
	public String getPlaylistName(String playlistid);
	public ArrayList<Video> listVideo(String playlistid);
	public Playlist listplaylistname(Playlist dto);
	public ArrayList<Playlist> listplaylistbyPublicView(boolean publicview);
	public ArrayList<Playlist> listplaylistbyAdmin(boolean publicview);
	public ArrayList<PlaylistDetail> listplaylistdetail(String userid);
	public ArrayList<Playlist> listplaylistdetail(String userid , String playlistid);
	public Playlist get(String playlistid);
	public Playlist getPlaylistForUpdate(String playlistid);
	public boolean addVideoToPlst(String pid , String vid );
	public boolean insert(FrmCreatePlaylist playlist);
	public boolean update(FrmUpdatePlaylist playlist);
	public boolean delete(String playlistid);
	public int count(String keyword);
	public int countUserPlaylist( String userid);
	public int countUserPlaylist( String userid, String pname);
	public int countvideos(String playlistid);
	public ArrayList<Playlist> recommendPlaylist();
	public boolean deleteVideoFromPlaylist(String playlistid , String vid);
	public boolean updateThumbnail(String vid , String pid);
	public boolean updateThumbnailToDefault(String pid);
	public int countPlayList();
}
