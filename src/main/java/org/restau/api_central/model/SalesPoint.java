package org.restau.api_central.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SalesPoint {
    private Long id;
    private String name;
    private String url;
    private String api_key;

    public SalesPoint(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SalesPoint(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
