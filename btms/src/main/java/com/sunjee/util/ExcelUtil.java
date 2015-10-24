package com.sunjee.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sunjee.btms.exception.AppIOException;
import com.sunjee.component.bean.BaseBean;

public class ExcelUtil extends BaseBean {

	private static final long serialVersionUID = 513138871084698662L;

	private String dateFormat = null;

	/**
	 * 不格式化时间
	 */
	public ExcelUtil() {
	}

	/**
	 * 格式化时间
	 * 
	 * @param dateFormat
	 */
	public ExcelUtil(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 生成Excel临时文件
	 * 
	 * @param context
	 *            上下文
	 * @param templateName
	 *            模板名称（路径）
	 * @param startRowIndex
	 *            从第几行开始写入
	 * @param fields
	 *            需要读取的数据集合的属性，属性必须对应setter,getter方法
	 * @param data
	 *            数据源（数据集合）
	 * @return
	 * @throws AppIOException
	 * @throws IOException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public InputStream createExcel(ServletContext context, String templateName,
			int startRowIndex, String[] fields, List<? extends BaseBean> data)
			throws AppIOException {
		HSSFWorkbook workBook = null;
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {
			workBook = new HSSFWorkbook(getResourceAsStream(context, templateName)); // 获取模板文件
			Sheet sheet = workBook.getSheetAt(0);
			if(data != null && data.size() > 0){
				int currentRowIndex = startRowIndex;
				Row templateRow = sheet.getRow(startRowIndex); // 模板行，以后新增行都已此为模板
				fillValuesOfRow(templateRow, fields, data.get(0), templateRow);
				if (data.size() > 1) {
					for (int i = 1; i < data.size(); i++) {
						currentRowIndex++;
						Row row = sheet.createRow(currentRowIndex);
						fillValuesOfRow(row, fields, data.get(i), templateRow);
					}
				}
			}
			baos = new ByteArrayOutputStream();
			workBook.write(baos);
			baos.flush();
			byte[] buf = baos.toByteArray();
			bais = new ByteArrayInputStream(buf);
			return bais;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppIOException("生成Excel文件出错", e);
		} finally {
			try {
				if (bais != null)
					bais.close();
			} catch (Exception ex) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (Exception ex) {
			}
			try {
				if (workBook != null)
					workBook.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 填充一行的数据
	 * 
	 * @param row
	 *            需要填充的行
	 * @param fields
	 *            数据源的属性
	 * @param data
	 *            数据源
	 * @param templateRow
	 *            模板行
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void fillValuesOfRow(Row row, String[] fields, Object data,
			Row templateRow) throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		for (int i = 0; i < fields.length; i++) {
			Cell cell = row.getCell(i);

			if (cell == null) {
				cell = row.createCell(i);
				// 如果时新建列则将样式设置成模板样式
				if (templateRow != null) {
					cell.setCellStyle(templateRow.getCell(i).getCellStyle());
				}
			}
			setCellValue(cell, fields[i], data);
		}
	}

	/**
	 * 将对象的属性名称为'fieldName'的值填充到cell中
	 * 
	 * @param cell
	 * @param fieldName
	 * @param data
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void setCellValue(Cell cell, String fieldName, Object data)
			throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class<?> clzz = data.getClass();
		Field field = clzz.getDeclaredField(fieldName);
		Method getter = getFieldGetter(field, clzz);
		Object value = getter.invoke(data, null);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		} else if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = (Date) value;
			cell.setCellValue(sdf.format(date));
		} else if (value instanceof Boolean) {
			cell.setCellValue((boolean) value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
		} else if (value instanceof RichTextString) { // 富文本暂时未实现

		}
	}

	/**
	 * 返回属性的getter方法
	 * 
	 * @param field
	 *            属性
	 * @param clzz
	 *            类
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getFieldGetter(Field field, Class<?> clzz)
			throws NoSuchMethodException, SecurityException {
		String fieldName = field.getName();
		String methodName;
		Method method = null;
		Object obj = field.getType();
		if (obj instanceof Boolean) {
			methodName = "is" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			try {
				method = clzz.getMethod(methodName, null);
			} catch (NoSuchMethodException e) {
				methodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				method = clzz.getMethod(methodName, null);
				e.printStackTrace();
			}
		} else {
			methodName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			method = clzz.getMethod(methodName, null);
		}
		return method;
	}

	/**
	 * 返回属性的setter方法
	 * 
	 * @param field
	 * @param clzz
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getFieldSetter(Field field, Class<?> clzz)
			throws NoSuchMethodException, SecurityException {
		String fieldName = field.getName();
		String methodName;
		Method method = null;
		methodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		method = clzz.getMethod(methodName, null);
		return method;
	}

	private InputStream getResourceAsStream(ServletContext context,
			String templateName) {
		return context.getResourceAsStream(templateName);
	}

	private String getContextRealPath(ServletContext context, String path) {
		return context.getRealPath(path);
	}
}
