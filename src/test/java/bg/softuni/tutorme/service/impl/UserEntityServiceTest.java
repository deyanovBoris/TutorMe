package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.dtos.UserRegisterDTO;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.repositories.RoleRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    private UserEntityServiceImpl toTest;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp(){
        toTest = new UserEntityServiceImpl(
                mockUserRepository,
                mockRoleRepository,
                mockPasswordEncoder
        );
    }

    @Test
    void testRegisterUser(){

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO()
                .setUsername("testUsername")
                .setEmail("test@mail")
                .setFullName("test first last")
                .setPassword("topsecret")
                .setConfirmPassword("topsecret");

        Mockito.when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                        .thenReturn("testPassMocked");
        Mockito.when(this.mockRoleRepository.findByRole(UserRoleEnum.USER))
                        .thenReturn(
                            Optional.of(
                                new UserRoleEntity()
                                    .setId(1)
                                    .setRole(UserRoleEnum.USER)
                            )
                        );

        toTest.registerUser(userRegisterDTO);

        Mockito.verify(mockUserRepository).save(userEntityCaptor. capture());

        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertEquals(userRegisterDTO.getUsername(), actualSavedEntity.getUsername());
        Assertions.assertEquals(userRegisterDTO.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(userRegisterDTO.getFullName(), actualSavedEntity.getName());
        Assertions.assertEquals("testPassMocked", actualSavedEntity.getPassword());

    }

    @Test
    void testGetUserIdStringByUsernameExisting() throws UserNotFoundException {
        UserEntity user = new UserEntity().setId(1);

        Mockito.when(this.mockUserRepository.findByUsername("existingUsername"))
                        .thenReturn(Optional.of(user));

        String userIdAsString = toTest.getUserIdByUsername("existingUsername");
        Assertions.assertEquals("1", userIdAsString);
    }

    @Test
    void testGetUserIdStringByUsernameNonExistingShouldThrow() {
        Assertions.assertThrows(UserNotFoundException.class,
                () -> this.toTest.getUserIdByUsername("nonExistingUser"));
    }
}
