package io.jmix.tests.ui.test.webdav.wdentities

import com.codeborne.selenide.Selenide
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.administration.webdav.dialog.VersionConfirmationDialog
import io.jmix.tests.ui.screen.application.wdenabledentity.WDEnabledEntityBrowse
import io.jmix.tests.ui.screen.application.wdenabledentity.WDEnabledEntityEditor
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
class WDEnabledVersioningUiTest extends WebDAVBaseUITest {

    public fileNameArray = []
    public filePathArray = []
    public entityNameArray = []

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        $j(MainScreen).openWDEnabledEntityBrowse()
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

        // removing created entities
        $j(MainScreen).openWDEnabledEntityBrowse()
        entityNameArray.forEach(entityName -> {
            removeEntity(entityName as String, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
        })
        $j(MainScreen).logout()
    }

    @Test
    @DisplayName("Creates an entity with WD file with enabled versioning")
    void createWDEnabledEntity() {
        def uniqueFileName = getUniqueName(BASE_FILENAME)
        def uniqueFileNamePath = RESOURCES_PATH + uniqueFileName

        fileNameArray.add(uniqueFileName)
        filePathArray.add(uniqueFileNamePath)

        def uniqueFile = createNewFile(BASE_FILE_PATH, uniqueFileNamePath)

        def entityName = getUniqueName("name")
        entityNameArray.add(entityName)

        $j(WDEnabledEntityBrowse).with {
            clickButton(createBtn)
        }

        $j(WDEnabledEntityEditor).with {
            fillTextField(nameField, entityName)
            uploadNewDocument(fileField, uniqueFile)
            checkUploadedWDFilename(uniqueFileName)
            checkVersionButtonCaption(V1)
            Selenide.sleep(300)
            clickButton(ok)
        }

        $j(WDEnabledEntityBrowse).with {
            checkRecordIsDisplayed(entityName, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
        }

        $j(MainScreen).openWebDAVDocumentBrowse()
        checkVersionedDocumentInBrowser(uniqueFileName, V1)
    }

    @Test
    @DisplayName("Uploads a new version of entity's file, WD versioning is enabled")
    void uploadNewVersionWDEnabledEntity() {
        def uniqueFileName = getUniqueName(BASE_FILENAME)
        def uniqueFileNamePath = RESOURCES_PATH + uniqueFileName

        fileNameArray.add(uniqueFileName)
        filePathArray.add(uniqueFileNamePath)

        def uniqueFile = createNewFile(BASE_FILE_PATH, uniqueFileNamePath)

        def entityName = getUniqueName("name")
        entityNameArray.add(entityName)

        $j(WDEnabledEntityBrowse).with {
            clickButton(createBtn)
        }

        $j(WDEnabledEntityEditor).with {
            fillTextField(nameField, entityName)
            uploadNewDocument(fileField, uniqueFile)
            checkVersionButtonCaption(V1)
            Selenide.sleep(500)
            clickButton(ok)
        }

        $j(WDEnabledEntityBrowse).with {
            checkRecordIsDisplayed(entityName, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
            selectRowInTableByText(entityName, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
            clickButton(editBtn)
        }

        $j(WDEnabledEntityEditor).with {
            uploadNewDocument(fileField, uniqueFile)
        }

        $j(VersionConfirmationDialog).clickCreateNewVersionButton()

        $j(WDEnabledEntityEditor).with {
            Selenide.sleep(500)
            checkVersionButtonCaption(V2)
            clickButton(ok)
        }

        $j(MainScreen).openWebDAVDocumentBrowse()
        checkVersionedDocumentInBrowser(uniqueFileName, V2)
    }

    @Test
    @DisplayName("Replace an existing entity's document with new document, WD versioning is enabled")
    void uploadNewDocumentToWDEnabledEntity() {
        def firstFileName = getUniqueName(BASE_FILENAME)
        def firstFilePath = RESOURCES_PATH + firstFileName
        filePathArray.add(firstFilePath)

        def firstFile = createNewFile(BASE_FILE_PATH, firstFilePath)

        def secondFileName = getUniqueName(BASE_FILENAME)
        def secondFilePath = RESOURCES_PATH + secondFileName
        fileNameArray.add(secondFileName)
        filePathArray.add(secondFilePath)

        def secondFile = createNewFile(BASE_FILE_PATH, secondFilePath)

        def entityName = getUniqueName("name")
        entityNameArray.add(entityName)

        $j(WDEnabledEntityBrowse).with {
            clickButton(createBtn)
        }

        $j(WDEnabledEntityEditor).with {
            fillTextField(nameField, entityName)
            uploadNewDocument(fileField, firstFile)
            checkUploadedWDFilename(firstFileName)
            checkVersionButtonCaption(V1)
            clickButton(ok)
        }

        $j(WDEnabledEntityBrowse).with {
            checkRecordIsDisplayed(entityName, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
            selectRowInTableByText(entityName, WEBDAV_ENABLED_VERSIONING_TABLE_J_TEST_ID)
            clickButton(editBtn)
        }

        $j(WDEnabledEntityEditor).with {
            uploadNewDocument(fileField, secondFile)
        }

        $j(VersionConfirmationDialog).clickReplaceButton()

        $j(WDEnabledEntityEditor).with {
            Selenide.sleep(500)
            checkUploadedWDFilename(secondFileName)
            checkVersionButtonCaption(V1)
            clickButton(ok)
        }

        $j(MainScreen).openWebDAVDocumentBrowse()
        checkVersionedDocumentInBrowser(secondFileName, V1)
    }
}
