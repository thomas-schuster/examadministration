package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.User;
import de.hspf.swt.exam.administration.dao.UserGroup;
import de.hspf.swt.exam.administration.dao.facade.UserFacade;
import java.util.HashSet;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author ThomasSchuster
 */
@ApplicationScoped
public class UserServiceIdentityStore implements IdentityStore {

    @EJB
    UserFacade userManager;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        String email = login.getCaller();
        String password = login.getPasswordAsString();

        Optional<User> optUser = userManager.findByNameAndPassword(email, password);

        if (optUser.isPresent()) {
            User user = optUser.get();
            HashSet<String> set = new HashSet<>();
            // set.addAll(user.getUserGroups() map/filter?);
            user.getUserGroups().forEach((UserGroup group) -> {
                set.add(group.getGroupName());
            });
            return new CredentialValidationResult(user.getUserId(), set);
        } else {
            return CredentialValidationResult.INVALID_RESULT;
        }
    }
}
