package com.test.xml_parse_test;

import java.io.File;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import org.apache.ibatis.*;

public class App 
{
	 
	
   
	 public static void main(String[] args) throws Exception, IOException {  
	      
		  SAXReader saxReader = new SAXReader();

	        Document document = saxReader.read(new File("d:\\Region.xml"));

	        // 获取根元素
	        Element root = document.getRootElement();
	        System.out.println("Root: " + root.getName());
	        
	        
	        List<province> provincelist=new ArrayList<province>();
	        List<city> citylist=new ArrayList<city>();
	        List<county> countylist=new ArrayList<county>();
	  
	     
	         
	                         Iterator itersElIterator = root.elementIterator("province"); // 获取子节点body下的子节点form
	                         // 遍历Header节点下的Response节点
	                         while (itersElIterator.hasNext()) {
	                            
	                        	 
	                        	 
	                             Element itemEle = (Element) itersElIterator.next();
	                             
	                             province p=new province();
	                             
	                             p.setId(itemEle.attributeValue("id"));
	                             p.setIdentity( itemEle.attributeValue("identity"));
	                             p.setName(itemEle.attributeValue("name"));
	                             provincelist.add(p);
	                             
	                             Iterator iterscityIterator = itemEle.elementIterator("city"); // 获取子节点body下的子节点form  
	                             
	                             while (iterscityIterator.hasNext()) {
	                               
	                            	 Element itemcity = (Element) iterscityIterator.next();
	                            	 
	                            	 city c=new city();
		                             
		                             c.setId( itemcity.attributeValue("id"));
		                             c.setIdentity( itemcity.attributeValue("identity"));
		                             c.setName(itemcity.attributeValue("name"));
		                             c.setProvinceid(itemEle.attributeValue("id"));
		                             citylist.add(c);
	                            	 
	                            	 
	                            	 
		                             Iterator iterscountyIterator = itemcity.elementIterator("county"); 
	                            	 
		                             while (iterscountyIterator.hasNext()) {
		                             
		                            	 Element itemcounty = (Element) iterscountyIterator.next();
		                            	 
		                            	 county co=new county();
			                             
		                            	 co.setId(itemcounty.attributeValue("id"));
		                            	 co.setIdentity( itemcounty.attributeValue("identity"));
			                             co.setName(itemcounty.attributeValue("name"));
			                             co.setCityid( itemcity.attributeValue("id"));
			                             countylist.add(co);
		                            	 
		                             }
	                             }
	                           
	                         }
	                         
	                         
	                         
	                   SqlSession sqlSession=null;
	                   try
	                   {
	                	   sqlSession=SqlSessionFactoryUtil.openSqlSession();
	                	   RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);
	                	   roleMapper.deletecounty();
	                	   roleMapper.deletecity();
	                	   roleMapper.deleteprovince();
	                	   sqlSession.commit();
	                	   
	                	   for(province p:provincelist){  
	                		   
	                		   roleMapper.insertProvince(p);
	                		    }
	                	   
	                	   for(city ci: citylist){  
	                		   roleMapper.insertCity(ci);
               		         }
	                	   
	                	   for(county co:countylist){  
	                		   roleMapper.insertCounty(co);
               		       }
	                	   
	                	   sqlSession.commit();
	                   }
	                   catch(Exception ex)
	                   {
	                	   System.err.println(ex.getMessage());
	                	   sqlSession.rollback();
	                   }
	                   finally{
	                	   if (sqlSession!=null)
	                		   sqlSession.close();
	                			   
	                   }  
	                         
	             
	        
	    }  
	 
	 
	 
	
	  
}
