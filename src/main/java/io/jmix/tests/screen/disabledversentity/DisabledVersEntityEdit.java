package io.jmix.tests.screen.disabledversentity;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.webdav.DisabledVersEntity;

@UiController("DisabledVersEntity.edit")
@UiDescriptor("disabled-vers-entity-edit.xml")
@EditedEntityContainer("disabledVersEntityDc")
public class DisabledVersEntityEdit extends StandardEditor<DisabledVersEntity> {
}