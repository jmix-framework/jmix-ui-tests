package io.jmix.tests.screen.disabledversentity;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.webdav.DisabledVersEntity;

@UiController("DisabledVersEntity.browse")
@UiDescriptor("disabled-vers-entity-browse.xml")
@LookupComponent("disabledVersEntitiesTable")
public class DisabledVersEntityBrowse extends StandardLookup<DisabledVersEntity> {
}