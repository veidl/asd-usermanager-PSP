package at.ac.fhcampuswien.asdusermanager.mapper;

import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.dto.UserDTO;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.mapper.crypto.EncodedMapping;
import at.ac.fhcampuswien.asdusermanager.mapper.crypto.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class},
        uses = {PasswordEncoderMapper.class})
public interface UserMapper {

    @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    UserEntity toEntity(RegisterDTO dto);

    UserDTO toDto(UserEntity entity);
}
