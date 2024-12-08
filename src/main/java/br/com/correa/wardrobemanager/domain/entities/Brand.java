package br.com.correa.wardrobemanager.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;

@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@AllArgsConstructor
public class Brand {
    private String code;
    private String name;
    private URI webSite;

    @Override
    public String toString() {
        return """
                {
                    "code": "%s",
                    "name": "%s",
                    "webSite": "%s",
                    "class": "%s"
                }""".formatted(this.code, this.name, this.webSite, this.getClass().getName());
    }
}
