package cn.zefre.validation.hibernate.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义约束validator
 *
 * @author pujian
 * @date 2021/10/19 10:39
 */
public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    /**
     * 验证方法
     *
     * @param objectString 待验证的字符串
     * @param constraintValidatorContext context信息
     * @return 验证通过返回true，否则返回false
     */
    @Override
    public boolean isValid(String objectString, ConstraintValidatorContext constraintValidatorContext) {
        // Bean验证规范建议将空值视为有效。如果null不是元素的有效值，则应使用@NotNull显式注释
        if (objectString == null) {
            return true;
        }
        if (caseMode == CaseMode.UPPER) {
            return objectString.equals(objectString.toUpperCase());
        }
        else {
            return objectString.equals(objectString.toLowerCase());
        }
    }
}
