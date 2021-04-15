package io.jmix.tests.screen.moon;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spacebody.Moon;

@UiController("Moon.edit")
@UiDescriptor("moon-edit.xml")
@EditedEntityContainer("moonDc")
public class MoonEdit extends StandardEditor<Moon> {
}
