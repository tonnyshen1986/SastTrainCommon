package com.util;

import java.util.Date;

import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonDateFilter implements ValueFilter{
	
	
	@Override
	public Object process(Object source, String name, Object value) {
			
		try {
			if ( value!=null&&value.getClass() == Class.forName("java.util.Date") ) {
				return TimeUtil.formatDate((Date)value);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return value;
	}

}
