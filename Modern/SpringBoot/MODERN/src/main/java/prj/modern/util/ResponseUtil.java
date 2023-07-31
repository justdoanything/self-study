package prj.modern.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import prj.modern.constant.StatusCodeMessageConstant;
import prj.modern.model.common.CommonResponseVO;

@UtilityClass
public class ResponseUtil {

    public static ResponseEntity<CommonResponseVO> createSuccessResponse() {
        return createSuccessResponse(null);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(Object responseData) {
        return createSuccessResponse(responseData, StatusCodeMessageConstant.SUCCESS);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(Object responseData, String statusCodeMessage) {
        return createSuccessResponse(responseData, statusCodeMessage, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(Object responseData, String statusCodeMessage, HttpStatus httpStatus) {
        return createSuccessResponseData(responseData, statusCodeMessage, httpStatus);
    }

    private static ResponseEntity<CommonResponseVO> createSuccessResponseData(Object responseData, String statusCodeMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                CommonResponseVO.builder()
                        .message(statusCodeMessage)
                        .data(responseData)
                        .build(),
                httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse() {
        return createFailResponse(StatusCodeMessageConstant.FAIL);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String statusCodeMessage) {
        return createFailResponse(statusCodeMessage, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String statusCodeMessage, HttpStatus httpStatus) {
        return createFailResponseData(statusCodeMessage, httpStatus);
    }

    private static ResponseEntity<CommonResponseVO> createFailResponseData(String statusCodeMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                CommonResponseVO.builder()
                        .message(statusCodeMessage)
                        .build(),
                httpStatus);
    }
}
