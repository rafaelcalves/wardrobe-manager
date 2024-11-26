package br.com.correa.wardrobemanager.domain.entities;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Piece {
    private String description;
    private String brandCode;
    private String predominantColorHex;
    private String categoryCode;
    private String fabric;
    private List<URI> images;
    private List<URI> links;

    public Piece(String description, String brandCode, String predominantColorHex,
                 String categoryCode, String fabric, List<URI> images, List<URI> links) {
        this.description = description;
        this.brandCode = brandCode;
        this.predominantColorHex = predominantColorHex;
        this.categoryCode = categoryCode;
        this.fabric = fabric;
        this.images = images;
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) return false;
        return Objects.equals(getDescription(), piece.getDescription()) && Objects.equals(getBrandCode(), piece.getBrandCode()) && Objects.equals(getPredominantColorHex(), piece.getPredominantColorHex()) && Objects.equals(getCategoryCode(), piece.getCategoryCode()) && Objects.equals(getFabric(), piece.getFabric()) && Objects.equals(getImages(), piece.getImages()) && Objects.equals(getLinks(), piece.getLinks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getBrandCode(), getPredominantColorHex(), getCategoryCode(), getFabric(), getImages(), getLinks());
    }

    @Override
    public String toString() {
        return "{" +
                "description: \"" + description + "\"," +
                "brandCode: \"" + brandCode + "\"," +
                "predominantColorHex: \"" + predominantColorHex + "\"," +
                "categoryCode: \"" + categoryCode + "\"," +
                "fabric: \"" + fabric + "\"," +
                "images: " + Arrays.deepToString(images.toArray()) + "," +
                "links: " + Arrays.deepToString(links.toArray()) +  "," +
                "class: \"" + this.getClass().getName() + "\"" +
                "}";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getPredominantColorHex() {
        return predominantColorHex;
    }

    public void setPredominantColorHex(String predominantColorHex) {
        this.predominantColorHex = predominantColorHex;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public List<URI> getImages() {
        return images;
    }

    public void setImages(List<URI> images) {
        this.images = images;
    }

    public List<URI> getLinks() {
        return links;
    }

    public void setLinks(List<URI> links) {
        this.links = links;
    }
}
