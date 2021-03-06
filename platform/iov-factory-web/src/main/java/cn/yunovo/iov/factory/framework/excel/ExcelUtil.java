package cn.yunovo.iov.factory.framework.excel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("all")
public class ExcelUtil {
	private static ExcelUtil eu = new ExcelUtil();

	private ExcelUtil() {
	}

	public static ExcelUtil getInstance() {
		return eu;
	}

	/**
	 * 处理对象转换为Excel
	 * 
	 * @param template
	 * @param objs
	 * @param clz
	 * @param isClasspath
	 * @return
	 */
	private ExcelTemplate handlerObj2Excel(FileInputStream inputStream, List objs, Class clz, boolean isClasspath) {
		ExcelTemplate et = ExcelTemplate.getInstance();
		try {
			if (isClasspath) {
				et.readTemplateByClasspath(inputStream);
			} else {
				et.readTemplateByPath(inputStream);
			}
			List<ExcelHeader> headers = getHeaderListForField(clz);
			if (0 == headers.size()) {
				headers = getHeaderListForMethod(clz);
			}
			Collections.sort(headers);
			// 输出标题
			et.createNewRow();
			for (ExcelHeader eh : headers) {
				et.createCell(eh.getTitle());
			}
			// 输出值
			for (Object obj : objs) {
				et.createNewRow();
				for (ExcelHeader eh : headers) {
					// Method m = clz.getDeclaredMethod(mn);
					// Object rel = m.invoke(obj);
					et.createCell(BeanUtils.getProperty(obj, getMethodName(eh)));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return et;
	}

	/**
	 * 根据标题获取相应的方法名称
	 * 
	 * @param eh
	 * @return
	 */
	private String getMethodName(ExcelHeader eh) {
		String mn = eh.getMethodName().substring(3);
		mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
		return mn;
	}

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流
	 * 
	 * @param datas       模板中的替换的常量数据
	 * @param template    模板路径
	 * @param os          输出流
	 * @param objs        对象列表
	 * @param clz         对象的类型
	 * @param isClasspath 模板是否在classPath路径下
	 */
	public void exportObj2ExcelByTemplate(Map<String, String> datas, FileInputStream inputStream, OutputStream os, List objs, Class clz, boolean isClasspath) {
		try {
			ExcelTemplate et = handlerObj2Excel(inputStream, objs, clz, isClasspath);
			et.replaceFinalData(datas);
			et.wirteToStream(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中
	 * 
	 * @param datas       模板中的替换的常量数据
	 * @param template    模板路径
	 * @param outPath     输出路径
	 * @param objs        对象列表
	 * @param clz         对象的类型
	 * @param isClasspath 模板是否在classPath路径下
	 */
	public void exportObj2ExcelByTemplate(Map<String, String> datas, FileInputStream inputStream, String outPath, List objs, Class clz, boolean isClasspath) {
		ExcelTemplate et = handlerObj2Excel(inputStream, objs, clz, isClasspath);
		et.replaceFinalData(datas);
		et.writeToFile(outPath);
	}

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流,基于Properties作为常量数据
	 * 
	 * @param prop        基于Properties的常量数据模型
	 * @param template    模板路径
	 * @param os          输出流
	 * @param objs        对象列表
	 * @param clz         对象的类型
	 * @param isClasspath 模板是否在classPath路径下
	 */
	public void exportObj2ExcelByTemplate(Properties prop, FileInputStream inputStream, OutputStream os, List objs, Class clz, boolean isClasspath) {
		ExcelTemplate et = handlerObj2Excel(inputStream, objs, clz, isClasspath);
		et.replaceFinalData(prop);
		et.wirteToStream(os);
	}

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中,基于Properties作为常量数据
	 * 
	 * @param prop        基于Properties的常量数据模型
	 * @param template    模板路径
	 * @param outPath     输出路径
	 * @param objs        对象列表
	 * @param clz         对象的类型
	 * @param isClasspath 模板是否在classPath路径下
	 */
	public void exportObj2ExcelByTemplate(Properties prop, FileInputStream inputStream, String outPath, List objs, Class clz, boolean isClasspath) {
		ExcelTemplate et = handlerObj2Excel(inputStream, objs, clz, isClasspath);
		et.replaceFinalData(prop);
		et.writeToFile(outPath);
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
	 * 
	 * @param outPath 导出路径
	 * @param objs    对象列表
	 * @param clz     对象类型
	 */
	public void exportObj2Excel(String outPath, List objs, Class clz) {
		Workbook wb = handleObj2Excel(objs, clz);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outPath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流
	 * 
	 * @param os   输出流
	 * @param objs 对象列表
	 * @param clz  对象类型
	 */
	public void exportObj2Excel(OutputStream os, List objs, Class clz) {
		try {
			Workbook wb = handleObj2Excel(objs, clz);
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从类路径读取相应的Excel文件到对象列表
	 * 
	 * @param path     类路径下的path
	 * @param clz      对象类型
	 * @param readLine 开始行，注意是标题所在行
	 * @param tailLine 底部有多少行，在读入对象时，会减去这些行
	 * @return
	 */
	public List<Object> readExcel2ObjsByClasspath(FileInputStream inputStream, Class clz, int readLine, int tailLine) {
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
			return handlerExcel2Objs(wb, clz, readLine, tailLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表
	 * 
	 * @param path     文件路径下的path
	 * @param clz      对象类型
	 * @param readLine 开始行，注意是标题所在行
	 * @param tailLine 底部有多少行，在读入对象时，会减去这些行
	 * @return
	 */
	public List<Object> readExcel2ObjsByPath(FileInputStream inputStream, Class clz, int readLine, int tailLine) {
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
			return handlerExcel2Objs(wb, clz, readLine, tailLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从类路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
	 * 
	 * @param path 路径
	 * @param clz  类型
	 * @return 对象列表
	 */
	public List<Object> readExcel2ObjsByClasspath(FileInputStream inputStream, Class clz) {
		return this.readExcel2ObjsByClasspath(inputStream, clz, 0, 0);
	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
	 * 
	 * @param path 路径
	 * @param clz  类型
	 * @return 对象列表
	 */
	public List<Object> readExcel2ObjsByPath(FileInputStream inputStream, Class clz) {
		return this.readExcel2ObjsByPath(inputStream, clz, 0, 0);
	}

	/**
	 * add huangzz
	 * 
	 * 从文件路径读取相应的Excel文件到对象列表
	 * 
	 * @param path     文件路径下的path
	 * @param clz      对象类型
	 * @param readLine 开始行，注意是标题所在行
	 * @param tailLine 底部有多少行，在读入对象时，会减去这些行
	 * @return
	 */
	public List<Object> readExcel2Objs(InputStream inputStream, Class clz, int readLine, int tailLine) {
		Workbook wb = null;
		try {
			// wb = new HSSFWorkbook(inputStream); supper xls
			// wb = new XSSFWorkbook(inputStream); //super xlsx
			wb = WorkbookFactory.create(inputStream); // super xls、xlsx
			return handlerExcel2Objs(wb, clz, readLine, tailLine);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getCellValue(Cell c) {
		String o = null;
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			o = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			o = String.valueOf(c.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			o = String.valueOf(c.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			o = String.valueOf(c.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			o = c.getStringCellValue();
			break;
		default:
			o = null;
			break;
		}
		return o;
	}

	private Workbook handleObj2Excel(List objs, Class clz) {
		// Workbook wb = new HSSFWorkbook(); update by huangzz
		Workbook wb = new XSSFWorkbook();
	
		try {
			Sheet sheet = wb.createSheet();
			Row r = sheet.createRow(0);
			List<ExcelHeader> headers = getHeaderListForField(clz);
			if (0 == headers.size()) {
				headers = getHeaderListForMethod(clz);
			}
			Collections.sort(headers);
			// 写标题
			for (int i = 0; i < headers.size(); i++) {
				r.createCell(i).setCellValue(headers.get(i).getTitle());
			}
			// 写数据
			Object obj = null;
			for (int i = 0; i < objs.size(); i++) {
				r = sheet.createRow(i + 1);
				sheet.setColumnWidth(i, headers.get(0).getWidth());
				obj = objs.get(i);
				for (int j = 0; j < headers.size(); j++) {
					r.createCell(j).setCellValue(BeanUtils.getProperty(obj, getMethodName(headers.get(j))));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return wb;
	}

	private List<Object> handlerExcel2Objs(Workbook wb, Class clz, int readLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(0);
		List<Object> objs = null;
		try {
			Row row = sheet.getRow(readLine);
			objs = new ArrayList<Object>();
			Map<Integer, String> maps = getHeaderMap(row, clz);
			if (maps == null || maps.size() <= 0)
				throw new RuntimeException("要读取的Excel的格式不正确，检查是否设定了合适的行");
			for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
				row = sheet.getRow(i);
				Object obj = clz.newInstance();
				for (Cell c : row) {
					int ci = c.getColumnIndex();
					String mn = maps.get(ci).substring(3);
					mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
					BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
				}
				objs.add(obj);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return objs;
	}

	/**
	 * 通过GET方法注解方式：@ExcelResources(title="XXX",order=1,width=3000)
	 * 
	 * @param clz
	 * @return
	 */
	private List<ExcelHeader> getHeaderListForMethod(Class clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		Method[] ms = clz.getDeclaredMethods();
		for (Method m : ms) {
			String mn = m.getName();
			if (mn.startsWith("get")) {
				if (m.isAnnotationPresent(ExcelResources.class)) {
					ExcelResources er = m.getAnnotation(ExcelResources.class);
					headers.add(new ExcelHeader(er.title(), er.order(), mn, er.width()));
				}
			}
		}
		return headers;
	}

	private List<ExcelHeader> getHeaderListForField(Class clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		Field[] fields = clz.getDeclaredFields();
		for (Field f : fields) {
			String fn = f.getName();
			if (f.isAnnotationPresent(ExcelResources.class)) {
				ExcelResources er = f.getAnnotation(ExcelResources.class);
				String cap = fn.substring(0, 1).toUpperCase() + fn.substring(1);
				headers.add(new ExcelHeader(er.title(), er.order(), "get" + cap, er.width()));
			}
		}
		return headers;
	}

	private Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
		List<ExcelHeader> headers = getHeaderListForField(clz);
		if (0 == headers.size()) {
			headers = getHeaderListForMethod(clz);
		}
		Map<Integer, String> maps = new HashMap<Integer, String>();
		for (Cell c : titleRow) {
			String title = c.getStringCellValue();
			for (ExcelHeader eh : headers) {
				if (eh.getTitle().equals(title.trim())) {
					maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
					break;
				}
			}
		}
		return maps;
	}
}