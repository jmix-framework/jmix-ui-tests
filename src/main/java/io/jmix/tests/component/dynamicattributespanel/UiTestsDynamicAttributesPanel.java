package io.jmix.tests.component.dynamicattributespanel;

import com.google.common.base.Strings;
import io.jmix.dynattrui.panel.DynamicAttributesPanel;
import io.jmix.ui.component.CompositeDescriptor;
import io.jmix.ui.meta.CanvasIconSize;
import io.jmix.ui.meta.StudioComponent;

import javax.annotation.Nullable;

@StudioComponent(category = "Components",
        unsupportedProperties = {"enable", "responsive"},
        xmlns = "http://jmix.io/schema/dynattr/ui",
        xmlnsAlias = "dynattr",
        icon = "io/jmix/dynattrui/icon/component/dynamicAttributesPanel.svg",
        canvasIcon = "io/jmix/dynattrui/icon/component/dynamicAttributesPanel_canvas.svg",
        canvasIconSize = CanvasIconSize.LARGE)
@CompositeDescriptor("/io/jmix/dynattrui/panel/dynamic-attributes-panel.xml")
public class UiTestsDynamicAttributesPanel extends DynamicAttributesPanel {

    @Override
    public void setId(@Nullable String id) {
        super.setId(id);

        updateComponentIds(getComposition());
    }

    @Override
    protected String getFullId(String id) {
        return Strings.isNullOrEmpty(getId()) ? id : getId() + "_" + id;
    }
}
