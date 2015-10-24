package com.sunjee.util;

import java.io.Serializable;
import java.util.Date;

public class CommonUtil implements Serializable {

	private static final long serialVersionUID = -5130979795741386883L;

	public static String getUniqueCode(){
		String code = String.valueOf(new Date().getTime());
		code += String.valueOf((Math.random()+1)*10000).substring(0,5);
		return code;
	}
}
