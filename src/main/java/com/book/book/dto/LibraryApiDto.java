package com.book.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)// Root 요소가 "channel"인 XML을 처리
public class LibraryApiDto {

    @XmlElement(name = "channel")
    private Channel channel;

    @XmlElement(name = "totalCount")  // 추가: 응답에 포함된 totalCount 매핑
    private int totalCount;

    @XmlElement(name = "list")  // 추가: 응답에 포함된 totalCount 매핑
    private int list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Channel {

        @XmlElement(name = "item")
        private List<LibraryApiDto.Item> items;
    }


    // Item 클래스: <item>
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {

        @XmlElement(name = "drCode")
        private String drCode;

        @XmlElement(name = "recomisbn")
        private String recomisbn;
    }
}


