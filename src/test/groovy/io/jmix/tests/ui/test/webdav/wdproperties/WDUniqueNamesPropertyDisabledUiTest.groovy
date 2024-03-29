package io.jmix.tests.ui.test.webdav.wdproperties

import io.jmix.core.DataManager
import io.jmix.core.security.SystemAuthenticator
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.webdav.WebDAVBaseUITest
import io.jmix.webdav.entity.WebdavDocument
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@Disabled
// need to recreate context to enable property overriding see @DirtiesContext
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(classes = [JmixUiTestsApplication],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["jmix.webdav.auto-generate-unique-resource-uri=false", "main.liquibase.contexts=base"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WDUniqueNamesPropertyDisabledUiTest extends WebDAVBaseUITest {

    @Autowired
    private SystemAuthenticator authenticator

    @Autowired
    private DataManager dataManager

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()
    }

    @Test
    @DisplayName("Uploads files with same name with disabled property 'auto-generate-unique-resource-uri'")
    void uploadFilesWithSameNameInWDBrowser() {
        def uniqueFileName = getUniqueName(BASE_FILENAME)
        def uniqueFileNamePath = RESOURCES_PATH + uniqueFileName

        def uniqueFile = createNewFile(BASE_FILE_PATH, uniqueFileNamePath)

        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, uniqueFile)
            checkRecordIsDisplayed(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            uploadNewDocument(uploadBtn, uniqueFile)
            authenticator.withSystem(() -> {
                dataManager.load(WebdavDocument.class)
                        .all()
                        .list()
                        .forEach(doc -> log.debug("Full path: " + doc.getFullPath()))
            })
            checkNotificationDescription("The document name is already taken. Please choose a different name.")

            selectRowInTableByText(uniqueFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(lockBtn)
            clickButton(removeBtn)
            $j(ConfirmationDialog).confirmChanges()
        }
        cleanTempFile(uniqueFileNamePath)
    }
}
