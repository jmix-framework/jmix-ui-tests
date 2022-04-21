package io.jmix.tests.ui.test.emailtemplates

import io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.common.tabblock.EditorMainTabTemplateDefaultAddresses
import io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.common.tabblock.EditorMainTabTemplateDetailsBlock
import io.jmix.tests.ui.test.BaseUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static io.jmix.masquerade.Selectors.$j

abstract class BaseEmailTemplateUiTest extends BaseUiTest implements UiHelper {

    static void fillTemplateDetailsBlockFields(String templateName, String templateCode) {
        $j(EditorMainTabTemplateDetailsBlock).with {
            fillRequiredTextField(name, templateName)
            fillRequiredTextField(code, templateCode)
        }
    }

    static void fillDefaultAddressesBlockFields(String templateSubject, String templateEmailFrom, String templateEmailTo, String templateEmailCc, String templateEmailBcc) {
        $j(EditorMainTabTemplateDefaultAddresses).with {
            fillTextField(subject, templateSubject)
            fillTextField(from, templateEmailFrom)
            fillTextField(to, templateEmailTo)
            fillTextField(cc, templateEmailCc)
            fillTextField(bcc, templateEmailBcc)
        }
    }
}
