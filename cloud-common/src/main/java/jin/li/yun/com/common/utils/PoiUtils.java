package jin.li.yun.com.common.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

/**
 * 读写Microsoft office格式的文档<br>
 * Poi结构：<br>
 * HSSF: 读写 Microsoft Excel XLS <br>
 * XSSF: 读写 Microsoft Excel OOXML XLSX <br>
 * HWPF: 读写 Microsoft Word DOC <br>
 * HSLF: 读写 Microsoft PowerPoint <br>
 *
 * @author WangJiao
 * @since 2021/07/12
 */
public class PoiUtils {
  public static void main(String[] args) throws IOException, InvalidFormatException {
    File file = ResourceUtils.getFile("classpath:date_report.xlsx");
    // 获取工作簿
    XSSFWorkbook workbook = new XSSFWorkbook(file);
    // 获取工作表
    XSSFSheet shape = workbook.getSheetAt(0);
    // 获取行
    for (Row row : shape) {
      // 获取单元格
      for (Cell cell : row) {
        // 获取单元格中的内容
        String cellValue = cell.getStringCellValue();
        System.out.print(cellValue +" ");
      }
      System.out.println();
    }
  }
}
