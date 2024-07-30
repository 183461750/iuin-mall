package com.iuin.commodity.repostory.dao.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author fa
 */
@Slf4j
public abstract class CommonProxyServiceImpl<S, M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    protected S baseService;

    @SuppressWarnings("unchecked")
    protected CommonProxyServiceImpl() {
        baseService = (S) AopContext.currentProxy();
    }

    public Optional<T> getFirst(QueryWrapper<T> wrapper) {
        wrapper.last("LIMIT 1");
        return Optional.ofNullable(baseMapper.selectOne(wrapper));
    }

    public Optional<T> getFirst(LambdaQueryWrapper<T> wrapper) {
        wrapper.last("LIMIT 1");
        return Optional.ofNullable(baseMapper.selectOne(wrapper));
    }

    public <V> Optional<V> getFirst(LambdaQueryWrapper<T> wrapper, Function<? super Object, V> mapper) {
        wrapper.last("LIMIT 1");
        return Optional.ofNullable(baseMapper.selectOne(wrapper)).map(mapper);
    }

}
