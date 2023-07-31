package prj.modern.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import prj.modern.constant.StatusCodeMessageConstant;
import prj.modern.model.common.CommonResponseVO;
import prj.modern.util.ResponseUtil;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseVO> customExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseUtil.createFailResponse(StatusCodeMessageConstant.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CommonResponseVO> applicationExceptionHandler(ApplicationException applicationException){
        log.error(applicationException.getMessage(), applicationException);
        return ResponseUtil.createFailResponse(applicationException.getStatusCodeMessage());
    }

    @ExceptionHandler({
            ConstraintViolationException.class
            , MethodArgumentTypeMismatchException.class
            , HttpMessageNotReadableException.class
    })
    public ResponseEntity<CommonResponseVO> invalidParameterValueExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseUtil.createFailResponse(StatusCodeMessageConstant.PARAMETER_VALUE_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class
            , MethodArgumentNotValidException.class
            , BindException.class
    })
    public ResponseEntity<CommonResponseVO> invalidMandatoryParameterExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseUtil.createFailResponse(StatusCodeMessageConstant.MANDATORY_PARAMETER_ERROR, HttpStatus.BAD_REQUEST);
    }
}
