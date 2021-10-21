package io.jmix.tests.sampler.test_support.component.grouptable;

import io.jmix.masquerade.component.GroupTable;

public interface SamplerGroupTable extends GroupTable {

    /**
     * Expands all group rows. Each row will be expanded after the interval.
     *
     * @param interval the delay between expanding rows
     */
    SamplerGroupTable expandAllWithInterval(int interval);

    /**
     * Collapses all group rows. Each row will be collapsed after the interval.
     *
     * @param interval the delay between collapsing rows
     */
    SamplerGroupTable collapseAllWithInterval(int interval);
}
