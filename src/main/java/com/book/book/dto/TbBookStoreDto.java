package com.book.book.dto;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbBookStoreDto {
    private String offName;
    private String link;
}

//public class TbBookStoreDto {
//
//    private String offCode;
//    private String offName;
//    private String link;
//
//    @XmlElement
//    public String getOffCode() {
//        return offCode;
//    }
//
//    public void setOffCode(String offCode) {
//        this.offCode = offCode;
//    }
//
//    @XmlElement
//    public String getOffName() {
//        return offName;
//    }
//
//    public void setOffName(String offName) {
//        this.offName = offName;
//    }
//
//    @XmlElement
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//}
