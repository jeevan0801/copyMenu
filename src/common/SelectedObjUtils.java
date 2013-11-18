package common;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class SelectedObjUtils {
	private Object[] objects;
	private List<String> pathList;
	private Shell shell;
	
	private String pathType;
	private boolean popupFlag;
	
	public SelectedObjUtils(Object[] objects, Shell shell) {
		this.objects = objects;
		this.shell = shell;
		pathList = new ArrayList<String>();
		pathType = Utils.getPreferenceStore().getString(KeyValues.pathType);
		popupFlag = Utils.getPreferenceStore().getBoolean(KeyValues.popupFlag);
	}
	public void getFilePaths() {
		String path = "";
		for (int i = 0; i < objects.length; i++) {
			Object obj = objects[i];
			if(KeyValues.fullPath.equals(pathType)) {
				path = Utils.getFullPath(obj);
			}else {
				path = Utils.getAbsolutePath(obj);
			}
			pathList.add(path);
		}
	}
	
	private StringBuffer pathStrBuf() {
		StringBuffer sb = new StringBuffer();
		for(String str : pathList) {
			sb.append(str).append("\r\n");
		}
		return sb;
	}
	
	public void copy2Clipboard() throws Exception{
		StringBuffer sb = pathStrBuf();
		Utils.copy2clipboard(this.shell.getDisplay(), sb.toString());
	}
	
	public void print() throws Exception {
		StringBuffer sb = pathStrBuf();
		sb.append("A total of "+ pathList.size() + " files.");
		if(popupFlag){
			MessageDialog.openInformation(this.shell,"CopyFilePaths",sb.toString());
		}
		Utils.printOnConsole(sb.toString());
	}
	
	public void copy2File() throws Exception {
		StringBuffer sb = pathStrBuf();
		String homeDir = Utils.getPreferenceStore().getString(KeyValues.homeDir);
		String inText = Utils.getPreferenceStore().getString(KeyValues.inText);
		FileWriter fw = null;
		try {
			fw = new FileWriter(homeDir + inText);
			fw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
