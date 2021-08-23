package io.jmix.tests.ui.screen.reports.browser

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ReportExecutionBrowse extends Composite<ReportExecutionBrowse> implements TableActionsTrait {

    @Wire
    Button excel

    @Wire
    Button download

}
