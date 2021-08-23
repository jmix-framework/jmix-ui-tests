package io.jmix.tests.ui.screen.application.company

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class CompanyBrowse extends Composite<CompanyBrowse> implements TableActionsTrait{

    @Wire
    Button printBtn
}
