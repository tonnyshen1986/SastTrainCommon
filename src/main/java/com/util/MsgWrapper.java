package com.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MsgWrapper<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_CODE = "errorCode";
	private static final String ERROR_MSG = "errorMsg";
	
	public MsgWrapper(List<K> keys, List<V> values){
		super();
		if ( keys == null || keys.size() == 0 ){
			keys = (List<K>) Arrays.asList(ERROR_CODE, ERROR_MSG);
		}else{
			Object[] arr = keys.toArray();
			int len = 2;
			Object[] arrKeys = new Object[len + keys.size()];
			arrKeys[0] = ERROR_CODE;
			arrKeys[1] = ERROR_MSG;
			for ( int i = 0; i < arr.length; i++ ){
				arrKeys[ len + i ] = arr[i];
			}
			keys = (List<K>) Arrays.asList(arrKeys);
		}
		if ( keys == null || values == null || keys.size() != values.size() ){
			throw new IllegalArgumentException("arguments error");
		}
		for ( int i = 0; i < keys.size(); i ++ ){
			this.put(keys.get(i), values.get(i)); 
		} 
	}
	
	public static void main(String[] args) {
		MsgWrapper<? extends Object, ? extends Object> msgWrapper = new MsgWrapper<>(null, Arrays.asList(-1, "error"));
		System.out.println(msgWrapper);
		
		msgWrapper = new MsgWrapper<>(Arrays.asList("returnData"), Arrays.asList(0, "success", 1));
		System.out.println(msgWrapper);
		
		MsgWrapper<? extends Object, ? extends Object> msgWrapper2 = new MsgWrapper<>(Arrays.asList("a", 4), Arrays.asList("d", 1, "a", new Integer(5)));
		System.out.println(msgWrapper2);
		
	}

}
