package org.richfaces.ui.validator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hamcrest.Matcher;
import org.jboss.test.faces.htmlunit.HtmlUnitEnvironment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public abstract class ValidatorIntegrationTestBase {

    protected HtmlUnitEnvironment environment;
    protected HtmlPage page;

    public ValidatorIntegrationTestBase() {
        super();
    }

    @Before
    public void setUp() {
        this.environment = new HtmlUnitEnvironment();
        this.environment.withResource("/" + getPageName() + ".xhtml", "org/richfaces/component/" + getPageName() + ".xhtml")
                .withResource("/WEB-INF/faces-config.xml", "org/richfaces/component/" + getFacesConfig());
        setupEnvironment(environment);
        this.environment.start();
    }

    protected void setupEnvironment(HtmlUnitEnvironment environment2) {
        // place for additional environment setup
    }

    protected abstract String getFacesConfig();

    protected abstract String getPageName();

    @Rule
    public MethodRule watchment = new TestWatchman() {
        public void failed(Throwable e, org.junit.runners.model.FrameworkMethod method) {
            if (page != null) {
                System.out.println(page.asXml());
            }
        };
    };

    @After
    public void thearDown() throws Exception {
        environment.release();
        environment = null;
    }

    protected HtmlPage submitValueAndCheckMessage(String value, Matcher<String> matcher) throws Exception {
        page = requestPage();
        HtmlInput input = getInput(page);
        page = (HtmlPage) input.setValueAttribute(value);
        page = submit(page);
        checkMessage(page, "uiMessage", matcher);
        return page;
    }

    protected void checkMessage(HtmlPage page, String messageId, Matcher<String> matcher) {
        HtmlElement message = page.getElementById(messageId);
        assertThat(message.getTextContent(), matcher);
    }

    protected HtmlPage submit(HtmlPage page) throws IOException {
        HtmlInput input = getInput(page);
        input.fireEvent("blur");
        return page;
    }

    protected HtmlInput getInput(HtmlPage page) {
        HtmlForm htmlForm = page.getFormByName("form");
        assertNotNull(htmlForm);
        HtmlInput input = htmlForm.getInputByName("form:text");
        return input;
    }

    protected HtmlPage requestPage() throws IOException {
        HtmlPage page = environment.getPage("/" + getPageName() + ".jsf");
        return page;
    }

}
