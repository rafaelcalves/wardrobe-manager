package br.com.correa.wardrobemanager.config;

import br.com.correa.wardrobemanager.application.exceptions.InvalidElementException;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SourceParameterCondition;

import java.util.Objects;

@MapperConfig(uses = MapStructConfig.class, imports = MapStructConfig.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructConfig {

    @SourceParameterCondition
    static boolean assertNotNull(Object value) {
        try {
            Objects.requireNonNull(value);
        } catch (NullPointerException e) {
            throw new InvalidElementException("Element should not be null");
        }
        return true;
    }
}
