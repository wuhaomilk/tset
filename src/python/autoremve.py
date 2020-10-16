import os
import datetime
import shutil

def remove_temp_file():
	path = r"C:\Users\wuhao\AppData\Local\Temp"
	file_list = os.listdir(path)
	today = datetime.datetime.now()
	offset = datetime.timedelta(days=-2)
	re_date = today + offset

	for file in file_list:
		file_path = os.path.join(path, file)
		c_time = os.path.getmtime(file_path)
		file_time = datetime.datetime.fromtimestamp(c_time)
		if file_time < re_date:
			if "xl_converter" in file_path:				
				shutil.rmtree(file_path)
				
				
if __name__ == "__main__":
	remove_temp_file()
	