package io.jmix.tests.ui.screen.administration.datatools.browsers

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class GasBrowse extends Composite<GasBrowse> implements TableActionsTrait {

    @Wire(path = "lookupSelectAction")
    Button ok

    @Wire(path = "lookupCancelAction")
    Button cancel

}
