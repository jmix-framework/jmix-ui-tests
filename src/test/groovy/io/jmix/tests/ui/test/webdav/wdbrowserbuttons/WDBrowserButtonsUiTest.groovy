package io.jmix.tests.ui.test.webdav.wdbrowserbuttons


import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.administration.webdav.dialog.DocumentVersionDialog
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

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "main.liquibase.contexts=base")
@ContextConfiguration(initializers = TestContextInitializer)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WDBrowserButtonsUiTest extends WebDAVBaseUITest {
    public fileNameArray = []
    public filePathArray = []

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()
    }

    @AfterAll
    void afterAll() {
        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()

        // removing WebDAV documents from db
        fileNameArray.forEach(fileName -> {
            $j(WebDAVDocumentBrowse).with {
                selectRowInTableByText(fileName as String, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
                clickButton(lockBtn)
                clickButton(removeBtn)
            }
            $j(ConfirmationDialog).confirmChanges()
        })

        // removing created files in `resource` directory
        filePathArray.forEach(filePath -> {
            cleanTempFile(filePath as String)
        })
    }

    @Test
    @DisplayName("Imports file with size more than 20mb")
    void importFileWithTooBigSize() {
        File file = new File(BIG_FILENAME_PATH)

        def notificationCaption = "Your file '".concat(BIG_FILENAME)
                .concat("' is too big. File size limit equals 20 MB")
        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, file)
            checkNotification(notificationCaption)
            checkRecordIsNotDisplayed(BIG_FILENAME, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Disables and enables versioning for WD file")
    void changeVersioningForFile() {
        def firstFileName = getUniqueName(BASE_FILENAME)
        def firstFileNamePath = RESOURCES_PATH + firstFileName

        filePathArray.add(firstFileNamePath)

        def firstFile = createNewFile(BASE_FILE_PATH, firstFileNamePath)

        def secondFileName = getUniqueName(BASE_FILENAME)
        def secondFileNamePath = RESOURCES_PATH + secondFileName

        fileNameArray.add(secondFileName)
        filePathArray.add(secondFileNamePath)

        def secondFile = createNewFile(BASE_FILE_PATH, secondFileNamePath)

        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, firstFile)
            checkRecordIsDisplayed(firstFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            selectRowInTableByText(firstFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(manageVersionsBtn)

            $j(DocumentVersionDialog).with {
                checkRecordIsDisplayed(firstFileName, WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)
                uploadNewDocument(upload, secondFile)
                checkRecordIsDisplayed(secondFileName, WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)
                clickButton(ok)
            }
            checkRecordIsDisplayed(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            selectRowInTableByText(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(disableVersioningBtn)
            checkNotVersionedDocumentInBrowser(secondFileName)

            selectRowInTableByText(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(enableVersioningBtn)
            checkVersionedDocumentInBrowser(secondFileName, V2)
        }
    }

    @Test
    @DisplayName("Removes non-locked WD file")
    void removeNonLockedFile() {
        def uniqueFileName = getUniqueName(BASE_FILENAME)
        def uniqueFileNamePath = RESOURCES_PATH + uniqueFileName

        fileNameArray.add(uniqueFileName)
        filePathArray.add(uniqueFileNamePath)

        def uniqueFile = createNewFile(BASE_FILE_PATH, uniqueFileNamePath)

        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, uniqueFile)
            checkRecordIsDisplayed(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            selectRowInTableByText(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(removeBtn)
            $j(ConfirmationDialog).confirmChanges()

            checkNotification(DOCUMENT_IS_NOT_LOCKED_NOTIFICATION_CAPTION)

            checkRecordIsDisplayed(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Creates new file version using Copy to head button in ManageVersionDialog")
    void createNewFileVersionWithCopyToHeadButton() {
        def uniqueFileName = getUniqueName(BASE_FILENAME)
        def uniqueFileNamePath = RESOURCES_PATH + uniqueFileName

        fileNameArray.add(uniqueFileName)
        filePathArray.add(uniqueFileNamePath)

        def uniqueFile = createNewFile(BASE_FILE_PATH, uniqueFileNamePath)

        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, uniqueFile)
            checkRecordIsDisplayed(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            selectRowInTableByText(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(manageVersionsBtn)

            $j(DocumentVersionDialog).with {
                selectRowInTableByText(uniqueFileName, WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)
                clickButton(copyToHeadButton)
                checkRecordIsDisplayed("2*", WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)

                clickButton(refresh)
                checkRecordIsDisplayed(uniqueFileName, WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)
                checkRecordIsNotDisplayed("2*", WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)

                selectRowInTableByText(uniqueFileName, WEBDAV_DOCUMENT_VERSIONS_TABLE_J_TEST_ID)
                clickButton(copyToHeadButton)
                clickButton(ok)
            }
            checkVersionedDocumentInBrowser(uniqueFileName, V2)
        }
    }

}
