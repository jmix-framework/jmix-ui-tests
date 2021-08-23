package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class JPQLQueryEditorDialog extends Composite<JPQLQueryEditorDialog> implements TableActionsTrait {
    @Wire
    Button runBtn

    @Wire
    Button nextBtn
}
