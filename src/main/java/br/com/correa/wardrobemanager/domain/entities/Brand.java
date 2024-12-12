package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.Validate;

import java.net.URI;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
public class Brand {
    private String code;
    private String name;
    private URI webSite;

    public static BrandBuilder builder() {
        return new CustomBrandBuilder();
    }

    private static class CustomBrandBuilder extends BrandBuilder {

        @Override
        public Brand build() {
            try {
                Validate.notBlank(super.code, "Brand code cannot be blank");
                Validate.notBlank(super.name, "Brand name cannot be blank");
            } catch (NullPointerException|IllegalArgumentException e) {
                throw new InvalidEntityAttributeException(e.getMessage());
            }

            return super.build();
        }
    }

}
