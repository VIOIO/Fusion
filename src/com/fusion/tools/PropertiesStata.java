package com.fusion.tools;

import a.a.S;
import com.fusion.Listeners;

import java.io.*;
import java.util.Properties;

public class PropertiesStata {

    private static final String WEB3J_PATH = "WEB3J_PATH";
    private static final String SOLIDITY_PATH = "SOLIDITY_PATH";
    private static final String JAVA_PATH = "JAVA_PATH";
    private static final String COMMENT = "JAVA_PATH";
    private static final String ABI_PATH = "ABI_PATH";
    private static final String PROPERTIES = "fusion.properties";
    private static final String CAHCE_PATH = "/abi/";

    public static void defaultTemplate(String path,Listeners templateListeners) {
        try {
            FileOutputStream out = new FileOutputStream(path + "/" + PROPERTIES);
            Properties prop = new Properties();
            prop.put(WEB3J_PATH, path);
            prop.put(SOLIDITY_PATH, path);
            prop.put(JAVA_PATH, path);
            prop.put(ABI_PATH, path + CAHCE_PATH);
            prop.store(out, COMMENT);
            out.close();
            templateListeners.success();
        } catch (IOException e) {
            templateListeners.error(0);
            e.printStackTrace();
        }
    }


    public static StoreProperties getProperties(String path) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path + "/" + PROPERTIES));
            Properties prop = new Properties();
            prop.load(in);
            String web3j = prop.getProperty(WEB3J_PATH);
            String solidity = prop.getProperty(SOLIDITY_PATH);
            String java = prop.getProperty(JAVA_PATH);
            String abi = prop.getProperty(ABI_PATH);
            return new StoreProperties(web3j, solidity, java, abi);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StoreProperties();
    }

    public static class StoreProperties {

        public StoreProperties() {

        }

        public StoreProperties(String web3j, String solidity, String java, String abi) {
            this.web3j = web3j;
            this.solidity = solidity;
            this.java = java;
            this.abi = abi;
        }

        public String web3j;
        public String solidity;
        public String java;
        public String abi;

    }

    public static String defaultTemplatePath(String path) {
        return path + "/" + PROPERTIES;
    }


}
