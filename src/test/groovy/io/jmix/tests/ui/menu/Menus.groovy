/*
 * Copyright (c) 2008-2013 Haulmont. All rights reserved.
 */

package io.jmix.tests.ui.menu

import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse

final class Menus {
    public static final SideMenu.Menu<UserBrowse> USER_BROWSE =
            new SideMenu.Menu<>(UserBrowse, 'application', 'User.browse')

    public static final SideMenu.Menu<DynamicAttributeBrowse> DYNAMIC_ATTRIBUTES_BROWSE =
            new SideMenu.Menu<>(DynamicAttributeBrowse, 'administration', 'dynat_Category.browse')

}
