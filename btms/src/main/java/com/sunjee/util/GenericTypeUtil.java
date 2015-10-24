package com.sunjee.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeUtil {

	/**
	 * 获取泛型参数的类型
	 * @param clazz	泛型
	 * @return
	 */
	public static Class<?> getGenerParamType(Class<?> clazz){
		 Type genericType = clazz.getGenericSuperclass();
		 if(!(genericType instanceof ParameterizedType)){
			 return Object.class;
		 }
		 Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
		 return (Class<?>) params[0];
	}
}
