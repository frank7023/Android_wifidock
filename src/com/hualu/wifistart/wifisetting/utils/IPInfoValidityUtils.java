package com.hualu.wifistart.wifisetting.utils;

public final class IPInfoValidityUtils {
	public final static boolean IPInfoValidity(String ipinfo,int id){
		String info = ipinfo;
		int countalldot=0;
		int len = info.length();
		char dot = '.'; 
		String temp[]={"","","",""};
		/*�������������Ƿ�Ϊ��*/
		if(0==len&&id==4){
			return true;
		}
		/*�ַ����Ƿ���ϳ���7-15*/
		if(6<len&&len<16){
			/*����.���Ÿ���*/
			for(int i=0;i<len;i++){
				if(dot == info.charAt(i)){
					/*�Ƿ���βΪ��*/
					if(i==0||i==len-1){
						return false;
					}
					countalldot++;
					/*�Ƿ���������.����*/
					if(len-1!=i){
						if(info.charAt(i)==info.charAt(i+1)){
							return false;
						}
					}
				}
			}
			if(3==countalldot){
				/*��.����Ϊ��־����ַ���*/
				temp=info.split("\\.");
				for(int i=0;i<4;i++){
					int size = temp[i].length();
					/*�Ƿ�ȫΪ����*/
					for(int j=0;j<size;j++){
						if('0'>temp[i].charAt(j)||'9'<temp[i].charAt(j)){
							return false;
						}
					}
					if(size >1){
						/*���ַ���Ϊ0*/
						if('0'==temp[i].charAt(0)){
							return false;
						}
					}
					/*�Ƿ�Ϊ0-255*/
					if(0> Integer.parseInt(temp[i])||255<Integer.parseInt(temp[i])){
						return false;
					}
					/*�����������ַ�����Ϊ255*/
					if(1==id&&255!=Integer.parseInt(temp[0])){
						return false;
					}
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		return true;
	}
}
