@echo off
setlocal
cd target
set "partialName=sc-editor"

for %%f in (*%partialName%*) do set "fullFile=%%f"

cd ..
java --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED -jar target/%fullFile%
endlocal
@echo on