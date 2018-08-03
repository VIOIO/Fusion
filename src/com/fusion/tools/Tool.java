package com.fusion.tools;

import java.io.*;

public class Tool {

    public static void onExeCmd(String commandStr) throws IOException {
        Runtime run = Runtime.getRuntime();
            Process process = run.exec(commandStr);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String message;
            while ((message = br.readLine()) != null) {
                sb.append(message);
            }
            System.out.println(sb);

    }


    public static void onAbiToJava(String path) {
        try {
            Process ps = Runtime.getRuntime().exec(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  static  void renameFile(String file, String toFile) {
        File toBeRenamed = new File(file);
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            System.out.println("File does not exist: " + file);
            return;
        }
        File newFile = new File(toFile);
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }
    }

    public static String getAbiPath(String inputPath, String outputPath) {
        return new StringBuffer().append("solcjs")
                .append(" ")
                .append(inputPath)
                .append(" --bin ")
                .append(" --abi ")
                .append(" --optimize -o ")
                .append(outputPath).toString();
    }

    public static String getJavaPath(String web3jPath,String inputBin,String inputAbi, String outputPath) {
        return  new StringBuffer()
                .append(web3jPath+" ")
                .append(" solidity generate ")
                .append(inputBin)
                .append(" ")
                .append(inputAbi)
                .append(" -o ")
                .append(outputPath)
                .append("  -p  java ").toString();
    }
}
