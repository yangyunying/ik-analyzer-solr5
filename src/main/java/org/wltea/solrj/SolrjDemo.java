package org.wltea.solrj;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjDemo {
	
	//指定solr服务器的地址  
	private String serverUrl = "http://127.0.0.1:8080/solr/";
    /**
     * 增加与修改<br>
     * 增加与修改其实是一回事，只要id不存在，则增加，如果id存在，则是修改
     * @throws IOException 
     * @throws SolrServerException 
     */
    @Test
    public void upadteIndex() throws SolrServerException, IOException{
       
        //创建
        HttpSolrClient client = new  HttpSolrClient(serverUrl+ "mycore");
        SolrInputDocument doc = new SolrInputDocument();
        
        doc.addField("id", "1");
        doc.addField("name", "周星驰");
        doc.addField("address", "图片地址2");
        
        client.add(doc);
        client.commit();
        
        client.close();
    }
    
    /** 
     * 根据id从索引库删除文档 
     */
    public void deleteDocumentById() throws Exception {  
        //选择具体的某一个solr core
        HttpSolrClient server = new HttpSolrClient(serverUrl+"mycore");  
        //删除文档  
        server.deleteById("zxj1");  
        //删除所有的索引
        //solr.deleteByQuery("*:*");
        //提交修改  
        server.commit();  
        server.close();
    }  

    /**
     * 查询
    * @throws Exception 
     */
    public void querySolr() throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(serverUrl+"mycore/");  
        SolrQuery query = new SolrQuery();  
        //下面设置solr查询参数
        //query.set("q", "*:*");// 参数q  查询所有   
        query.set("q","周星驰");//相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询

        //给query增加布尔过滤条件  
        //query.addFilterQuery("description:演员");  //description字段中含有“演员”两字的数据

        //参数df,给query设置默认搜索域  
        query.set("df", "name");  

        //参数sort,设置返回结果的排序规则  
        query.setSort("id",SolrQuery.ORDER.desc);

        //设置分页参数  
        query.setStart(0);  
        query.setRows(10);//每一页多少值  

        //参数hl,设置高亮  
        query.setHighlight(true);  
        //设置高亮的字段  
        query.addHighlightField("name");  
        //设置高亮的样式  
        query.setHighlightSimplePre("<font color='red'>");  
        query.setHighlightSimplePost("</font>"); 

        //获取查询结果
        QueryResponse response = solrServer.query(query);  
        //两种结果获取：得到文档集合或者实体对象

        //查询得到文档的集合  
        SolrDocumentList solrDocumentList = response.getResults();  
        System.out.println("通过文档集合获取查询的结果"); 
        System.out.println("查询结果的总数量：" + solrDocumentList.getNumFound());  
        //遍历列表  
        for (SolrDocument doc : solrDocumentList) {
            System.out.println("id:"+doc.get("id")+"   name:"+doc.get("name")+"    address:"+doc.get("address"));
        } 

        //得到实体对象
        List<User> tmpLists = response.getBeans(User.class);
        if(tmpLists!=null && tmpLists.size()>0){
            System.out.println("通过文档集合获取查询的结果"); 
            for(User per:tmpLists){
                System.out.println("id:"+per.getId()+"   name:"+per.getName()+"    address:"+per.getAddress());
            }
        }
        
        solrServer.close();
    }


	public static void main(String[] args) throws Exception {
		SolrjDemo s  = new SolrjDemo();
		s.upadteIndex();
		s.querySolr();
		s.deleteDocumentById();
	}

}
