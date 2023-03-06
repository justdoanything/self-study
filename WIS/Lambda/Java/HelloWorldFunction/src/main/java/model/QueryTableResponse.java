package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryTableResponse {
    private int id;
    private String country;
    private String flightNo;
    private String fromCountry;
    private String fromDate;
    private String grade;
    private String membership;
    private String name;
    private String phoneNumber;
    private String sitNo;
    private String toCountry;
    private String toDate;
    private boolean isReserved;
    private String nickname;
    private String contactName;
}
