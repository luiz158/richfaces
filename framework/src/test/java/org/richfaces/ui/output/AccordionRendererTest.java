package org.richfaces.ui.output;

import java.io.IOException;

import org.junit.Test;
import org.richfaces.renderkit.RendererTestBase;
import org.xml.sax.SAXException;

public class AccordionRendererTest extends RendererTestBase {
    @Test
    public void testAccordion() throws IOException, SAXException {
        doTest("accordion", "f:accordion");
    }

    @Test
    public void testAccordionItem() throws IOException, SAXException {
        doTest("accordionItem", "f:item");
        doTest("accordionItem", "accordionItemDisabled", "f:item2");
    }
}
