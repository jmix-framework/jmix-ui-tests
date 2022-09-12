package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.DateField
import io.jmix.masquerade.component.TextField
import org.openqa.selenium.support.FindBy

class WorkingScheduleEditor extends Composite<WorkingScheduleEditor> {



    @FindBy(xpath = '//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']//*[span=\'OK\']')
    Button commitAndCloseBtn

    @FindBy(xpath = '//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']//*[span=\'Close\']')
    Button closeBtn
}
