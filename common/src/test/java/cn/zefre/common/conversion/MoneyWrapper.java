package cn.zefre.common.conversion;

import cn.zefre.common.conversion.annotation.IntegerCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pujian
 * @date 2023/1/30 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyWrapper {

    @IntegerCurrency
    Integer money;

}
