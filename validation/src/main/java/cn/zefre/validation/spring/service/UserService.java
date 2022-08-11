package cn.zefre.validation.spring.service;

import cn.zefre.validation.spring.dto.UserDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author pujian
 * @date 2021/10/19 15:36
 */
@Validated
public interface UserService {

    /**
     * 根据id更新用户名，返回旧用户名
     *
     * @param id 用户id
     * @param newName 新用户名
     * @author pujian
     * @date 2021/10/19 17:12
     * @return 旧用户名
     */
    @NotBlank(message = "数据异常")
    String updateNameById(@NotBlank(message = "id不能为空") String id,
                          @NotBlank(message = "用户名不能为空") String newName);


    @Valid
    UserDto getById(@NotBlank(message = "id不能为空") String id);

    void insert(@Valid UserDto userDto);

}
