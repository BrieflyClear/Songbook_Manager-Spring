package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = UserSongRatingMapper.class)
public interface UserMapper {

  @Mapping(target = "userRoleId", expression = "java(entity.getUserRole().getId())")
  UserDTO userToUserDTO(User entity);


  User userDTOToUser(UserDTO dto);

  default List<Long> convertPlaylistsToIDs(Set<Playlist> list) {
    return list.stream().mapToLong(Playlist::getId).boxed().collect(Collectors.toList());
  }

    /*default List<Tag> convertIDsToTags(List<Long> list) {
        //return list.stream().mapToLong(Tag::getId).boxed().collect(Collectors.toList());
    }*/
}
