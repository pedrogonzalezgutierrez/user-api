package com.kiesoft.userapi.mapper.user

import com.kiesoft.userapi.dto.role.RoleDTO
import com.kiesoft.userapi.dto.user.UserDTO
import com.kiesoft.userapi.jpa.entity.role.RoleEntity
import com.kiesoft.userapi.jpa.entity.user.UserEntity
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.tuple;

class UserMapperSpec extends Specification {

    final userMapper = new UserMapperImpl()

    def "will convert an Entity to DTO"() {
        given:
        final firstRole = new RoleEntity.Builder()
                .id(UUID.randomUUID())
                .name("ROLE_ADMIN")
                .build()

        final secondRole = new RoleEntity.Builder()
                .id(UUID.randomUUID())
                .name("ROLE_EDITOR")
                .build()

        final userEntity = new UserEntity.Builder()
                .id(UUID.randomUUID())
                .name("Pedro")
                .password("Real Betis")
                .enabled(Boolean.TRUE)
                .roles([firstRole, secondRole])
                .points(100)
                .build()

        when:
        final userDTO = userMapper.asDTO(userEntity)

        then:
        with(userDTO) {
            id == userEntity.id
            name == userDTO.name
            password == userEntity.password
            enabled == userEntity.enabled
            points == userEntity.points
        }
        assertThat(userDTO.roles)
                .extracting("id", "name")
                .contains(
                tuple(firstRole.id, firstRole.name),
                tuple(secondRole.id, secondRole.name))
    }

    def "will convert a DTO to Entity"() {
        given:
        final firstRole = new RoleDTO.Builder()
                .id(UUID.randomUUID())
                .name("ROLE_ADMIN")
                .build()

        final secondRole = new RoleDTO.Builder()
                .id(UUID.randomUUID())
                .name("ROLE_EDITOR")
                .build()

        final userDTO = new UserDTO.Builder()
                .id(UUID.randomUUID())
                .name("Pedro")
                .password("Real Betis")
                .enabled(Boolean.TRUE)
                .roles([firstRole, secondRole])
                .points(100)
                .build()

        when:
        final userEntity = userMapper.asEntity(userDTO)

        then:
        with(userEntity) {
            id == userDTO.id
            name == userDTO.name
            password == userDTO.password
            enabled == userDTO.enabled
            points == userDTO.points
        }
        assertThat(userEntity.roles)
                .extracting("id", "name")
                .contains(
                tuple(firstRole.id, firstRole.name),
                tuple(secondRole.id, secondRole.name))
    }

}
