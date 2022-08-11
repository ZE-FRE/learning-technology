package cn.zefre.validation.spring.controller;

import cn.zefre.base.web.response.UniformResponse;

import cn.zefre.validation.spring.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author pujian
 * @date 2021/10/19 15:37
 */
@Slf4j
@RestController("myUserController")
@RequestMapping("/user")
@Validated
public class UserController {

    @GetMapping(value = "/{id}")
    public UserDto getById(@PathVariable String id) {
        return new UserDto(id, "张三", 12, null, null);
    }

    @PostMapping(value = "updateNameById")
    public UniformResponse updateNameById(@NotBlank(message = "id不能为空") String id,
                                   @NotBlank(message = "用户名不能为空") String name) {
        log.info("id:{},name:{}", id, name);
        return UniformResponse.ok();
    }

    @PostMapping(value = "updateByForm")
    public UniformResponse updateByForm(UserDto userDto) {
        log.info("userDto:{}", userDto);
        return UniformResponse.ok();
    }

    @PostMapping(value = "updateByBody", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UniformResponse updateByBody(@Valid @RequestBody UserDto userDto) {
        log.info("userDto:{}", userDto);
        return UniformResponse.ok();
    }

}
