package com.bobsgame.exporter;

import com.bobsgame.exporter.export.ExportType;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BobsGameExporter {
    public static final Logger LOGGER = LoggerFactory.getLogger("bgexporter");

    private static final Option outputOption = Option.builder("o")
        .desc("The path to the output file.")
        .longOpt("output")
        .argName("outputFile")
        .hasArg(true)
        .required(true)
        .build();

    private static final Option mapExportOption = Option.builder("me")
        .desc("Exports the maps as a TMX file for Tiled.")
        .longOpt("maps-export")
        .argName("projectZipFilePath")
        .hasArg(true)
        .build();

    private static final Option pngExportTilemapOption = Option.builder("pe")
        .desc("Exports the tilemap as a PNG file.")
        .longOpt("png-export-tilemap")
        .argName("projectZipFilePath")
        .hasArg(true)
        .build();

    private static final Option tiledExportTilemapOption = Option.builder("te")
        .desc("Exports the tilemap as a Tiled TSX file.")
        .longOpt("tiled-export-tilemap")
        .argName("projectZipFilePath")
        .hasArg(true)
        .build();

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption(outputOption);
        options.addOption(pngExportTilemapOption);
        options.addOption(tiledExportTilemapOption);

        CommandLine commandLine;

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("bgexporter", options);
            return;
        }

        String outputPath;

        if (commandLine.hasOption("o")) {
            outputPath = commandLine.getOptionValue(outputOption);
        } else {
            LOGGER.info("You have not provided an output file path!");
            return;
        }

        // export maps as tileset tmx
        if (commandLine.hasOption("me")) {
            Exporter exporter = new Exporter(commandLine.getOptionValue(mapExportOption), outputPath);
            exporter.export(ExportType.MAPS);
            return;
        }

        // export tileset as png
        if (commandLine.hasOption("pe")) {
            Exporter exporter = new Exporter(commandLine.getOptionValue(pngExportTilemapOption), outputPath);
            exporter.export(ExportType.PNG_TILESET);
            return;
        }

        // export tileset as tileset tsx
        if (commandLine.hasOption("te")) {
            Exporter exporter = new Exporter(commandLine.getOptionValue(tiledExportTilemapOption), outputPath);
            exporter.export(ExportType.TILED_TILESET);
            return;
        }
    }
}
