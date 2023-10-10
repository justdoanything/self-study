package prj.yong.modern.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import prj.yong.modern.constants.HttpStatusConstant;
import prj.yong.modern.model.spring.CommonResponseVO;

@UtilityClass
public class ResponseUtil {
    @JsonSerialize
    public static class EmptyJsonResponse {}

    public static ResponseEntity<CommonResponseVO> createSuccessResponse() {
        return createSuccessCommonResponseVO(HttpStatusConstant.SUCCESS, null, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(Object data) {
        return createSuccessResponse(HttpStatusConstant.SUCCESS, data);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(String status, Object data) {
        return createSuccessResponse(status, data, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(
            String status, Object data, HttpStatus httpStatus) {
        return createSuccessCommonResponseVO(status, data, httpStatus);
    }

    private static ResponseEntity<CommonResponseVO> createSuccessCommonResponseVO(
            String status, Object data, HttpStatus httpStatus) {
        if (data == null) {
            data = new EmptyJsonResponse();
        }
        return new ResponseEntity<>(
                CommonResponseVO.builder().status(status).data(data).build(), httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse() {
        return createFailResponse(HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(HttpStatus httpStatus) {
        return createFailResponse(HttpStatusConstant.FAIL, httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String status) {
        return createFailResponse(status, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String status, HttpStatus httpStatus) {
        return new ResponseEntity<>(createFailCommonResponseVO(status), httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(
            String status, HttpHeaders httpHeaders, HttpStatus httpStatus) {
        return new ResponseEntity<>(createFailCommonResponseVO(status), httpHeaders, httpStatus);
    }

    private static CommonResponseVO createFailCommonResponseVO(String status) {
        return CommonResponseVO.builder().status(status).build();
    }
}
