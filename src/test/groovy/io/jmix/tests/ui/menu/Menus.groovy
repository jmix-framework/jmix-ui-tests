/*
 * Copyright (c) 2008-2013 Haulmont. All rights reserved.
 */

package io.jmix.tests.ui.menu

import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.screen.individual.IndividualBrowse
import io.jmix.tests.ui.screen.administration.audit.EntityLogBrowse
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse

final class Menus {
    public static final SideMenu.Menu<UserBrowse> USER_BROWSE =
            new SideMenu.Menu<>(UserBrowse, 'application', 'User.browse')

    public static final SideMenu.Menu<DynamicAttributeBrowse> DYNAMIC_ATTRIBUTES_BROWSE =
            new SideMenu.Menu<>(DynamicAttributeBrowse, 'administration', 'dynat_Category.browse')

    public static final SideMenu.Menu<EntityInspectorBrowse> ENTITY_INSPECTOR_BROWSE =
            new SideMenu.Menu<>(EntityInspectorBrowse, 'administration', 'entityInspector.browse')
    public static final SideMenu.Menu<EntityLogBrowse> ENTITY_LOG_BROWSE =
            new SideMenu.Menu<>(EntityLogBrowse, 'administration', 'entityLog.browse')

    public static final SideMenu.Menu<IndividualBrowse> INDIVIDUAL_BROWSE =
            new SideMenu.Menu<>(IndividualBrowse, 'application', 'Individual.browse')

    public static final SideMenu.Menu<EntityInspectorBrowse> ENTITY_INSPECTOR =
            new SideMenu.Menu<>(EntityInspectorBrowse, 'administration', 'entityInspector.browse')
}
