@echo off
set startdir=%~dp0

del /f /s /q %startdir%\.sbtserver\ > nul
del /f /s /q %startdir%\project\project\ > nul
del /f /s /q %startdir%\project\target\ > nul
del /f /s /q %startdir%\target\ > nul
del /f /s /q %startdir%\.sbtserver\  > nul
del /f /s /q %startdir%\project\.sbtserver > nul
del /f /s /q %startdir%\project\.sbtserver.lock > nul
del /f /s /q %startdir%\project\sbt-ui.sbt > nul

rmdir /s /q %startdir%\.sbtserver\
rmdir /s /q %startdir%\project\project\
rmdir /s /q %startdir%\project\target\
rmdir /s /q %startdir%\target\