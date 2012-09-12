package org.richfaces.ui.output;

import java.io.IOException;

import org.junit.Test;
import org.richfaces.renderkit.RendererTestBase;
import org.xml.sax.SAXException;

public class TabPanelRendererTest extends RendererTestBase {
    @Test
    public void testTabPanel() throws IOException, SAXException {
        doTest("tabPanel", "f:tabPanel");
    }

    @Test
    public void testTab() throws IOException, SAXException {
        doTest("tab", "f:tabPanel");
    }
}
