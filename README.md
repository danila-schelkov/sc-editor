# SC Editor

[Technical Requirements (ru)](./docs/technical_requirements.md)

## Building

The project uses Maven for building.

To build jar containing only the project code (without dependencies) use:

```shell
mvn clean package
```

And to build jar file with all dependencies built-in use following command:

```shell
mvn clean compile assembly:single
```

To build for all Operating Systems:
```shell
make release
```

## Running

1. Download jar from [releases](https://github.com/danila-schelkov/sc-editor/releases)
   for your OS or [build it yourself](#building).
2. Run with command line:
   ```shell
   java --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED -jar sc-editor.jar
   ```
   Note: JOGL requires you to pass certain VM options. So do not delete it, otherwise
   the program will not work.

### Troubleshooting

If you have issues with running an application try few next steps.

According to [the question](https://stackoverflow.com/questions/66722833/executable-jar-with-dependencies-and-dll-dependency-using-maven), adding `--illegal-access=permit` to the VM-Options solves run issues with Java 16:

```shell
java --illegal-access=permit -jar sc-editor.jar
```
