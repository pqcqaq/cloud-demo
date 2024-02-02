package online.zust.services.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.reflect.GenericTypeUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import online.zust.services.annotation.DeepSearch;
import online.zust.services.utils.ProxyUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;


/**
 * @author qcqcqc
 */
public class EnhanceService<M extends BaseMapper<T>, T> implements IService<T> {

    protected final Log log = LogFactory.getLog(getClass());

    protected final Class<?>[] typeArguments = GenericTypeUtils.resolveTypeArguments(getClass(), ServiceImpl.class);

    protected final Class<T> entityClass = currentModelClass();

    protected Class<T> currentModelClass() {
        return (Class<T>) this.typeArguments[1];
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }


    private volatile SqlSessionFactory sqlSessionFactory;

    protected SqlSessionFactory getSqlSessionFactory() {
        if (this.sqlSessionFactory == null) {
            synchronized (this) {
                if (this.sqlSessionFactory == null) {
                    Object target = this.baseMapper;
                    // 这个检查目前看着来说基本上可以不用判断Aop是不是存在了.
                    if (com.baomidou.mybatisplus.extension.toolkit.AopUtils.isLoadSpringAop()) {
                        if (AopUtils.isAopProxy(this.baseMapper)) {
                            target = AopProxyUtils.getSingletonTarget(this.baseMapper);
                        }
                    }
                    if (target != null) {
                        MybatisMapperProxy mybatisMapperProxy = (MybatisMapperProxy) Proxy.getInvocationHandler(target);
                        SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate) mybatisMapperProxy.getSqlSession();
                        this.sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
                    } else {
                        this.sqlSessionFactory = GlobalConfigUtils.currentSessionFactory(this.entityClass);
                    }
                }
            }
        }
        return this.sqlSessionFactory;
    }

    @Autowired
    protected M baseMapper;

    protected final Class<M> mapperClass = currentMapperClass();

    protected Class<M> currentMapperClass() {
        return (Class<M>) this.typeArguments[0];
    }

    @Override
    public M getBaseMapper() {
        return this.baseMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    protected String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(getSqlSessionFactory(), this.log, list, batchSize, consumer);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = tableInfo.getPropertyValue(entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return SqlHelper.saveOrUpdateBatch(getSqlSessionFactory(), this.mapperClass, this.log, entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = tableInfo.getPropertyValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal)
                   || CollectionUtils.isEmpty(sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(getSqlStatement(SqlMethod.UPDATE_BY_ID), param);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    @Override
    public T getById(Serializable id) {
        T byId = baseMapper.selectById(id);
        byId = getDeepSearch(byId);
        return byId;
    }

    public T getDeepSearch(T entity) {
        Class<?> aClass = entity.getClass();
        List<Field> deepSearchFields = getDeepSearchFields(aClass);
        // 获取对象字段的值
        for (Field deepSearchField : deepSearchFields) {
            deepSearchField.setAccessible(true);
            try {
                // 获取注解中的字段
                String field = deepSearchField.getAnnotation(DeepSearch.class).field();
                // 获取注解中的service
                Class<? extends EnhanceService> service = deepSearchField.getAnnotation(DeepSearch.class).service();
                // 获取service的实例
                EnhanceService bean = ProxyUtil.getBean(service);
                // 名为field的字段的值
                Field declaredField = aClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                Object value = declaredField.get(entity);
                if (value instanceof Long l) {
                    // 获取service的getById方法
                    Object byId1 = bean.getById(l);
                    deepSearchField.set(entity, byId1);
                }
            } catch (Exception e) {
                log.error("获取字段值失败", e);
                // 暂时不需要抛出异常，直接返回null
                // throw new ErrorDeepSearchException("获取字段值失败:" + e.getMessage());
            }
        }
        return entity;
    }

    private List<Field> getDeepSearchFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> deepSearchFields = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(DeepSearch.class)) {
                deepSearchFields.add(declaredField);
            }
        }
        return deepSearchFields;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return baseMapper.selectOne(queryWrapper, throwEx);
    }

    @Override
    public Optional<T> getOneOpt(Wrapper<T> queryWrapper, boolean throwEx) {
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper, throwEx));
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
    }

    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

}
