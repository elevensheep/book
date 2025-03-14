package com.book.book.service;

import com.book.book.dto.LibraryApiDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;  // Collectors 추가

@Service
public class LibraryApiService {

    private final WebClient webClient;

    @Value("${openapi.key}")
    private String apiKey;

    public LibraryApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://nl.go.kr/NL/search/openApi")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    public Mono<List<String>> getRecomisbn() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/saseoApi.do")
                        .queryParam("key", apiKey)
                        .queryParam("startRowNumApi", "1")
                        .queryParam("endRowNumApi", "500")

                        .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            // 에러 상태 코드가 4xx 또는 5xx 일 경우 로그 출력
                            System.err.println("WebClient 호출 실패, 상태 코드: " + clientResponse.statusCode());
                            return Mono.error(new RuntimeException("WebClient 호출 실패"));
                        })
                .bodyToMono(String.class)  // XML 응답을 String으로 받기
                .doOnNext(xmlResponse -> {
                    // XML 응답을 출력
                    System.out.println("응답 받은 XML: ");
                    System.out.println(xmlResponse);
                })
                .map(xmlResponse -> {
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        LibraryApiDto.Channel channel = xmlMapper.readValue(xmlResponse, LibraryApiDto.Channel.class);
                        List<LibraryApiDto.Item> items = Optional.ofNullable(channel)
                                .map(LibraryApiDto.Channel::getItems)
                                .orElseGet(List::of); // items가 null일 경우 빈 리스트를 반환
                        return items.stream()
                                .map(item -> item.getRecomisbn())
                                .collect(Collectors.toList());  // Collectors.toList() 사용
                    } catch (Exception e) {
                        e.printStackTrace();
                        return List.of(); // 오류 발생 시 빈 리스트 반환
                    }
                });
    }
}
