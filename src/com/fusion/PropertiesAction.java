package com.fusion;

import com.fusion.tools.PropertiesStata;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;


public class PropertiesAction extends AnAction {

    private static final String SUCCESSFULLY = "Fusion generate successfully a template";
    private static final String FAIL = " Fusion generate fail a template ";
    private static final String FAIL_CONTENT = " Must check your fusion,please ";

    private Notification n = null;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        PropertiesStata.defaultTemplate(project.getBasePath(), new Listeners() {
            @Override
            public void success() {
                n = new Notification("success", null, NotificationType.INFORMATION);
                n.setTitle(SUCCESSFULLY);
                n.setContent("Template saved to " + PropertiesStata.defaultTemplatePath(project.getBasePath()));
            }

            @Override
            public void error(int status) {
                n = new Notification("error", null, NotificationType.ERROR);
                n.setTitle(FAIL);
                n.setContent(FAIL_CONTENT);
            }
        });
        Notifications.Bus.notify(n);
        LocalFileSystem.getInstance().refresh(true);
    }

}
