package bg.softuni.tutorme.service.session;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.entities.user.TutorMeUserDetails;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.session.TutorMeUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class TutorMeUserDetailServiceTest {

    private TutorMeUserDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    private static final String EXISTING_USERNAME = "test_user";
    private static final String NON_EXISTING_USERNAME = "non_existing_user";

    @BeforeEach
    void setUp(){
        toTest = new TutorMeUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUserName_UserFound(){
        UserEntity user = new UserEntity()
                .setUsername(EXISTING_USERNAME)
                .setName("Test Name")
                .setEmail("example@gmail.com")
                .setPassword("topsecret")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.USER),
                        new UserRoleEntity().setRole(UserRoleEnum.TUTOR),
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN)));

        Mockito.when(mockUserRepository.findByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = this.toTest.loadUserByUsername(EXISTING_USERNAME);

        Assertions.assertInstanceOf(TutorMeUserDetails.class,userDetails);

        TutorMeUserDetails tutorMeUserDetails = (TutorMeUserDetails) userDetails;

        Assertions.assertEquals(user.getUsername(), tutorMeUserDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), tutorMeUserDetails.getPassword());
        Assertions.assertEquals(user.getRoles().size(), tutorMeUserDetails.getAuthorities().size());

        List<String> expectedRoles = user.getRoles()
                .stream()
                .map(r -> "ROLE_" + r.getRole().name())
                .sorted()
                .collect(Collectors.toList());
        List<String> actualRoles = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserNotFound_ShouldThrow(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.toTest.loadUserByUsername(NON_EXISTING_USERNAME));
    }
}
