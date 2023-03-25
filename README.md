# bobsgame-exporter

[![Discord](https://img.shields.io/discord/953721054495387659.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/FfDxFc4JuS)

This project currently only exports tilemaps from Bob's Game projects made with [bgEditor](https://github.com/bobsgame/bgEditor). Future functionality will be added for exporting maps to be compatible with [Tiled](https://www.mapeditor.org/) in the near future.

## Usage

Using the exporter is fairly simple, here is the usage:

```
usage: bgexporter
 -o,--output <outputFile>                          The path to the output
                                                   file.
 -pe,--png-export-tilemap <projectZipFilePath>     Exports the tilemap as
                                                   a PNG file.
```

An example command would be `java -jar bgexporter.jar --output test/tilemap_output.png -pe test/demo.zip`. This will export the tilemaps from `demo.zip` into `tilemap_output.png`.
