package cn.zefre.rabbitmq.idempotence.service;

import cn.zefre.mybatisplus.crud.Expression;
import cn.zefre.mybatisplus.crud.ExpressionUtil;
import cn.zefre.mybatisplus.crud.SqlOperator;
import cn.zefre.mybatisplus.crud.mapper.GenericMapper;
import cn.zefre.mybatisplus.crud.sql.InsertSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.SelectSqlBuilder;
import cn.zefre.mybatisplus.crud.where.AtomicWhere;
import cn.zefre.mybatisplus.crud.where.Where;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 基于Mysql提供幂等性服务
 *
 * @author pujian
 * @date 2022/9/17 19:03
 */
public class MysqlIdempotenceService implements IdempotenceService {

    @Resource
    private GenericMapper genericMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean exists(String uniqueId) {
        Where where = new AtomicWhere(null, new Expression("message_id", SqlOperator.EQ, uniqueId));
        String sql = new SelectSqlBuilder("t_idempotence", Collections.singletonList("count(message_id) AS count"), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        Map<String, Object> result = genericMapper.selectOne(sql, whereMap);
        Object count = result.get("count");
        return (Long) count != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void persist(String uniqueId) {
        List<Map<String, Object>> values = new ArrayList<>();
        Map<String, Object> value = new HashMap<>();
        value.put("message_id", uniqueId);
        values.add(value);
        String sql = new InsertSqlBuilder("t_idempotence", Collections.singletonList("message_id"), values.size()).build();
        genericMapper.insert(sql, values);
    }

}
