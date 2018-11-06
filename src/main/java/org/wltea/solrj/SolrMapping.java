package org.wltea.solrj;

import java.lang.annotation.*;

/**
 * 在字段上使用该注解,并标注对应的索引域,
 *  配合Doc2BeanUtils即可将查询到的Document对象转换成bean
 */
@Documented
@Target(ElementType.FIELD) //作用范围: 字段
@Retention(RetentionPolicy.RUNTIME)  // 作用时间: 运行时
public @interface SolrMapping {
    String value() default ""; //solr索引名
}
