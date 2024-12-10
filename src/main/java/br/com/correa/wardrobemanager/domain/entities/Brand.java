package br.com.correa.wardrobemanager.domain.entities;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
@AllArgsConstructor
public class Brand {
    private String code;
    private String name;
    private URI webSite;
}
