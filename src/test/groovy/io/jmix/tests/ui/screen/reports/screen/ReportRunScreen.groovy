package io.jmix.tests.ui.screen.reports.screen

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ReportRunScreen extends Composite<ReportRunScreen> implements TableActionsTrait {

    @Wire(path = "runReport")
    Button run

}
