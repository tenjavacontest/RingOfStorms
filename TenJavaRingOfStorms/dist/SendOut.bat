@Echo off
replace %~dp0\*.jar D:\Stuff\ProgrammingSources /s /u
cd /d "D:\Stuff\Minecraft\servers"
replace %~dp0\*.jar %cd% /s /u