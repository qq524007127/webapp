package com.sunjee.util;

import org.apache.commons.lang.StringUtils;

/**
 * 拼接hql where条件语句
 * @author ShenYunjie
 *
 */
public class HqlLikeType implements java.io.Serializable{

	private static final long serialVersionUID = 4965898637803172270L;
	
	private LikeType type;
	private String value;
	
	public HqlLikeType(String value, LikeType type){
		this.value = value;
		this.type = type;
		if(StringUtils.isEmpty(value)){
			this.value = "";
		}
		if(type == null){
			this.type = LikeType.allLike;
		}
	}

	public LikeType getType() {
		return type;
	}

	protected void setType(LikeType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	protected void setValue(String value) {
		this.value = value;
	}
}
