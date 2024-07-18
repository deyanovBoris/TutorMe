package bg.softuni.tutorme.init;

import bg.softuni.tutorme.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InitRolesTest {

    private InitRoles toTest;

    @Mock
    private RoleRepository mockRoleRepository;

    @BeforeEach
    void setUp(){
        toTest = new InitRoles(mockRoleRepository);
    }

    @Test
    void testNonEmptyDbDoesNotInsert(){

        toTest.initializeRoles();

    }

}
