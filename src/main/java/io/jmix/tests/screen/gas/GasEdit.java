package io.jmix.tests.screen.gas;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.Gas;

@UiController("Gas.edit")
@UiDescriptor("gas-edit.xml")
@EditedEntityContainer("gasDc")
public class GasEdit extends StandardEditor<Gas> {
}