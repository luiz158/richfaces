package org.richfaces.ui.input;

import java.io.File;
import java.net.URISyntaxException;

import org.jboss.test.faces.htmlunit.HtmlUnitEnvironment;
import org.junit.Test;
import org.richfaces.renderkit.RendererTestBase;

public class InputNumberSpinnerRendererTest extends RendererTestBase {

    @Override
    public void setUp() throws URISyntaxException {
        environment = new HtmlUnitEnvironment();
        environment.withWebRoot(new File(this.getClass().getResource(".").toURI()));
        environment.withResource("/WEB-INF/faces-config.xml", "org/richfaces/ui/input/faces-config.xml");
        environment.start();
    }

    @Test
    public void testBasicLayout() throws Exception {
        doTest("inputNumberSpinner", "inputNumberSpinner", "form:spinner");
    }
}
