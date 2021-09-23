package io.jmix.tests.ui.screen.administration.datatools.browsers

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class AtmosphericGasBrowse extends Composite<AtmosphericGasBrowse> implements TableActionsTrait {
    @Wire
    Button create
}
