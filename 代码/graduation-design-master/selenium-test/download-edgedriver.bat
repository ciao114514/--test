@echo off
echo 正在下载Microsoft Edge WebDriver...

powershell -Command "Invoke-WebRequest -Uri 'https://msedgedriver.azureedge.net/131.0.2903.37/edgedriver_win64.zip' -OutFile 'edgedriver.zip'"

echo 正在解压文件...
powershell -Command "Expand-Archive -Path 'edgedriver.zip' -DestinationPath '.' -Force"

echo 正在重命名文件...
ren msedgedriver.exe msedgedriver.exe

echo 清理临时文件...
del edgedriver.zip

echo Microsoft Edge WebDriver下载完成!