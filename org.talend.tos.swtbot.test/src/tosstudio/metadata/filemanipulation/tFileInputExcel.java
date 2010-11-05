// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package tosstudio.metadata.filemanipulation;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetOfType;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.talend.swtbot.TalendSwtBotForTos;

/**
 * DOC Administrator class global comment. Detailled comment
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class tFileInputExcel extends TalendSwtBotForTos {

    private static SWTBotTree tree;

    private static SWTBotShell shell;

    private static SWTBotView view;

    private static SWTBotEditor botEditor;

    private static SWTBotGefEditor gefEditor;

    private static String FILENAME = "test_excel";

    private static String FILEPATH = "E:/testdata/test.xls";

    private static String JOBNAME = "UsetFileInputExcel";

    @Test
    public void createConnection() {
        view = gefBot.viewByTitle("Repository");
        view.setFocus();

        tree = new SWTBotTree((Tree) gefBot.widget(WidgetOfType.widgetOfType(Tree.class), view.getWidget()));
        tree.setFocus();

        tree.expandNode("Metadata").getNode("File Excel").contextMenu("Create file Excel").click();
        gefBot.waitUntil(Conditions.shellIsActive("New Excel File"));
        gefBot.shell("New Excel File").activate();

        gefBot.textWithLabel("Name").setText(FILENAME);
        gefBot.button("Next >").click();
        gefBot.textWithLabel("File").setText(FILEPATH);
        gefBot.treeWithLabel("Set sheets parameters").getTreeItem("All sheets/DSelect sheet").check();
        gefBot.button("Next >").click();
        while (!"Refresh Preview".equals(gefBot.button(0).getText())) {
        }
        gefBot.button("Next >").click();
        gefBot.button("Finish").click();
    }

    @Test
    public void createJob() {
        tree.select("Job Designs").contextMenu("Create job").click();

        gefBot.waitUntil(Conditions.shellIsActive("New job"));
        shell = gefBot.shell("New job");
        shell.activate();

        gefBot.textWithLabel("Name").setText(JOBNAME);

        gefBot.button("Finish").click();
    }

    @Test
    public void useDataInJob() {
        gefBot.viewByTitle("Palette").close();
        botEditor = gefBot.activeEditor();
        gefEditor = gefBot.gefEditor(botEditor.getTitle());

        gefEditor.activateTool("tFileInputExcel");
        gefEditor.click(100, 100);
        gefEditor.activateTool("tLogRow");
        gefEditor.click(300, 100);

        gefEditor.doubleClick(110, 110);
        gefBot.viewByTitle("Component").setFocus();
        gefBot.ccomboBox(0).setSelection("Repository");

        /* link two component */
        gefEditor.click(110, 110);
        gefEditor.clickContextMenu("Row").clickContextMenu("Main");
        gefEditor.click(310, 110);

        gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").click();
    }

    @Test
    public void runTheJob() {
        gefBot.viewByTitle("Run (Job " + JOBNAME + ")").setFocus();
        gefBot.button(" Run").click();

        gefBot.waitUntil(Conditions.shellIsActive("Launching " + JOBNAME + " 0.1"));
        gefBot.waitUntil(Conditions.shellCloses(gefBot.shell("Launching " + JOBNAME + " 0.1")), 10000);
    }

}
