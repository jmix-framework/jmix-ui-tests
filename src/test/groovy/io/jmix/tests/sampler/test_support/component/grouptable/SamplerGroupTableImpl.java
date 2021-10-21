package io.jmix.tests.sampler.test_support.component.grouptable;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.jmix.masquerade.component.impl.GroupTableImpl;
import org.openqa.selenium.By;

public class SamplerGroupTableImpl extends GroupTableImpl implements SamplerGroupTable {

    public SamplerGroupTableImpl(By by) {
        super(by);
    }

    @Override
    public SamplerGroupTable expandAllWithInterval(int interval) {
        while (!getGroupRows(this::isRowCollapsed).isEmpty()) {
            SelenideElement collapsedRow = getGroupRows(this::isRowCollapsed)
                    .get(0);

            expandRow(collapsedRow);
            Selenide.sleep(interval);
        }
        return this;
    }

    @Override
    public SamplerGroupTable collapseAllWithInterval(int interval) {
        while (!getGroupRows(this::isRowExpanded).isEmpty()) {
            SelenideElement expandedRow = getGroupRows(this::isRowExpanded)
                    .get(0);

            collapseRow(expandedRow);
            Selenide.sleep(interval);
        }
        return this;
    }
}
