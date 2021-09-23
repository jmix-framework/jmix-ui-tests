package io.jmix.tests.screen.localizeddynamic;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.LocalizedDynamic;

@UiController("LocalizedDynamic.edit")
@UiDescriptor("localized-dynamic-edit.xml")
@EditedEntityContainer("localizedDynamicDc")
public class LocalizedDynamicEdit extends StandardEditor<LocalizedDynamic> {
}