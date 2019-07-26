#!/usr/bin/python
import pandas as pd
import math
import xlrd
import os
import time
import zipfile
import xmltodict
import shutil
from app.db.utility import Utillity
END_HEAD = 5


def get_filename(file_path):
    for root, dirs, files in os.walk(file_path):
        root = root
        files = files
        # print(root) #当前目录路径
        # print(dirs) #当前路径下所有子目录
        # print(files) #当前路径下所有非目录子文件
    return root, files


def get_spec_max_version(file_path, filename):
    """
    获取excel最大版本号
    :param file_path: 文件所在路径
    :param filename: 文件名
    :return: 成功：True, '', max_version
             失败：False, '错误信息'，max_version＝0.0
    """
    rst, msg, ver = False, filename, 0.0
    one_file_path = os.path.join(file_path, filename)
    res, sheet_names = get_sheet_details(one_file_path)
    if not res:
        if os.path.exists(sheet_names):
            shutil.rmtree(sheet_names)
        try:
            wb = xlrd.open_workbook(one_file_path, on_demand=True)
            sheet_names = wb.sheet_names()
        except Exception as e:
            msg = filename + str(e)
            return rst, msg, ver

    history_sheet_name = get_history_sheet_name(sheet_names)
    if not history_sheet_name:
        msg = '[%s]不存在[履历更新/History]sheet.' % filename
        return rst, msg, ver
    ver_col_name, df = get_version_column_and_df(one_file_path, history_sheet_name)
    if not ver_col_name:
        msg = '[%s][%s sheet]缺少Version列.'% (filename, history_sheet_name)
        return rst, msg, ver
    max_ver = get_max_version_num(df, ver_col_name)
    if not max_ver:
        msg = '[%s][%s sheet][max_ver 列] 缺少版本号.' % (filename, history_sheet_name)
        return rst, msg, ver
    else:
        return True, msg, max_ver


# def remove_files():
#     """
#     删除文件，get_sheet_details()在解析excel文件可能发生异常退出，会有残存文件遗留
#     :return:
#     """
#     path = os.path.join(os.getcwd(), 'temp')
#     for root, dirs, files in os.walk(path):
#         for dir in dirs:
#             shutil.rmtree(os.path.join(root, dir))
#         for name in files:
#             os.remove(os.path.join(root, name))


def get_sheet_details(file_path):
    """
    获取excel的sheet的名称
    :param file_path: 文件路径　＋　文件名
    :return: 正常退出True,sheet_name的list
            异常退出 False,临时生成文件的路径
    """
    sheets = []
    # remove_files()
    only_id = Utillity.get_nextval()
    file_name = os.path.splitext(os.path.split(file_path)[-1])[0]
    file_name = '[' + str(only_id) + ']' + file_name
    # Make a temporary directory with the file name
    directory_to_extract_to = os.path.join(os.getcwd(), 'temp', file_name)
    if os.path.exists(directory_to_extract_to):
        shutil.rmtree(directory_to_extract_to)
    os.mkdir(directory_to_extract_to)

    # Extract the xlsx file as it is just a zip file
    try:
        zip_ref = zipfile.ZipFile(file_path, 'r')
        zip_ref.extractall(directory_to_extract_to)
        zip_ref.close()

        # Open the workbook.xml which is very light and only has meta data, get sheets from it
        path_to_workbook = os.path.join(directory_to_extract_to, 'xl', 'workbook.xml')
        with open(path_to_workbook, 'r', encoding='utf-8') as f:
            xml = f.read()
            dictionary = xmltodict.parse(xml)
            for sheet in dictionary['workbook']['sheets']['sheet']:
                sheets.append(sheet['@name'])

        # Delete the extracted files directory
        shutil.rmtree(directory_to_extract_to)
        return True, sheets
    except Exception as e:
        return False, directory_to_extract_to


def get_version_column_and_df(one_file_path, history_sheet, head=2):
    """
    读取excel的指定sheet的dataframe数据和列名
    :param one_file_path: 文件路径　＋　文件名
    :param history_sheet: sheet名
    :param head: 读取sheet的起始行
    :return: dataframe and 列名
    """
    for start_head in range(head, 5):
        df = pd.read_excel(one_file_path, sheetname=history_sheet, header=start_head, parse_cols=[2])
        col_name = get_ver_column(df)
        if col_name:
            return col_name, df
    return '', None


def get_max_version_num(df, ver_col_name):
    """
    获取最大version
    :param df: excel的指定sheet的dataframe数据
    :param ver_col_name: 列名
    :return: max_ver
    """
    from decimal import Decimal
    data = df[ver_col_name].values
    data_ver = []
    for ver_item in data:
        if isinstance(ver_item, float) and not math.isnan(ver_item):
            data_ver.append(ver_item)
    max_ver = max(data_ver)
    # xxx
    decimal_part = str(max_ver).split('.')[-1]
    if len(decimal_part) < 2:
        max_ver = Decimal(max_ver).quantize(Decimal('0.00'))
    return max_ver


def get_history_sheet_name(sheet_names):
    """
    获取不同项目excel文件的履历更新表
    :param sheet_names: excel所有的sheet名组成的list
    :return:
    """
    history_sheet = ''
    for history in ["更新履歴", "history", "History"]:
        if history in sheet_names:
            history_sheet = history
    return history_sheet


def get_ver_column(df):
    """
    获取sheet的版本号列名
    :param df:
    :return: column_name
    """
    column_name = ''
    cols = df.columns.values
    for col_name in ["版番", "Version", "Ver."]:
        if col_name in cols:
            column_name = col_name
            break
    return column_name


if __name__ == '__main__':
    start = time.time()
    file_path = r'C:\Users\wuhao\Desktop\17cy'
    root, files = get_filename(file_path)
    for item in files:
        start = time.time()
        res = get_spec_max_version(root, item)
        end = time.time()
        print(end - start)
        print(res)
    pass