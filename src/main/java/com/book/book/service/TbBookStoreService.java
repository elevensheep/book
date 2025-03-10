package com.book.book.service;

import com.book.book.dto.TbBookStoreDto;
import com.book.book.dto.TbBookStoreResponseDto;
import com.book.book.entity.TbBookStore;
import com.book.book.repository.TbBookStoreRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class TbBookStoreService {

    private final WebClient.Builder webClientBuilder;

    private final TbBookStoreRepository tbBookStoreRepository;

    @Value("${ttb.key}")
    private String ttbKey;

    public Mono<Void> fetchAndSaveData(String itemId) {
        String url = "http://www.aladin.co.kr/ttb/api/ItemOffStoreList.aspx"
                + "?TTBKey=" + ttbKey
                + "&itemIdType=ISBN13"  // TODO : 10자리 13자리 분기
                + "&ItemId=" + itemId
                + "&output=xml";

        // WebClient를 사용하여 API 호출
        return webClientBuilder.baseUrl(url)
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(xmlResponse -> {
                    if (xmlResponse != null) {
                        System.out.println("API 응답 XML:");
                        System.out.println(xmlResponse);
                        try {
                            TbBookStoreResponseDto apiResponse = parseXml(xmlResponse);

                            // 매장 정보 저장
                            if (apiResponse.getItemOffStoreList() != null) {
                                for (TbBookStoreDto store : apiResponse.getItemOffStoreList()) {
                                    TbBookStore tbBookStore = new TbBookStore();
                                    tbBookStore.setOffName(store.getOffName());
                                    tbBookStore.setLink(store.getLink());
                                    tbBookStoreRepository.save(tbBookStore);
                                }
                            }
                        } catch (JAXBException e) {
                            return Mono.error(e);
                        }
                    }
                    return Mono.empty();
                });
    }

    // JAXB를 이용해 XML을 DTO로 변환
    private TbBookStoreResponseDto parseXml(String xmlResponse) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TbBookStoreResponseDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlResponse);
        return (TbBookStoreResponseDto) unmarshaller.unmarshal(reader);
    }
}
