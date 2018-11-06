package org.wltea.solrj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrjBeanDemo {
	//指定solr服务器的地址  
	private String serverUrl = "http://127.0.0.1:8080/solr/";
		
	public void add(){
		HttpSolrClient client = new  HttpSolrClient(serverUrl+ "mycore");
		List<User> beans = new ArrayList<User>();
		User bo = new User();
		bo.setId("13");
		bo.setName("你好");
		bo.setAddress("test bean.");
		beans.add(bo);
		try {
			client.addBeans(beans);
			client.commit();
			System.out.println("----索引创建完毕!!!----");
			
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void queryAll() throws SolrServerException, IOException{
		
        HttpSolrClient solrServer = new HttpSolrClient(serverUrl+"mycore/");  
        //创建Query查询
        SolrQuery query = new SolrQuery();  
        query.set("q","你好");
        //参数df,给query设置默认搜索域  
        query.set("df", "name");  
	    query.setStart(0);
        query.setRows(2);
        //执行查询
        QueryResponse response = solrServer.query(query);  
        //获取SolrDocumentList对象
        SolrDocumentList results = response.getResults();

        //总记录数
        long total = results.getNumFound();
        System.out.println("total = " + total);

        //结果集
        for (SolrDocument result : results) {
            User bean = Doc2BeanUtil.getBean(User.class, result);
            System.out.println(bean.getName());
        }
        solrServer.close();
	}
	
	public static void main(String[] args) throws SolrServerException, IOException {
		SolrjBeanDemo s2 = new SolrjBeanDemo();
		s2.add();
		s2.queryAll();
	}
}
