package com.fusion;

import com.fusion.tools.PropertiesStata;
import com.fusion.make.GenerateJava;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import java.util.concurrent.*;

public class BuildJavaAction extends AnAction {

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static final String SUCCESSFULLY = "Solidity generate successfully to java";
    private static final String FAIL = " Solidity generate fail to java ";

    private Notification n = null;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                PropertiesStata.StoreProperties properties = PropertiesStata.getProperties(project.getBasePath());
                GenerateJava generateJava = new GenerateJava(properties.solidity, properties.abi, properties.java, properties.web3j);
                generateJava.addListener(new Listeners() {
                    @Override
                    public void success() {
                        n = new Notification("success", null, NotificationType.INFORMATION);
                        n.setTitle(SUCCESSFULLY);
                        n.setContent("Java saved to " + properties.abi);
                    }

                    @Override
                    public void error(int status) {
                        n = new Notification("error", null, NotificationType.ERROR);
                        n.setTitle(FAIL);
                        switch (status) {
                            case GenerateJava.HAVEFILE:
                                n.setContent("Must check your fusion's config,please ! ");
                                break;
                            default:
                                n.setContent("your project not have solidity or abi ! ");
                                break;
                        }

                    }
                });
                generateJava.solidityBuild();
                Notifications.Bus.notify(n);
            }
        });

    }

}
