package cn.zefre.mybatisplus.crud.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;

/**
 * @author pujian
 * @date 2022/8/9 16:55
 */
@UtilityClass
public class PageUtil {

    public static <T> Page<T> getPage(PageModel<T> pageModel) {
        int pageNo = PageModel.DEFAULT_PAGE_NO;
        int pageSize = PageModel.DEFAULT_PAGE_SIZE;
        if (pageModel != null) {
            pageNo = pageModel.getPageNo();
            pageSize = pageModel.getPageSize();
        }
        return new Page<>(pageNo, pageSize);
    }

    public static <T> PageModel<T> getPageModel(IPage<T> page) {
        PageModel<T> pageModel = new PageModel<>();
        if (page != null) {
            pageModel.setPageNo((int) page.getCurrent());
            pageModel.setPageSize((int) page.getSize());
            pageModel.setRecords(page.getRecords());
            pageModel.setTotal((int) page.getTotal());
        }
        return pageModel;
    }

}
