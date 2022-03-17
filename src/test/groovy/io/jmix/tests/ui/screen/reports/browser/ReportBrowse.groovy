package io.jmix.tests.ui.screen.reports.browser

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.PopupButton
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

import static io.jmix.masquerade.Conditions.VISIBLE

class ReportBrowse extends Composite<ReportBrowse> implements TableActionsTrait {

    @Wire
    PopupButton popupCreateBtn

    @Wire
    Button remove

    @Wire
    Button edit

    @Wire(path = "runReport")
    Button run

    @Wire
    Button copy

    @Wire
    Button export

    @Wire(path = "import")
    Button importBtn

    @Wire
    Button executionsButton

    void chooseCreatingTypeInCreatePopupButton(String typeString) {
        popupCreateBtn
                .shouldBe(VISIBLE)
                .openPopupContent()
                .select(typeString)
    }

}
