DISABLE_AUTOPROFILE = -Dskip.auto.profiles=true

VERSION := $(shell git describe --tags)

.PHONY: all
all: clean build

version:
	echo ${VERSION}

.PHONY: release
release: clean build-windows build-linux build-macos

.PHONY: build-windows
build-windows:
	mvnw -DreleaseVersion=${VERSION} compile -Pwindows ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-linux
build-linux:
	mvnw -DreleaseVersion=${VERSION} compile -Plinux ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: build-macos
build-macos:
	mvnw -DreleaseVersion=${VERSION} compile -Pmacos ${DISABLE_AUTOPROFILE} assembly:single

.PHONY: run
run: build
	${JAVA_HOME}/bin/java --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED -jar target/sc-editor-${VERSION}.jar

.PHONY: build
build:
	mvnw -DreleaseVersion=${VERSION} package

.PHONY: clean
clean:
	mvnw clean
