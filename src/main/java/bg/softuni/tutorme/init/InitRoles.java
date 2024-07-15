package bg.softuni.tutorme.init;

import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.repositories.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitRoles {
    private final RoleRepository roleRepository;

    public InitRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public void initializeRoles(){
        if (this.roleRepository.count() == 0){
            //if role db is empty
            roleRepository.saveAll(createRoleEntities());
        }
    }
    private static List<UserRoleEntity> createRoleEntities() {
        return Arrays.stream(UserRoleEnum.values())
                .map(r -> new UserRoleEntity().setRole(r))
                .collect(Collectors.toList());
    }


}
