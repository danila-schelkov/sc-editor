DISABLE_AUTOPROFILE = -Dskip.auto.profiles=true
ifeq ($(OS),Windows_NT)
	MAVEN_EXECUTABLE = mvnw.cmd
else
	MAVEN_EXECUTABLE = ./mvnw
endif

VERSION := $(shell git describe --tags)

.PHONY: all
all: clean build

version:
	echo ${VERSION}

.PHONY: release
release: clean build-windows build-linux build-macos

.PHONY: build-windows
build-windows:
	${MAVEN_EXECUTABLE} -DreleaseVersion=${VERSION} compile -Pwindows ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-linux
build-linux:
	${MAVEN_EXECUTABLE} -DreleaseVersion=${VERSION} compile -Plinux ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-macos
build-macos:
	${MAVEN_EXECUTABLE} -DreleaseVersion=${VERSION} compile -Pmacos ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: run
run: build
	${JAVA_HOME}/bin/java --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED -jar target/sc-editor-${VERSION}.jar

.PHONY: build
build:
	${MAVEN_EXECUTABLE} -DreleaseVersion=${VERSION} package

.PHONY: clean
clean:
	${MAVEN_EXECUTABLE} clean
