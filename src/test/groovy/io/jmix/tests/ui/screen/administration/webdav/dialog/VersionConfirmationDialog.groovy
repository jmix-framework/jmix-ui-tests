package io.jmix.tests.ui.screen.administration.webdav.dialog

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.base.Composite

import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId
import static io.jmix.masquerade.Selectors.withText

class VersionConfirmationDialog extends Composite<VersionConfirmationDialog> {

    static void clickCreateNewVersionButton(){
        SelenideElement button = $(byChain(byJTestId("optionDialog_yes"), withText("Create new version")))
        button.click()
    }

    static void clickReplaceButton(){
        SelenideElement button = $(byChain(byJTestId("optionDialog_yes"), withText("Replace document")))
        button.click()
    }

}
