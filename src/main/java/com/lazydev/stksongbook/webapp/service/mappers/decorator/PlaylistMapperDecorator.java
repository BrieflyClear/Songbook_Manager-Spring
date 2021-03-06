package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

@Setter
public abstract class PlaylistMapperDecorator implements PlaylistMapper {

  @Autowired
  @Qualifier("delegate")
  private PlaylistMapper delegate;
  @Autowired
  private SongService songService;
  @Autowired
  private UserService userService;
  @Autowired
  private PlaylistService playlistService;

  @Override
  public Playlist map(PlaylistDTO dto) {
    var playlist = delegate.map(dto);
    playlist.setOwner(userService.findById(dto.getOwnerId()));
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> songs.add(songService.findById(s)));
    playlist.setSongs(songs);
    playlist.setPrivate(dto.getIsPrivate());
    var found = playlistService.findById(dto.getId());
    playlist.setCreationTime(found.getCreationTime());
    return playlist;
  }
}
