package com.sunjee.util;

import java.io.Serializable;

import com.sunjee.btms.exception.AppRuntimeException;

/**
 * Hql语句不等于
 * 
 * @author ShenYunjie
 * 
 */
public class HqlNoEquals implements Serializable {

	private static final long serialVersionUID = 5644908871443972516L;

	public final static int NO_EQ = 0;	//不等于 !=
	public final static int MORE = 1;	//大于 >
	public final static int LESS = 2;	//小于 <
	public final static int MORE_EQ = 3;	//大于登陆  >=
	public final static int LESS_EQ = 4;	//大于等于 <=
	public final static int BETWEEN = 5;
	
	
	private Object value;
	private int expression;	//表达式
	private Object start;
	private Object end;

	/**
	 * 默认表示不等于返回  ' != '
	 * @param value
	 */
	public HqlNoEquals(Object value) {
		this.value = value;
		this.expression = NO_EQ;
	}
	
	/**
	 * 
	 * @param value
	 * @param expression 0:' != '; 
	 * 	1:' > ';
	 * 	2:' < ';
	 * 	3:' >= ';
	 * 	4' <= '
	 */
	public HqlNoEquals(Object value,int expression) {
		this.value = value;
		this.expression = expression;
	}
	
	public HqlNoEquals(Object start, Object end){
		if(start == null || end == null){
			throw new AppRuntimeException("HQL语句中between语法开始于结束不能为空");
		}
		this.start = start;
		this.end = end;
		this.expression = BETWEEN;
	}

	public int getExpression() {
		return expression;
	}

	public Object getValue() {
		return value;
	}
	
	public Object getStart() {
		return start;
	}

	public Object getEnd() {
		return end;
	}

	public String getSymbol(){
		String str = " != ";
		switch (expression) {
		case MORE:
			str = " > ";
			break;
		case LESS:
			str = " < ";
			break;
		case MORE_EQ:
			str = " >= ";
			break;
		case LESS_EQ:
			str = " <= ";
			break;

		default:
			break;
		}
		return str;
	}
	
	public String getStartKey(String key){
		return "_"+key+"_start";
	}
	
	public String getEndKey(String key){
		return "_"+key+"_end";
	}
}
