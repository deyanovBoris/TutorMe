package bg.softuni.tutorme.service.session;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.entities.user.TutorMeUserDetails;
import bg.softuni.tutorme.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class TutorMeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public TutorMeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);

        if (!byUsername.isPresent()){
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        UserDetails userDetails = map(byUsername.get());
        return userDetails;
    }

    private static UserDetails map(UserEntity userEntity){
        return new TutorMeUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles()
                        .stream()
                        .map(UserRoleEntity::getRole)
                        .map(TutorMeUserDetailsService::map)
                        .toList());
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
