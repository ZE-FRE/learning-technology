package cn.zefre.validation.hibernate.custom;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 自定义注解测试
 *
 * @author pujian
 * @date 2021/10/19 11:14
 */
public class CheckCaseTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUpper() {
        Computer hpComputer = new Computer("hp");
        Set<ConstraintViolation<Computer>> violations = validator.validate(hpComputer);
        Assert.assertEquals("厂商必须大写", violations.iterator().next().getMessage());
    }
}
