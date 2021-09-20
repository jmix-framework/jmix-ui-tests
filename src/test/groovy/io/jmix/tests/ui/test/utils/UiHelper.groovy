package io.jmix.tests.ui.test.utils

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Conditions.description
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

trait UiHelper {

    /**
     * Clicks defined button
     * @param button - defined button
     */
    static void clickButton(Button button) {
        button.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    /**
     * Checks Notification's appearing and caption with description
     * @param capt - expected caption
     * @param descr - expected description
     */
    static void checkNotification(String capt, String descr) {
        $j(Notification).shouldBe(VISIBLE)
                .shouldHave(caption(capt))
                .shouldHave(description(descr))
        $j(Notification).clickToClose()

    }

    /**
     * Checks Notification's appearing and caption
     * @param capt - expected caption
     */
    static void checkNotification(String capt) {
        $j(Notification).shouldBe(VISIBLE)
                .shouldHave(caption(capt))
        $j(Notification).clickToClose()

    }

    /**
     * Generates string from 4 random symbols
     * @return generated string
     */
    static String getGeneratedString() {
        int leftLimit = 97
        int rightLimit = 122
        int targetStringLength = 4
        Random random = new Random()

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()
        return generatedString
    }

    /**
     * Selects param in defined Combobox by string value
     * @param comboBox
     * @param strValue
     */
    static void selectValueInComboBox(ComboBox comboBox, String strValue) {
        comboBox.setFilter(strValue)
                .getOptionsPopup()
                .select(strValue)
                .shouldHave(value(strValue))
    }

    /**
     * Fills defined field
     * @param field - defined field
     * @param value - defined value
     */
    static void fillTextField(TextField field, String value) {
        field.shouldBe(VISIBLE)
                .shouldBe(EDITABLE)
                .setValue(value)
    }

}