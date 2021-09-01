package io.jmix.tests.entity.searchProgremmic;

import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import io.jmix.search.index.annotation.ManualMappingDefinition;
import io.jmix.search.index.mapping.processor.MappingDefinition;
import io.jmix.search.index.mapping.strategy.AutoMappingStrategy;

import static io.jmix.search.index.mapping.ParameterKeys.INDEX_FILE_CONTENT;

@JmixEntitySearchIndex(entity = PersonProgrammingMapping.class)
public interface PersonProgrammingMappingDefinition {
    @ManualMappingDefinition
    default MappingDefinition mapping() {
        return MappingDefinition.builder()
                .newElement()
                .includeProperties("*", "user.*")
                .excludeProperties("description", "user.email")
                .usingFieldMappingStrategyClass(AutoMappingStrategy.class)
                .withParameter(INDEX_FILE_CONTENT, true)
                .buildElement()
                .buildMappingDefinition();
    }
}
