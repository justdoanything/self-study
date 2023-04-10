package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScanTableResponse {
    private int id;
    private String country;
    private String flightNo;
    private String fromCountry;
    private String fromDate;
    private String grade;
    private String membership;
    private String name;
    private String localName;
    private String phoneNumber;
    private String sitNo;
    private String toCountry;
    private String toDate;
    private boolean isReserved;
    private String reservedNo;
}
