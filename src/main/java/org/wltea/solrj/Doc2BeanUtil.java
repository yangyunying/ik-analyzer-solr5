package org.wltea.solrj;

import java.lang.reflect.Field;

import org.apache.solr.common.SolrDocument;
import org.apache.commons.beanutils.BeanUtils;

public class Doc2BeanUtil {
	public static <T> T getBean(Class<T> beanClass, SolrDocument document) {
        try {
            //获取实例对象
            Object bean = beanClass.newInstance();

            // 反射获得所有字段
            Field[] fields = beanClass.getDeclaredFields();
            // 遍历bean中的字段
            for (Field field : fields) {
                SolrMapping anno = field.getAnnotation(SolrMapping.class);
                if (anno != null) {
                    String filedName = field.getName();
                    //获得注解中标记的对应的索引域
                    String indexName = anno.value();
                    Object value = document.get(indexName);
                    if("".equals(value)){
                        //如果注解不指定solr的索引,默认用字段名来作为索引名
                    	value = filedName;
                    }
                    // 判断字段的类型
                    BeanUtils.setProperty(bean,filedName,value);
                }
            }
            return (T) bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
