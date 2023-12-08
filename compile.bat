@echo off
if not exist bin mkdir bin else rd /S /Q bin
javac -sourcepath src -d bin -cp "lib/*" -encoding UTF-8 src/App.java
copy src\.e* bin