DISABLE_AUTOPROFILE=-Dskip.auto.profiles=true

.PHONY: all
all: clean build

.PHONY: release
release: clean build-windows build-linux build-macos

.PHONY: build-windows
build-windows:
	mvnw compile -Pwindows ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-linux
build-linux:
	mvnw compile -Plinux ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-macos
build-macos:
	mvnw compile -Pmacos ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: run
run: build
	${JAVA_HOME}/java --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED -jar target/sc-editor-1.5.5.jar

.PHONY: build
build:
	mvnw package

.PHONY: clean
clean:
	mvnw clean
