package com.greenlend.greenlend.model;

import com.greenlend.greenlend.dto.OfferPhotoDTO;
import com.greenlend.greenlend.exception.WrongValueException;
import com.greenlend.greenlend.utils.ImageUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;

@Entity(name = "offerPhoto")
public class OfferPhoto {
    @Id
    @GeneratedValue(generator = "image_generator")
    @SequenceGenerator(
            name = "image_generator",
            sequenceName = "image_sequence",
            initialValue = 1000
    )
    private Long photoId;

    @Lob
    @Column
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] hires;

    @Lob
    @Column
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] thumbnail;

    @Column
    private String meta;

    public OfferPhoto(OfferPhotoDTO dto) {
        if (dto.getHiresBase64() == null) {
            throw new WrongValueException("Missing key 'hires'");
        }
        if (dto.getThumbnailBase64() == null) {
            throw new WrongValueException("Missing key 'thumbnail'");
        }

        int indexOfDataStart = dto.getHiresBase64().indexOf(',')+1;
        this.meta = dto.getHiresBase64().substring(0, indexOfDataStart);
        this.hires = DatatypeConverter.parseBase64Binary(dto.getHiresBase64().substring(dto.getHiresBase64().indexOf(',')+1));
        //this.thumbnail = DatatypeConverter.parseBase64Binary(dto.getThumbnailBase64());
        this.thumbnail = ImageUtils.createThumbnailBytes(dto.getHiresBase64());
    }

    public OfferPhoto(byte[] hires, byte[] thumbnail, String meta) {
        this.hires = hires;
        this.thumbnail = thumbnail;
        this.meta = meta;
    }

    public OfferPhoto() {

    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public byte[] getHires() {
        return hires;
    }

    public void setHires(byte[] hires) {
        this.hires = hires;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
