package io.jmix.tests.ui.test.webdav

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.application.wdenabledentity.WDEnabledEntityBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.BaseLoginUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static com.codeborne.selenide.Selectors.byCssSelector
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.DISABLED
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

class WebDAVBaseUITest extends BaseLoginUiTest implements UiHelper {
    public static final String WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID = "enabledVersEntitiesTable"
    public static final String WEBDAV_DISABLED_VERSIONING_TABLE_J_TEST_ID = "disabledVersEntitiesTable"
    public static final String WEBDAV_DOCUMENTS_TABLE_J_TEST_ID = "webdavDocumentsTable"
    public static final String WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID = "webdavDocumentVersionsTable"
    public static final String SHOW_VERSION_BTN_J_TEST_ID = "showVersion"

    public static final String RESOURCES_PATH = "src/main/resources/"

    public static final String BASE_FILENAME = "helloworld.txt"
    public static final String BASE_FILE_PATH = RESOURCES_PATH + BASE_FILENAME

    public static final String BIG_FILENAME = "bigsizefile.jpg"
    public static final String BIG_FILENAME_PATH = RESOURCES_PATH + BIG_FILENAME

    public static final String DOCUMENT_IS_NOT_LOCKED_NOTIFICATION_CAPTION = "The document is not locked"
    public static final String V1 = "v1"
    public static final String V2 = "v2"

    static String getUniqueName(String baseString) {
        return baseString + getGeneratedString()
    }

    static void cleanTempFile(String fileNamePath) {
        File file = new File(fileNamePath)
        file.delete()
    }

    static def removeEntity(String entityName, String tableJTestId) {
        $j(WDEnabledEntityBrowse).with {
            selectRowInTableByText(entityName, tableJTestId)
            clickButton(removeBtn)
        }

        $j(ConfirmationDialog).with {
            clickButton(yes)
        }

        $j(WDEnabledEntityBrowse).with {
            checkRecordIsNotDisplayed(entityName, tableJTestId)
        }
    }

    static def checkVersionedDocumentInBrowser(String fileName, String buttonCaption) {
        $j(WebDAVDocumentBrowse).with {
            selectRowInTableByText(fileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            manageVersionsBtn.shouldBe(VISIBLE, ENABLED)
            $(byCssSelector("tr.v-selected span.v-button-caption"))
                    .shouldHave(Condition.text(buttonCaption))
        }
    }

    static def checkNotVersionedDocumentInBrowser(String fileName) {
        $j(WebDAVDocumentBrowse).with {
            selectRowInTableByText(fileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            manageVersionsBtn.shouldBe(VISIBLE, DISABLED)
            $(byCssSelector("tr.v-selected span.v-button-caption"))
                    .shouldNotBe(VISIBLE)
        }
    }

    static void checkUploadedWDFilename(String uniqueFileName) {
        SelenideElement wdLink = $(byCssSelector("div.webdav-document-link span"))
        wdLink.shouldHave(Condition.text(uniqueFileName))
    }

    static void checkVersionButtonCaption(String version) {
        $j(Button, SHOW_VERSION_BTN_J_TEST_ID).shouldHave(caption(version))
    }
}
