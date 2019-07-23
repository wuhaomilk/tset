# coding:utf-8

def convert_excel_htm3(t_path, local_file_path):
    from win32com.client import DispatchEx

    # w = win32com.client.DispatchEx('Word.Application')  # 或者使用下面的方法，使用启动独立的进程：
    xl = DispatchEx('Excel.Application') # 加载excel文件

    # 后台运行，不显示，不警告
    xl.Visible = 0
    xl.DisplayAlerts = 0

    wb = xl.Workbooks.Open(local_file_path)  # 打开文件
    wb.WebOptions.Encoding = 65001  # 65001编码是utf-8

    wb.SaveAs(t_path, FileFormat=44)  # 保存在本地 新文件夹下, constants.xlHtml==44
    xl.Workbooks.Close()
    xl.Quit()
    del xl


if __name__ == "__main__":
    t_path = r'C:\Users\wuhao\Desktop\new.html'
    filepath = r'C:\Users\wuhao\Desktop\17cy\func_2_05_Highway.xlsm'
    convert_excel_htm3(t_path, filepath)
    pass