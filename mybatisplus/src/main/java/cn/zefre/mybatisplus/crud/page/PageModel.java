package cn.zefre.mybatisplus.crud.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author pujian
 * @date 2022/8/9 16:49
 */
@Data
public class PageModel<T> {

    public static final int DEFAULT_PAGE_NO = 0;

    public static final int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(value = "分页编号")
    private int pageNo = DEFAULT_PAGE_NO;

    @ApiModelProperty(value = "分页大小")
    private int pageSize = DEFAULT_PAGE_SIZE;

    @ApiModelProperty(value = "分页数据")
    private List<T> records;

    @ApiModelProperty(value = "总数")
    private int total;

}
