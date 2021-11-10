package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "UserData")
@NamedQuery(name = "User.loginUser", query = "Select u from User u where u.userId = :userId and u.password= :password")
public class User implements Serializable {

    String firstName;
    String lastName;
    @Size(min = 4, message = " - password must be longer than 3 letters")
    private String password;
    //userId is expected to be in email format
    @Id
    @Pattern(regexp = "[_\\w-]+(\\.[_\\w-]+)*@[\\w-]+(\\.[\\w]+)*(\\.[A-Za-z]{2,3})", message = " - no valid email")
    private String userId;
    
    @ManyToMany
    private List<UserGroup> userGroups;
    

    public User() {
        this.userGroups = new ArrayList<>();
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = DigestUtils.sha256Hex(password);
        this.userGroups = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
    
     public void addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
    }
    
}
