package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.DateField
import io.jmix.masquerade.component.TextField
import org.openqa.selenium.support.FindBy

class HolidayEditor extends Composite<HolidayEditor> {

    @Wire(path = 'holidayTypeComboBox')
    ComboBox holidayType

    @Wire(path = 'dayOfWeekCombobox')
    ComboBox dayOfWeek

    @Wire
    DateField fixedDateField

    @Wire
    ComboBox monthField

    @Wire
    ComboBox dayField

    @Wire
    TextField cronExpressionField

    @Wire
    TextField descriptionField

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'OK\']')
    Button commitAndCloseBtn

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'Close\']')
    Button closeBtn
}
