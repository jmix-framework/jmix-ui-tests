package io.jmix.tests.ui.extension


import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension

class SpringBootExtension extends SpringExtension {

    protected static final String UI_BASE_HOST_PROPERTY = 'jmix.tests.ui.baseHost'
    protected static final String LOCAL_SERVER_PORT_PROPERTY = 'local.server.port'
    protected static final String CONTEXT_PATH_PROPERTY = 'server.servlet.contextPath'
    protected static final String SELENIDE_BASE_URL_PROPERTY = 'selenide.baseUrl'

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        super.beforeEach(context)

        initSelenideBaseUrl(getApplicationContext(context))
    }

    protected void initSelenideBaseUrl(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment()
        String baseHost = environment.getProperty(UI_BASE_HOST_PROPERTY)
        String localServerPort = environment.getProperty(LOCAL_SERVER_PORT_PROPERTY)
        String contextPath = environment.getProperty(CONTEXT_PATH_PROPERTY)
        String selenideBaseUrl = baseHost + ':' + localServerPort + contextPath
        System.setProperty(SELENIDE_BASE_URL_PROPERTY, selenideBaseUrl)
    }
}
