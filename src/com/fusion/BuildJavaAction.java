package com.fusion;

import com.fusion.tools.PropertiesStata;
import com.fusion.make.GenerateJava;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import java.util.concurrent.*;

public class BuildJavaAction extends AnAction {

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                PropertiesStata.StoreProperties properties = PropertiesStata.getProperties(project.getBasePath());
                GenerateJava generateJava = new GenerateJava(properties.solidity, properties.abi, properties.java, properties.web3j);
                generateJava.build();
            }
        });

    }

}
