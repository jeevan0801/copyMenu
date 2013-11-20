package copymenu.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import common.KeyValues;

import copymenu.Activator;


public class CopyMenuPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	public CopyMenuPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(KeyValues.popupFlag,
				"复制文件路径后弹出提示框", getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(KeyValues.pathType,"选择复制的文件路径类型", 1,
				new String[][] { { "相对路径", KeyValues.fullPath },{ "绝对路径", KeyValues.absolutePath } }, getFieldEditorParent()));
		
		addField(new DirectoryFieldEditor(KeyValues.homeDir,"工作目录:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.warDir,"存放war包的目录:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.treeDir,"存放升级文件的目录:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.webProjectDir,"web容器中工程所在目录:", getFieldEditorParent()));
		addField(new StringFieldEditor(KeyValues.projectName,"工程名:", getFieldEditorParent()));
		addField(new StringFieldEditor(KeyValues.inText,"存放升级文件列表的文件名:", getFieldEditorParent()));
		
	}

	public void init(IWorkbench workbench) {
	}

}