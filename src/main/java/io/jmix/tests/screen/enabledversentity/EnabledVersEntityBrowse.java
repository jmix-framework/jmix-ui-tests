package io.jmix.tests.screen.enabledversentity;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.webdav.EnabledVersEntity;

@UiController("EnabledVersEntity.browse")
@UiDescriptor("enabled-vers-entity-browse.xml")
@LookupComponent("enabledVersEntitiesTable")
public class EnabledVersEntityBrowse extends StandardLookup<EnabledVersEntity> {
}