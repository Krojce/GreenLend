package com.greenlend.greenlend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenlend.greenlend.model.OfferPhoto;

import javax.xml.bind.DatatypeConverter;

public class OfferPhotoDTO {
    @JsonProperty("hires")
    private String hiresBase64;
    @JsonProperty("thumbnail")
    private String thumbnailBase64;

    public OfferPhotoDTO() {

    }

    public OfferPhotoDTO(OfferPhoto photo) {

        if (photo == null) {
            this.hiresBase64 = null;
            this.thumbnailBase64 = null;
            return;
        }

        this.hiresBase64 = photo.getMeta() + DatatypeConverter.printBase64Binary(photo.getHires());
        this.thumbnailBase64 = photo.getMeta() + DatatypeConverter.printBase64Binary(photo.getThumbnail());
    }

    public OfferPhotoDTO(String hiresBase64, String thumbnailBase64) {
        this.hiresBase64 = hiresBase64;
        this.thumbnailBase64 = thumbnailBase64;
    }

    public String getHiresBase64() {
        return hiresBase64;
    }

    public void setHiresBase64(String hiresBase64) {
        this.hiresBase64 = hiresBase64;
    }

    public String getThumbnailBase64() {
        return thumbnailBase64;
    }

    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }

    @Override
    public String toString() {
        return "OfferPhotoDTO{" +
                "hiresBase64='" + hiresBase64 + '\'' +
                ", thumbnailBase64='" + thumbnailBase64 + '\'' +
                '}';
    }
}
