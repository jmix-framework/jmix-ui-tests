package io.jmix.tests.screen.enabledversentity;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.webdav.EnabledVersEntity;

@UiController("EnabledVersEntity.edit")
@UiDescriptor("enabled-vers-entity-edit.xml")
@EditedEntityContainer("enabledVersEntityDc")
public class EnabledVersEntityEdit extends StandardEditor<EnabledVersEntity> {
}