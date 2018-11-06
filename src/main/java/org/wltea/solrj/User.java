package org.wltea.solrj;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @Field注解的使用 作用1：指定Bean的一个字段为
 * Field,schema.xml配置必需有这个field，不然会报错。
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Field
	@SolrMapping(value = "id")
	private String id;
	@Field
	@SolrMapping(value = "name")
	private String name;
	@Field
	@SolrMapping(value = "address")
	private String address;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
