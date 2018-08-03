package com.fusion.make;

import com.fusion.Listeners;
import com.fusion.tools.Tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateJava implements Generate {

    public static final int NOFILE = 0x00;
    public static final int HAVEFILE = 0x01;

    private static final String ABI = ".abi";
    private static final String BIN = ".bin";
    private static final String SOL = ".sol";

    private ArrayList<String> abiNames;
    private ArrayList<String> abiPaths;
    private Listeners listeners;
    private String solidityPath, abiPath, javaPath, web3jPath;

    public GenerateJava(String solidityPath, String abiPath, String javaPath, String web3jPath) {
        this.solidityPath = solidityPath;
        this.abiPath = abiPath;
        this.javaPath = javaPath;
        this.web3jPath = web3jPath;
        abiNames = new ArrayList<>();
        abiPaths = new ArrayList<>();
    }

    public void solidityBuild() {

        fileList(solidityPath);

        if (abiNames.size() == 0) {
            listeners.error(NOFILE);
            return;
        }

        if (abiPaths.size() == 0) {
            listeners.error(NOFILE);
            return;
        }

        for (String name : abiNames) {
            try {
                String abi = Tool.getAbiPath(name, abiPath);
                Tool.onExeCmd(abi);
            } catch (IOException e) {
                e.printStackTrace();
                if (listeners != null) {
                    listeners.error(HAVEFILE);
                }
            }
        }

        for (String path : abiPaths) {
            List<String> sample = abiList(abiPath, path.substring(0, path.length() - 4));
            String cmd = Tool.getJavaPath(web3jPath, abiPath + sample.get(0), abiPath + sample.get(1), javaPath);
            Tool.onAbiToJava(cmd, listeners, HAVEFILE);
        }

        if (listeners != null) {
            listeners.success();
        }

    }

    public void addListener(Listeners listeners) {
        this.listeners = listeners;
    }

    private void fileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    fileList(files[i].getAbsolutePath());
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

    private ArrayList<String> abiList(String strPath, String name) {
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
