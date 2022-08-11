package cn.zefre.validation.hibernate;

import cn.zefre.validation.hibernate.entity.Person;
import cn.zefre.validation.hibernate.entity.Vehicle;
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
 * @date 2021/10/15 15:53
 */
public class NestedObjectTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 测试引用类型为null时，@Valid嵌套校验会忽略null值
     *
     * @author pujian
     * @date 2021/10/15 15:56
     */
    @Test
    public void testIgnoreNull() {
        Vehicle vehicle = new Vehicle("川A12345", null);
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testNestedValidation() {
        Vehicle vehicle = new Vehicle("川A12345", new Person(null));
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        Assert.assertEquals("不能为null", violations.iterator().next().getMessage());
    }

    /**
     * 测试对象互相引用
     *
     * @author pujian
     * @date 2021/10/15 16:09
     */
    @Test
    public void testCorrelated() {
        Person person = new Person("张三");

        Vehicle vehicle = new Vehicle("川E66666", person);

        person.setVehicle(vehicle);

        // 不会出现死循环
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        Assert.assertEquals(0, violations.size());
    }
}
