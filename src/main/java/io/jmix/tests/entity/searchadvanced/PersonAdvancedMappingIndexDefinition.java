package io.jmix.tests.entity.searchadvanced;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = PersonAdvancedMapping.class)
public interface PersonAdvancedMappingIndexDefinition {
    @AutoMappedField(includeProperties = {"*", "user.*"},
            excludeProperties = {"description", "user.email", "picture"},
            indexFileContent = true)
    void personMapping();

    @AutoMappedField(includeProperties = {"picture"},
            indexFileContent = false)
    void personMapping1();
}
