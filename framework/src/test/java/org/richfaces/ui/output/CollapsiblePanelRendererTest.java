package org.richfaces.ui.output;

import java.io.IOException;

import org.junit.Test;
import org.richfaces.renderkit.RendererTestBase;
import org.xml.sax.SAXException;

public class CollapsiblePanelRendererTest extends RendererTestBase {
    @Test
    public void testEmpty() throws IOException, SAXException {
        doTest("collapsiblePanel", "f:panel");
    }
}
