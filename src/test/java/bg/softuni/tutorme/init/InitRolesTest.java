package bg.softuni.tutorme.init;

import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class InitRolesTest {
    private InitRoles toTest;
    @Mock
    private RoleRepository mockRoleRepository;
    @Captor
    private ArgumentCaptor<List<UserRoleEntity>> roleEntityCaptor;

    @BeforeEach
    void setUp(){
        toTest = new InitRoles(mockRoleRepository);
    }

    @Test
    void testEmptyDbDoesInsert(){
        Mockito.when(mockRoleRepository.count()).thenReturn(0L);

        toTest.initializeRoles();

        Mockito.verify(mockRoleRepository).saveAll(roleEntityCaptor.capture());

        List<String> roleNames = roleEntityCaptor.getValue()
                .stream()
                .map(r -> r.getRole().name())
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertLinesMatch(List.of("ADMIN", "TUTOR", "USER"), roleNames);
    }
}
