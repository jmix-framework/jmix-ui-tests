package io.jmix.tests.screen.user;

import io.jmix.tests.entity.User;
import io.jmix.core.EntityStates;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@UiController("tests_User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
@Route(value = "users/edit", parentPrefix = "users")
public class UserEdit extends StandardEditor<User> {

    @Autowired
    protected EntityStates entityStates;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected PasswordField passwordField;

    @Autowired
    protected PasswordField confirmPasswordField;

    @Autowired
    protected Notifications notifications;

    @Autowired
    protected MessageBundle messageBundle;

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }
}
