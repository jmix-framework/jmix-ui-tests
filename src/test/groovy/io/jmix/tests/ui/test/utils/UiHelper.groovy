package io.jmix.tests.ui.test.utils

import com.codeborne.selenide.Condition
import io.jmix.masquerade.Selectors
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import org.testcontainers.shaded.org.apache.commons.io.FileUtils

import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Conditions.description
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byClassName
import static io.jmix.masquerade.Selectors.byClassName
import static io.jmix.masquerade.Selectors.withText

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
     * Selects param in defined Combobox by string value with setting filter
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
     * Selects param in defined Combobox by string value without filter
     * @param comboBox
     * @param strValue
     */
    static void selectValueWithoutFilterInComboBox(ComboBox comboBox, String strValue) {
        comboBox.openOptionsPopup()
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

    /**
     * Uploads renamed copy of defined file
     * @param fileUploadField - FileUploadField component
     * @param filePath - path to file in the 'resource' directory
     * @param newFilePath - new file name
     */
    static void uploadNewDocument(FileUploadField fileUploadField, String filePath, String newFilePath) {
        File file = new File(filePath)
        File newFile = new File(newFilePath)
        FileUtils.copyFile(file, newFile)

        fileUploadField.upload(newFile)
    }

    /**
     * Checks displayed name of uploaded file
     * @param fileName - name of uploaded file
     */
    static void checkUploadedFilename(String fileName) {
        $(byChain(byClassName("v-button-jmix-fileupload-filename"), byClassName("v-button-caption")))
                .shouldHave(Condition.text(fileName))
    }

}