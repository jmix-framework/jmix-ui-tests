package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.DateTimeField
import org.openqa.selenium.support.FindBy

class WorkingScheduleEditor extends Composite<WorkingScheduleEditor> {

    @Wire
    DateTimeField startTimeField

    @Wire
    DateTimeField endTimeField

    @Wire
    Button addAnotherIntervalLinkButton

    @Wire(path = 'dayOfWeekComboBoxField')
    ComboBox dayOfWeekSchedule

    @FindBy(xpath = '//*[@class=\'v-slot v-slot-link v-align-center v-align-middle\']')
    Button deleteIcon

    @FindBy(xpath = '(//*[contains(@class, \'jmix-timefield-wrapper\')])[3]')
    DateTimeField firstIntervalStartTimeField

    @FindBy(xpath = '(//*[contains(@class, \'jmix-timefield-wrapper\')])[4]')
    DateTimeField firstIntervalEndTimeField

    @FindBy(xpath = '(//*[contains(@class, \'jmix-timefield-wrapper\')])[5]')
    DateTimeField secondIntervalStartTimeField

    @FindBy(xpath = '(//*[contains(@class, \'jmix-timefield-wrapper\')])[6]')
    DateTimeField secondIntervalEndTimeField

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'OK\']')
    Button commitAndCloseBtn

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'Close\']')
    Button closeBtn
}
