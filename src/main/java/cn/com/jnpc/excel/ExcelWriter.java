package cn.com.jnpc.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingPlan;
import cn.com.jnpc.utils.DateUtil;

public final class ExcelWriter {

    private Workbook wb    = null;
    private Sheet    sheet = null;

    /**
     * 导出实际会议
     * 
     * @Title: writeReality
     */
    public void writeReality(List<Meeting> meetingList, OutputStream os, String year, String month) {
        try {
            wb = WorkbookFactory.create(this.getClass().getResourceAsStream("/reality.xls"));
            sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("#date", year + "年" + month + "月"));
            for (int i = 0, len = meetingList.size(); i < len; i++) {
                if (i != len - 1) {
                    insertRow(sheet, i + 2, 1);
                }
                Meeting meeting = meetingList.get(i);
                row = sheet.getRow(i + 2);
                setCellValue(row.getCell(0), i + 1);// 序号
                setCellValue(row.getCell(1), meeting.getContent());// 会议名称
                setCellValue(row.getCell(2), DateUtil.dateToString(meeting.getStarttime(), "yyyy年MM月dd日") + "\r\n"
                        + DateUtil.dateToString(meeting.getEndtime(), "yyyy年MM月dd日"));// 会议时间
                String address = meeting.getAddress();// 外部会议地址
                String address1 = meeting.getAddress1();// 内部会议地点
                setCellValue(row.getCell(3), (address == null || "".equals(address)) ? address1 : address);// 会议地点
                setCellValue(row.getCell(4), meeting.getOrg());// 组织单位/部门
                setCellValue(row.getCell(5), meeting.getLeader());// 公司参会领导
                setCellValue(row.getCell(6), meeting.getFdepart());// 参会上级/外单位及领导
                setCellValue(row.getCell(7), meeting.getCommitdepart());// 公司牵头处室
                setCellValue(row.getCell(8), meeting.getContact() + "/" + meeting.getContactphone());// 会议联系人/联系电话
                setCellValue(row.getCell(9), meeting.getActual_costs());// 会议实际费用（万）

            }
            String fileName = "会议执行情况汇总表（" + month + "月）";
            wb.setSheetName(0, fileName);
            wb.write(os);
            os.flush();
            os.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writerPlan(List<MeetingPlan> meetingPlanList, OutputStream os, Map<String, String> costsMap,
            Map<String, String> planMap, String year, String month) {
        try {
            wb = WorkbookFactory.create(this.getClass().getResourceAsStream("/reality.xls"));
            sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("#date", year + "年" + month + "月"));
            for (int i = 0, len = meetingPlanList.size(); i < len; i++) {
                if (i != len - 1) {
                    insertRow(sheet, i + 2, 1);
                }
                MeetingPlan mp = meetingPlanList.get(i);
                row = sheet.getRow(i + 2);
                setCellValue(row.getCell(0), i + 1);// 序号
                setCellValue(row.getCell(1), mp.getContent());// 会议名称
                setCellValue(row.getCell(2), DateUtil.dateToString(mp.getStarttime(), "yyyy年MM月dd日") + "\r\n"
                        + DateUtil.dateToString(mp.getEndtime(), "yyyy年MM月dd日"));// 会议时间
                String address = mp.getAddress();// 外部会议地址
                String address1 = mp.getReserve_address();// 内部会议地点
                setCellValue(row.getCell(3), (address == null || "".equals(address)) ? address1 : address);// 会议地点
                setCellValue(row.getCell(4), mp.getOrg());// 组织单位/部门
                setCellValue(row.getCell(5), mp.getLeader());// 公司参会领导
                setCellValue(row.getCell(6), mp.getFdepart());// 参会上级/外单位及领导
                setCellValue(row.getCell(7), mp.getCommitdepart());// 公司牵头处室
                setCellValue(row.getCell(8), mp.getContact() + "/" + mp.getContactphone());// 会议联系人/联系电话
                setCellValue(row.getCell(9), planMap.get(mp.getCommitdepart()));// 会议费年度预算（万）
                setCellValue(row.getCell(10), costsMap.get(mp.getCommitdepart()));// 会议费累计完成预算（万）
                setCellValue(row.getCell(10), mp.getActual_costs());// 本次会议费预算（万）
            }
            String fileName = "会议计划汇总表（" + month + "月）";
            wb.setSheetName(0, fileName);
            wb.write(os);
            os.flush();
            os.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入行
     * 
     * @Title: insertRow
     * @param sheet
     * @param startRow
     *            开始插入位置
     * @param rows
     *            插入的行数
     */
    private static void insertRow(Sheet sheet, int startRow, int rows) {  
        sheet.shiftRows(startRow, sheet.getLastRowNum(), rows, true, false);  
        for (int i = 0; i < rows; i++) {  
            Row sourceRow = null;//原始位置  
            Row targetRow = null;//移动后位置  
            Cell sourceCell = null;  
            Cell targetCell = null;  
            sourceRow = sheet.createRow(startRow);  
            targetRow = sheet.getRow(startRow + rows);  
            sourceRow.setHeight(targetRow.getHeight());  
  
            for (int m = targetRow.getFirstCellNum(); m < targetRow.getPhysicalNumberOfCells(); m++) {  
                sourceCell = sourceRow.createCell(m);  
                targetCell = targetRow.getCell(m);  
                sourceCell.setCellStyle(targetCell.getCellStyle());  
                sourceCell.setCellType(targetCell.getCellType());  
            }  
            startRow++;  
        }  
  
    }  

    private void setCellValue(Cell cell, Object value) {
        CellStyle cs = wb.createCellStyle();
        if (value instanceof String || value == null) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double || value instanceof Float) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            DataFormat fmt = wb.createDataFormat();
            cs.setDataFormat(fmt.getFormat("yyyy-MM-dd HH:mm:ss"));
            cell.setCellStyle(cs);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue("");
        }
    }

}  

