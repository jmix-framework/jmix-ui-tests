package io.jmix.tests.ui.test_support.component.checkbox;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import io.jmix.masquerade.component.CheckBox;
import io.jmix.masquerade.component.impl.CheckBoxImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.Selectors.byChain;
import static io.jmix.masquerade.sys.TagNames.INPUT;

public class UiCheckBoxImpl extends CheckBoxImpl implements UiCheckBox {

    public UiCheckBoxImpl(By by) {
        super(by);
    }

    /*
     * CAUTION! Copied from super method.
     */
    @Override
    public CheckBox setChecked(boolean checked) {
        SelenideElement checkBoxInput = $(byChain(by, INPUT))
                .shouldBe(visible)
                .shouldBe(enabled);

        if (checked != isInputChecked(checkBoxInput)) {
            checkBoxInput.sendKeys(Keys.SPACE);
        }

        return this;
    }

    protected boolean isInputChecked(SelenideElement input) {
        try {
            input.shouldHave(CHECKBOX_CHECKED);
            return true;
        } catch (UIAssertionError e) {
            return false;
        }
    }
}
