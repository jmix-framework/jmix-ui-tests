package io.jmix.tests.ui.screen.administration.dynattr

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class DynamicAttributeBrowse extends Composite<DynamicAttributeBrowse> {
    @Wire
    Button createBtn
}