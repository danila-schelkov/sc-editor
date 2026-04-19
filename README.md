# SC Editor

![Demo](https://github.com/user-attachments/assets/3ebf4ee0-62b4-4aa9-9ad4-e66a56478712)

[Technical Requirements (ru)](./docs/technical_requirements.md)
[Video Demo](https://github.com/user-attachments/assets/ed6b563c-e0f6-42d0-86a7-e12b9045cf54)

## Running

> [!NOTE]
> Try running the program by double-clicking the downloaded `.jar` file!
> 
> The program will attempt to restart with the proper VM options, but this method does not guarantee success.

### Running a jar

1. Download jar for your OS from [the latest release](https://github.com/danila-schelkov/sc-editor/releases/latest)
   or [build it yourself](#building).

2. Run it from the command line:
      
   ```shell
   java --add-exports=java.base/java.lang=ALL-UNNAMED --add-exports=java.desktop/sun.awt=ALL-UNNAMED --add-exports=java.desktop/sun.java2d=ALL-UNNAMED -jar sc-editor.jar
   ```

> [!WARNING]
> JOGL requires you to pass certain VM options. So do not delete it, otherwise the program will not work.

### Running with Make

```shell
make run
```

## Building

### Building with Make

```shell
make build
```

### Building with Maven

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

### Troubleshooting

If you have issues with running an application try few next steps.

According to [the question](https://stackoverflow.com/questions/66722833/executable-jar-with-dependencies-and-dll-dependency-using-maven), adding `--illegal-access=permit` to the VM-Options solves run issues with Java 16:

```shell
java --illegal-access=permit -jar sc-editor.jar
```
