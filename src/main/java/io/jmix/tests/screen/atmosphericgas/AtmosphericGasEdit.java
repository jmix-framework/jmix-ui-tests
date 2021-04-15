package io.jmix.tests.screen.atmosphericgas;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.AtmosphericGas;

@UiController("AtmosphericGas.edit")
@UiDescriptor("atmospheric-gas-edit.xml")
@EditedEntityContainer("atmosphericGasDc")
public class AtmosphericGasEdit extends StandardEditor<AtmosphericGas> {
}