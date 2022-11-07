package cn.zefre.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pujian
 * @date 2022/9/24 17:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;

    private String username;

}
