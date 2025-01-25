package br.viniciusjomori.forgotpassword.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.viniciusjomori.forgotpassword.User.DTO.UserRegisterDTO;
import br.viniciusjomori.forgotpassword.User.DTO.UserResponseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserRegisterDTO dto);
    UserResponseDTO toResponse(UserEntity entity);

}
