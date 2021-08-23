package io.jmix.tests.ui.screen.reports.browser

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ReportGroupBrowse extends Composite<ReportGroupBrowse> implements TableActionsTrait {
    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    Button remove

}
