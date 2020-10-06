package com.dgut.cloud_disk.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.pojo.vo.ImportUserVo;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * jxl读excel
 *
 * @author jianggujin
 *
 */
public class ReadExcel {
    public static List<ImportUserVo.UserVo> readExcelToUser(File xlsFile) throws IOException, BiffException, ParameterException {
        List<ImportUserVo.UserVo> users = new ArrayList<ImportUserVo.UserVo>();
        // 获得工作簿对象
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        // 遍历工作表
        if (sheets != null) {
            Sheet sheet = sheets[0];
            {
                // 获得行数
                int rows = sheet.getRows();
                if (rows < 2) {
                    throw new ParameterException("请填入参数");
                }
                // 获得列数
                int cols = sheet.getColumns();
                if (cols != 4) {
                    throw new ParameterException("请使用模板填写");
                }
                // 读取数据
                StringBuffer errormsg = new StringBuffer();
                int lag = 0;
                for (int row = 1; row < rows; row++) {
                    String sex = sheet.getCell(1, row).getContents();
                    String name = sheet.getCell(0, row).getContents();
                    String phone = sheet.getCell(2, row).getContents();
                    String email = sheet.getCell(3, row).getContents();
                    if (sex == null || name == null || phone == null || email == null) {
                        errormsg.append("第" + row + "行存在空值,");
                        lag = 1;
                    } else if ("".equals(sex) || "".equals(name) || "".equals(phone) || "".equals(email)) {
                        errormsg.append("第" + row + "行存在空值,");
                        lag = 1;
                    } else if (!sex.equals("男") && !sex.equals("女")) {
                        errormsg.append("第" + row + "行性别错误,");
                        lag = 1;
                    } else if (!Verify.verifyEmail(email)) {
                        errormsg.append("第" + row + "行邮箱错误,");
                        lag = 1;
                    } else if(Verify.verifyPhone(phone)==2){
                        errormsg.append("第" + row + "行电话号码错误,");
                        lag = 1;
                    }else if(Verify.verifyPhone(phone)==1){
                        errormsg.append("第" + row + "行电话号码已存在,");
                        lag = 1;
                    }
                    if (lag != 1) {
                        ImportUserVo.UserVo user = new ImportUserVo.UserVo();
                        user.setUserName(name);
                        user.setUserSex(sex);
                        user.setUserEmail(email);
                        user.setUserPhone(phone);
                        users.add(user);
                    }
                }
                if(lag == 1){
                    throw new ParameterException(errormsg.toString());
                }
            }
        }else{
            throw new ParameterException("请使用模板填写");
        }
        workbook.close();
        return users;
    }
}
