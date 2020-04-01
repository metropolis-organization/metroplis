package com.metropolis.sso.auth.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author Pop
 * @date 2020/3/11 19:53
 *
 * 和 importSeletor类似，使用方法也类似
 */
public class ShiroDefintionRegistater  implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //前面和importSeletor一样，也可以判断
        Class clazz = TestDemo.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(clazz);
        //之类需要包装一下，然后注入
        String beanName = StringUtils.uncapitalize(clazz.getSimpleName());//获取首字母小写的方法
        beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinition);
    }
}
