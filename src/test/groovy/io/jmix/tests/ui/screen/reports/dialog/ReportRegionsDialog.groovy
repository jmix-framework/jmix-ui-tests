package io.jmix.tests.ui.screen.reports.dialog

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

import static com.codeborne.selenide.Selectors.byId
import static com.codeborne.selenide.Selenide.$

class ReportRegionsDialog extends Composite<ReportRegionsDialog> implements TableActionsTrait {

    @Wire
    Button runBtn

    @Wire
    Button addSimpleRegionBtn

    @Wire
    Button nextBtn

    static void closeWindow() {
        SelenideElement closeBtn = $(byId("81_window_close"))
        closeBtn.click()
    }
}
