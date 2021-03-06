package org.richfaces.ui.validator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Test for dynamic add/remove {@link org.richfaces.ui.core.UIScripts} as view resource.
 *
 * @author asmirnov
 *
 */
public class GraphValidationTest extends ValidatorIntegrationTestBase {

    protected String getFacesConfig() {
        return "graph-faces-config.xml";
    }

    protected String getPageName() {
        return "graph-test";
    }

    @Override
    protected String getResourcePath() {
        return "org/richfaces/ui/validator/";
    }

    @Test
    public void testRequest() throws Exception {
        HtmlPage page = requestPage();
        HtmlInput input = getInput(page);
        assertNotNull(input);
    }

    @Override
    protected HtmlPage submit(HtmlPage page) throws IOException {
        HtmlElement submit = page.getElementById("form:submit");
        return submit.click();
    }

    @Test
    public void testSubmitTooShortValue() throws Exception {
        HtmlPage page = submitValueAndCheckMessage("", containsString(GraphBean.SHORT_MSG));
        checkMessage(page, "textMessage", containsString(GraphBean.SHORT_MSG));
        checkMessage(page, "graphMessage", equalTo(""));
    }

    @Test
    public void testBeanLevelConstrain() throws Exception {
        HtmlPage page = submitValueAndCheckMessage("bar", equalTo(GraphBean.FOO_MSG));
        checkMessage(page, "graphMessage", containsString(GraphBean.FOO_MSG));
        checkMessage(page, "textMessage", equalTo(""));
    }

    @Test
    public void testCorrectValue() throws Exception {
        submitValueAndCheckMessage("foobar", equalTo(""));
    }
}
