package br.com.correa.wardrobemanager.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;

@Builder
@Jacksonized
@AllArgsConstructor
public class Brand {
    @Getter
    private String code;
    private String name;
    private URI webSite;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"webSite\":\"" + webSite + "\"," +
                "\"class\":\"" + this.getClass().getName() + "\"" +
                "}";
    }
}
