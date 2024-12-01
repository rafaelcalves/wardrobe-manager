package br.com.correa.wardrobemanager.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Jacksonized
@AllArgsConstructor
public class Piece {
    private String description;
    private Brand brand;
    private String predominantColorHex;
    private Category category;
    private String fabric;
    private List<URI> images;
    private List<URI> links;

    @Override
    public String toString() {
        return "{" +
                "\"description\":\"" + description + "\"," +
                "\"brand\":" + brand + "," +
                "\"predominantColorHex\":\"" + predominantColorHex + "\"," +
                "\"category\":" + category + "," +
                "\"fabric\":\"" + fabric + "\"," +
                "\"images\":" + printList(images) + "," +
                "\"links\":" + printList(links) + "," +
                "\"class\":\"" + this.getClass().getName() + "\"" +
                "}";
    }

    private String printList(List<?> list) {
        return "[" + list.stream()
                .map(element -> "\"" + element.toString() + "\"")
                .collect(Collectors.joining(",")) + "]";
    }
}
