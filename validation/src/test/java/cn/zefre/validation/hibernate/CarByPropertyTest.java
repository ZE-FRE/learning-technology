package cn.zefre.validation.hibernate;

import cn.zefre.validation.hibernate.entity.CarByProperty;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author pujian
 * @date 2021/10/15 10:06
 */
public class CarByPropertyTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidation() {
        CarByProperty carByProperty = new CarByProperty(null);
        Set<ConstraintViolation<CarByProperty>> violations = validator.validate(carByProperty);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("不能为null", violations.iterator().next().getMessage());
    }
}
