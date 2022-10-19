package modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Reference : https://jsy1110.github.io/2022/enum-class-validation/ @Description JAVA에서 기본적으로 제공하는
 * 원시타입의 경우 @NotBlacn, @NotNull 등 제공되는 annotation으로 validation을 할 수 있다. 개발자가 원하는 필드의 원하는 validation을
 * 걸고 싶다면 따로 정의해줘야 한다.
 *
 * <p>예를들어, private Gender gender; 필드가 있을 때 Gender는 MEN, WOMEN만 가능하다고 해보자. public enum Gender { MEN,
 * WOMEN } 으로 정의하고 @EnumValid annotaion을 적용해서 원하는 validation을 걸어줄 수 있다. @Useage @EnumValid(enumClass
 * = Gender.class) private Gender gender; @EnumValid(enumClass = EnumConstants.StoreType.class)
 * private String storeType;
 */
public class SpringValidator implements ConstraintValidator<EnumValid, String> {
    /**
     * @Description 검증할 필드의 Type을 ConstraintValidator의 파라미터로 넘겨준다. String 타입의 필드를 검증하려면
     * ConstraintValidator<EnumValid, String>가 되어야 하고 enum 타입의 필드를 검증하려면
     * ConstraintValidator<EnumValid, Enum>가 되야 한다.
     */
    private EnumValid annotation;

    /** @param enumValid annotation instance for a given constraint declaration */
    @Override
    public void initialize(EnumValid enumValid) {}

    /**
     * 실제 Validation 에 사용할 코드
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
