@echo off
set startdir=%~dp0

del /f /s /q %startdir%\project\project\ > nul
del /f /s /q %startdir%\project\target\ > nul
del /f /s /q %startdir%\target\ > nul

rmdir /s /q %startdir%\project\project\
rmdir /s /q %startdir%\project\target\
rmdir /s /q %startdir%\target\