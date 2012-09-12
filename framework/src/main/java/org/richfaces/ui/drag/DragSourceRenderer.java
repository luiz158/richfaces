/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.ui.drag;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.cdk.annotations.JsfRenderer;
import org.richfaces.javascript.DnDScript;
import org.richfaces.javascript.DragScript;

/**
 * @author abelevich
 *
 */
@ResourceDependencies({ @ResourceDependency(library = "org.richfaces", name = "base-component.reslib"),
        @ResourceDependency(name = "jquery.position.js"),
        @ResourceDependency(name = "richfaces-event.js"),
        @ResourceDependency(library = "org.richfaces", name = "jquery-ui-core.js"),
        @ResourceDependency(library = "org.richfaces", name = "jquery-ui-widget.js"),
        @ResourceDependency(library = "org.richfaces", name = "jquery-ui-mouse.js"),
        @ResourceDependency(library = "org.richfaces", name = "jquery-dnd.js"),
        @ResourceDependency(library = "org.richfaces", name = "dnd-draggable.js") })
@JsfRenderer(type = "org.richfaces.DragSourceRenderer", family = AbstractDragSource.COMPONENT_FAMILY)
public class DragSourceRenderer extends DnDRenderBase {
    @Override
    public Map<String, Object> getOptions(FacesContext facesContext, UIComponent component) {
        Map<String, Object> options = new HashMap<String, Object>();
        if (component instanceof AbstractDragSource) {
            AbstractDragSource dragSource = (AbstractDragSource) component;
            options.put("indicator", getDragIndicatorClientId(facesContext, dragSource));
            options.put("type", dragSource.getType());
            options.put("parentId", getParentClientId(facesContext, component));
        }
        return options;
    }

    @Override
    public String getScriptName() {
        return "new RichFaces.ui.Draggable";
    }

    @Override
    public DnDScript createScript(String name) {
        return new DragScript(name);
    }

    public String getDragIndicatorClientId(FacesContext facesContext, AbstractDragSource dragSource) {
        String indicatorId = dragSource.getDragIndicator();
        if (indicatorId != null) {
            UIComponent indicator = getUtils().findComponentFor(facesContext, dragSource, indicatorId);
            if (indicator != null) {
                indicatorId = indicator.getClientId(facesContext);
            }
        }
        return indicatorId;
    }
}
