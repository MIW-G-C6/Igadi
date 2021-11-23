package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This user class is used for the creation of accounts. When the user creates an account, they are automatically
 *     saved with the role 'gardener'. They can get the role 'garden manager' when they create a garden. User login
 *     with their email address and their self assigned password.
 */

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId", unique = true)
    private Integer userId;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(unique = true, name = "userEmail", nullable = false)
    @Email
    private String userEmail;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        /*for (GardenUser gardenUser : this.gardenUsers) {
            authorityList.add(new SimpleGrantedAuthority(
                    (gardenUser.getGarden().getGardenId() + "_" + gardenUser.getRole())));
        }*/
        return authorityList;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> changeAuthority(String role) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(role));
        return authorityList;
    }
}