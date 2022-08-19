package cn.zefre.validation.spring.service;

import cn.zefre.validation.BootApplication;
import cn.zefre.validation.spring.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author pujian
 * @date 2021/10/19 15:38
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private Validator validator;

    @Resource
    private Validator validator2;

    @Test
    public void testValidate() {

        System.out.println(validator.getClass());
        System.out.println(validator2.getClass());

        UserDto userDto = new UserDto();
        Set<ConstraintViolation<UserDto>> violations = validator2.validate(userDto);
        for (ConstraintViolation<UserDto> violation : violations) {
            log.info(violation.getMessage());
        }

    }

    @Test
    public void testUpdateNameById() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userService.updateNameById("", null));
        ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
        assertEquals("id不能为空", violation.getMessage());
    }

    @Test
    public void testGetById() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userService.getById("1"));
        ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
        assertEquals("id不能为空", violation.getMessage());
    }

    @Test
    public void testInsert() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userService.insert(new UserDto()));
        ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
        assertEquals("id不能为空", violation.getMessage());
    }

}
