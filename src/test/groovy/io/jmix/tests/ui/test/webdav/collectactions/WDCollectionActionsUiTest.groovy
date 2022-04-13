package io.jmix.tests.ui.test.webdav.collectactions

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.Conditions
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.administration.webdav.dialog.WebdavCollectionDialog
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.webdav.WebDAVBaseUITest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Selectors.byCssSelector
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byJTestId

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContextInitializer)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WDCollectionActionsUiTest extends WebDAVBaseUITest {

    static final String COLLECTION_BASE_NAME = "collection"
    public static final String BREADCRUMBS_LAYOUT_J_TEST_ID = "breadcrumbsLayout"
    public static final String SLASH = "/"
    public collectionNames = []

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()
    }

    @AfterAll
    void afterAll() {
        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()

        // removing collections
        collectionNames.forEach(collectionName -> {
            $j(WebDAVDocumentBrowse).with {
                selectRowInTableByText(collectionName as String, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
                clickButton(removeBtn)
            }
            $j(ConfirmationDialog).confirmChanges()
        })
    }

    @Test
    @DisplayName("Creates a collection in WD document browser")
    void createCollection() {
        def collectionUniqueName = getUniqueName(COLLECTION_BASE_NAME)
        collectionNames.add(collectionUniqueName)

        $j(WebDAVDocumentBrowse).with {
            clickButton(createCollectionBtn)

            $j(WebdavCollectionDialog).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, collectionUniqueName)
                clickButton(ok)
            }
            checkRecordIsDisplayed(collectionUniqueName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edits a collection in WD document browser")
    void editCollection() {
        def collectionFirstName = getUniqueName(COLLECTION_BASE_NAME)
        def collectionSecondName = getUniqueName(COLLECTION_BASE_NAME)
        collectionNames.add(collectionSecondName)

        $j(WebDAVDocumentBrowse).with {
            clickButton(createCollectionBtn)

            $j(WebdavCollectionDialog).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, collectionFirstName)
                clickButton(ok)
            }

            checkRecordIsDisplayed(collectionFirstName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            selectRowInTableByText(collectionFirstName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(renameCollectionBtn)

            $j(WebdavCollectionDialog).with {
                nameField.shouldBe(Conditions.REQUIRED).shouldHave(value(collectionFirstName))
                fillTextField(nameField, collectionSecondName)
                clickButton(ok)
            }

            checkRecordIsDisplayed(collectionSecondName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Removes a collection in WD document browser")
    void removeCollection() {
        def collectionUniqueName = getUniqueName(COLLECTION_BASE_NAME)

        $j(WebDAVDocumentBrowse).with {
            clickButton(createCollectionBtn)

            $j(WebdavCollectionDialog).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, collectionUniqueName)
                clickButton(ok)
            }
            checkRecordIsDisplayed(collectionUniqueName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            selectRowInTableByText(collectionUniqueName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(removeBtn)

            $j(ConfirmationDialog).confirmChanges()

            checkRecordIsNotDisplayed(collectionUniqueName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Opens a collection by Path field in WD document browser")
    void goToCollectionUsingPathInput() {
        def collectionUniqueName = getUniqueName(COLLECTION_BASE_NAME)
        collectionNames.add(collectionUniqueName)

        $j(WebDAVDocumentBrowse).with {
            clickButton(createCollectionBtn)

            $j(WebdavCollectionDialog).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, collectionUniqueName)
                clickButton(ok)
            }
            checkRecordIsDisplayed(collectionUniqueName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            filePathField.shouldHave(Condition.value(SLASH))
            fillTextField(filePathField, SLASH.concat(collectionUniqueName))
            clickButton(applyFilePathBtn)

            SelenideElement breadcrumbsLayout = $(byJTestId(BREADCRUMBS_LAYOUT_J_TEST_ID))
            breadcrumbsLayout.shouldBe(VISIBLE)

            SelenideElement collectionBreadcrumb = $(byCssSelector("div.v-label-jmix-breadcrumbs-win-caption"))
            collectionBreadcrumb.shouldHave(Condition.text(collectionUniqueName))
        }
    }

}
