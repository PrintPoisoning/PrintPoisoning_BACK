package com.printpoisoning.bookfull.client;

import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printpoisoning.bookfull.dto.response.BookSearchResDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverBookApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    @Value("${naver.api.url.book}")
    private String naverBookApiUrl;

    public BookSearchResDTO searchBooks(String query, int start, int display, String sort) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(naverBookApiUrl)
                .queryParam("query", query)
                .queryParam("start", start)
                .queryParam("display", display)
                .queryParam("sort", sort);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode(StandardCharsets.UTF_8).toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        try {
            NaverBookApiResponse naverResponse = objectMapper.readValue(response.getBody(), NaverBookApiResponse.class);
            return BookSearchResDTO.builder()
                    .total(naverResponse.getTotal())
                    .start(naverResponse.getStart())
                    .display(naverResponse.getDisplay())
                    .hasNext(naverResponse.getTotal() > (naverResponse.getStart() + naverResponse.getDisplay() - 1))
                    .nextStart(naverResponse.getStart() + naverResponse.getDisplay())
                    .items(naverResponse.getItems())
                    .build();
        } catch (Exception e) {
            log.error("네이버 API 응답 처리 오류: {}", e.getMessage(), e);
            throw new RuntimeException("네이버 API 응답을 처리하는 중 오류가 발생했습니다.", e);
        }
    }
}
