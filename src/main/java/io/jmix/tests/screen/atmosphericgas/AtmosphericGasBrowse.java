package io.jmix.tests.screen.atmosphericgas;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.AtmosphericGas;

@UiController("AtmosphericGas.browse")
@UiDescriptor("atmospheric-gas-browse.xml")
@LookupComponent("atmosphericGasesTable")
public class AtmosphericGasBrowse extends StandardLookup<AtmosphericGas> {
}