package io.jmix.tests.screen.user;

import io.jmix.tests.entity.User;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;

@UiController("tests_User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {
}
