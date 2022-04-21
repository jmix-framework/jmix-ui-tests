package io.jmix.tests.ui.test.emailtemplates.templatesactions

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.emailtemplates.browse.EmailTemplateBrowse
import io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromdesigner.EmailTemplateFromDesignerEditor
import io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromreport.EmailTemplateFromReportEditor
import io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromreport.tabs.EditorFromReportMainTab
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.emailtemplates.BaseEmailTemplateUiTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.tests.ui.test.reports.BaseReportUiTest.createBasicHTMLReportForCompanyEntity
import static io.jmix.tests.ui.test.reports.BaseReportUiTest.getReportUniqueName
import static io.jmix.tests.ui.test.reports.BaseReportUiTest.COMPANY_ENTITY_NAME
import static io.jmix.tests.ui.test.reports.BaseReportUiTest.REPORTS_TABLE_JTEST_ID

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base'])
@ContextConfiguration(initializers = TestContextInitializer)
class EmailTemplatesCrudUiTest extends BaseEmailTemplateUiTest {

    public static final String TEMPLATE_FROM_DESIGNER_BASE_NAME = "Template from designer"
    public static final String TEMPLATE_BASE_CODE = "templatecode"
    public static final String TEMPLATE_SUBJECT = "Subject "
    public static final String TEMPLATE_EMAIL_FROM = "email_from@test.test"
    public static final String TEMPLATE_EMAIL_TO = "email_to@test.test"
    public static final String TEMPLATE_EMAIL_CC = "email_cc@test.test"
    public static final String TEMPLATE_EMAIL_BCC = "email_bcc@test.test"
    public static final String DESIGN_BASED = "Design-based"
    public static final String REPORT_BASED = "Report-based"

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
    }

    @Test
    @DisplayName("Creates an email template from designer")
    void createTemplateFromDesigner() {
        $j(MainScreen).openEmailTemplateBrowse()

        def templateName = TEMPLATE_FROM_DESIGNER_BASE_NAME.concat(getGeneratedString())
        def templateCode = TEMPLATE_BASE_CODE.concat(getGeneratedString())
        def templateSubject = TEMPLATE_SUBJECT.concat(getGeneratedString())
        def templateEmailFrom = TEMPLATE_EMAIL_FROM.concat(getGeneratedString())
        def templateEmailTo = TEMPLATE_EMAIL_TO.concat(getGeneratedString())
        def templateEmailCc = TEMPLATE_EMAIL_CC.concat(getGeneratedString())
        def templateEmailBcc = TEMPLATE_EMAIL_BCC.concat(getGeneratedString())

        $j(EmailTemplateBrowse).with {
            createBtn.shouldBe(VISIBLE, ENABLED).click("From designer")
        }

        $j(EmailTemplateFromDesignerEditor).with {

            fillTemplateDetailsBlockFields(templateName, templateCode)
            fillDefaultAddressesBlockFields(templateSubject, templateEmailFrom, templateEmailTo, templateEmailCc, templateEmailBcc)
            clickButton(ok)
        }

        $j(EmailTemplateBrowse).with {
            checkRecordByTwoCellsIsDisplayed(templateName, DESIGN_BASED, EMAIL_TEMPLATES_TABLE_J_TEST_ID)
        }

    }

    @Test
    @DisplayName("Creates an email template from report")
    void createTemplateFromReport(){
        def templateName = TEMPLATE_FROM_DESIGNER_BASE_NAME.concat(getGeneratedString())
        def templateCode = TEMPLATE_BASE_CODE.concat(getGeneratedString())
        def templateSubject = TEMPLATE_SUBJECT.concat(getGeneratedString())
        def templateEmailFrom = TEMPLATE_EMAIL_FROM.concat(getGeneratedString())
        def templateEmailTo = TEMPLATE_EMAIL_TO.concat(getGeneratedString())
        def templateEmailCc = TEMPLATE_EMAIL_CC.concat(getGeneratedString())
        def templateEmailBcc = TEMPLATE_EMAIL_BCC.concat(getGeneratedString())
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        $j(MainScreen).openReportsBrowse()

        createBasicHTMLReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }

        $j(MainScreen).openEmailTemplateBrowse()
        $j(EmailTemplateBrowse).with {
            createBtn.shouldBe(VISIBLE, ENABLED).click("From report")
        }

        $j(EmailTemplateFromReportEditor).with {

            fillTemplateDetailsBlockFields(templateName, templateCode)
            fillDefaultAddressesBlockFields(templateSubject, templateEmailFrom, templateEmailTo, templateEmailCc, templateEmailBcc)
            $j(EditorFromReportMainTab).with {
                selectValueInComboBox(emailBody, reportName)
            }
            clickButton(ok)
        }

        $j(EmailTemplateBrowse).with {
            checkRecordByTwoCellsIsDisplayed(templateName, REPORT_BASED, EMAIL_TEMPLATES_TABLE_J_TEST_ID)
        }
    }
}
