package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ThomasSchuster
 */
@Entity
@Table(name = "UserGroup")
@Data
public class UserGroup implements Serializable {

    @Id
    @Getter @Setter
    private String groupName;
    
    @Getter @Setter
    @ManyToMany(mappedBy = "userGroups")
    private List<User> users;
    
    public UserGroup() {
        users = new ArrayList<>();
    }

    public UserGroup(String groupName) {
        this();
        this.groupName = groupName;
    }

    public void addUser(User user) {
        users.add(user);
    }

}
