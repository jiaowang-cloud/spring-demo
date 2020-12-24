package jin.li.yun.com.common.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jin.li.yun.com.common.service.BaseService;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
    implements BaseService<T> {}
