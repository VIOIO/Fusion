package com.fusion.make;

import com.fusion.tools.Tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateJava implements Generate {

    private static final String ABI = ".abi";
    private static final String BIN = ".bin";
    private static final String SOL = ".sol";

    private ArrayList<String> abiNames;
    private ArrayList<String> abiPaths;
    private String solidityPath, abiPath, javaPath, web3jPath;

    public GenerateJava(String solidityPath, String abiPath, String javaPath, String web3jPath) {
        this.solidityPath = solidityPath;
        this.abiPath = abiPath;
        this.javaPath = javaPath;
        this.web3jPath = web3jPath;
        abiNames = new ArrayList<>();
        abiPaths = new ArrayList<>();
    }

    public void build() {
        getFileList(solidityPath);
        for (String ss : abiNames) {
            try {
                String abi = Tool.getAbiPath(ss, abiPath);
                Tool.onExeCmd(abi);
                System.out.println(abi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String name : abiPaths) {
            List<String> sample = getFileList1(abiPath, name.substring(0, name.length() - 4));
            String s = Tool.getJavaPath(web3jPath, abiPath + sample.get(0), abiPath + sample.get(1), javaPath);
            Tool.onAbiToJava(s);
        }
    }

    private void getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    getFileList(files[i].getAbsolutePath());
                } else if (fileName.endsWith(SOL)) {
                    String strFileName = files[i].getAbsolutePath();
                    abiPaths.add(files[i].getName());
                    abiNames.add(strFileName);
                } else {
                    continue;
                }
            }
        }
    }

    private ArrayList<String> getFileList1(String strPath, String name) {
        ArrayList<String> arrayList = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        String abi = "";
        String bin = "";
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (fileName.endsWith(name + ABI)) {
                    abi = name + ABI;
                    Tool.renameFile(files[i].getAbsolutePath(), files[i].getParentFile().getPath() + "/" + abi);
                } else if (fileName.endsWith(name + BIN)) {
                    bin = name + BIN;
                    Tool.renameFile(files[i].getAbsolutePath(), files[i].getParentFile().getPath() + "/" + bin);
                } else {
                    continue;
                }
            }
        }
        arrayList.add(bin);
        arrayList.add(abi);
        return arrayList;
    }

}
