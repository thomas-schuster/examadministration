package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "UserData")
@NamedQuery(name = "User.loginUser", query = "Select u from User u where u.userId = :userId and u.password= :password")
public class User implements Serializable {

    @Size(min = 4, message = " - password must be longer than 3 letters")
    private String password;
    //userId is expected to be in email format
    @Id
    @Pattern(regexp = "[_\\w-]+(\\.[_\\w-]+)*@[\\w-]+(\\.[\\w]+)*(\\.[A-Za-z]{2,3})", message = " - no valid email")
    private String userId;

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
