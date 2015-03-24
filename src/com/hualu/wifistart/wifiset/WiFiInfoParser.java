package com.hualu.wifistart.wifiset;

import java.io.InputStream;
import java.util.List;

public interface WiFiInfoParser {
	 /** 
	     * ���������� �õ�WiFiInfo���󼯺� 
	     * @param is 
	     * @return 
	     * @throws Exception 
	     */  
	    public List<WiFiInfo> parse(InputStream is) throws Exception;  
	      
	    /** 
	     * ���л�WiFiInfo���󼯺� �õ�XML��ʽ���ַ��� 
	     * @param wifiInfos 
	     * @return 
	     * @throws Exception 
	     */  
	   //public String serialize(List<WiFiInfo> wifiInfos) throws Exception;  
}
