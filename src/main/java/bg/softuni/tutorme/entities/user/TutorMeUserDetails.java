package bg.softuni.tutorme.entities.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class TutorMeUserDetails extends User {

    private final long id;

    public TutorMeUserDetails(
            long id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
