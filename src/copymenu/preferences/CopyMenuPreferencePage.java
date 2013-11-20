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
				"�����ļ�·���󵯳���ʾ��", getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(KeyValues.pathType,"ѡ���Ƶ��ļ�·������", 1,
				new String[][] { { "���·��", KeyValues.fullPath },{ "����·��", KeyValues.absolutePath } }, getFieldEditorParent()));
		
		addField(new DirectoryFieldEditor(KeyValues.homeDir,"����Ŀ¼:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.warDir,"���war����Ŀ¼:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.treeDir,"��������ļ���Ŀ¼:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(KeyValues.webProjectDir,"web�����й�������Ŀ¼:", getFieldEditorParent()));
		addField(new StringFieldEditor(KeyValues.projectName,"������:", getFieldEditorParent()));
		addField(new StringFieldEditor(KeyValues.inText,"��������ļ��б���ļ���:", getFieldEditorParent()));
		
	}

	public void init(IWorkbench workbench) {
	}

}