package org.example;

import java.io.*;
import java.util.Scanner;


/*
*
* @author Daniel
*
 */

public class CombinarCorrespondencia {

    public static void combinarCorresponencia() {
        String outputFolderPath = "src/main/resources/salida";

        try {
            File outputFolder = new File(outputFolderPath);

            if (outputFolder.exists()) {
                File[] existingFiles = outputFolder.listFiles();
                for (File file : existingFiles) {
                    file.delete();
                }
            } else {
                outputFolder.mkdirs();
            }

            File listaClientesFile = new File("src/main/resources/data.csv");
            Scanner scanner = new Scanner(listaClientesFile);

            String newline = System.getProperty("line.separator");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length != 5) {
                    String outputFileName = outputFolderPath + File.separator + "ERROR_" + "template-" + fields[0] + ".txt";
                    FileWriter errorWriter = new FileWriter(outputFileName);
                    errorWriter.write("ERROR: Datos insuficientes en data.csv");
                    errorWriter.close();
                    continue;
                }

                String codigoCliente = fields[0];
                String nombreEmpresa = fields[1];
                String localidad = fields[2];
                String correoElectronico = fields[3];
                String nombreResponsable = fields[4];

                File templateFile = new File("src/main/resources/template.txt");
                BufferedReader reader = new BufferedReader(new FileReader(templateFile));

                String outputFileName = outputFolderPath + File.separator + "template-" + codigoCliente + ".txt";
                FileWriter writer = new FileWriter(outputFileName);

                String lineTemplate;
                while ((lineTemplate = reader.readLine()) != null) {
                    lineTemplate = lineTemplate.replace("%%1%%", nombreEmpresa);
                    lineTemplate = lineTemplate.replace("%%2%%", localidad);
                    lineTemplate = lineTemplate.replace("%%3%%", correoElectronico);
                    lineTemplate = lineTemplate.replace("%%4%%", nombreResponsable);
                    lineTemplate = lineTemplate.replace("%%5%%", codigoCliente);

                    writer.write(lineTemplate + newline);
                }

                writer.close();
                reader.close();
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
