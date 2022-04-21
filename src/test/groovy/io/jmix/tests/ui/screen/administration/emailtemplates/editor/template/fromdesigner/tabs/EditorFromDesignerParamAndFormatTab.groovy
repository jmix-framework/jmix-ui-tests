package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromdesigner.tabs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EditorFromDesignerParamAndFormatTab extends Composite<EditorFromDesignerParamAndFormatTab> implements TableActionsTrait {

    public static final String INPUT_PARAMETERS_TABLE_J_TEST_ID = "inputParametersTable"
    public static final String VALUES_FORMATS_TABLE_J_TEST_ID = "valuesFormatsTable"

    @Wire(path = ["parametersFragment", "create"])
    Button createParameter

    @Wire(path = ["parametersFragment", "edit"])
    Button editParameter

    @Wire(path = ["parametersFragment", "remove"])
    Button removeParameter

    @Wire
    Button createValueFormat

    @Wire
    Button editValueFormat

    @Wire
    Button removeValueFormat
}
