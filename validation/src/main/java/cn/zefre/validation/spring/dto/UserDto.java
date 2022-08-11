package cn.zefre.validation.spring.dto;

import cn.zefre.base.validation.constraint.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author pujian
 * @date 2021/10/20 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @Min(value = 18, message = "未成年人禁止使用")
    private int age;

    @Phone
    private String phone;

    @Email
    private String email;
}
