package io.jmix.tests.ui.screen.reports.screen

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ShowReportTableScreen extends Composite<ShowReportTableScreen> implements TableActionsTrait{

    @Wire(path="reportEntityComboBox")
    ComboBox reports
}
