package io.jmix.tests.ui.extension

import io.jmix.tests.ui.screen.login.LoginScreen
import org.junit.jupiter.api.extension.ExtensionContext

import static io.jmix.masquerade.Components.wire

class DefaultLoginExtension extends DefaultCleanupExtension {

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        super.beforeEach(context)

        wire(LoginScreen.class).submit()
    }
}
