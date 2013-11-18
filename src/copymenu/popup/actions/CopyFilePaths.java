package copymenu.popup.actions;

import org.eclipse.jface.action.IAction;

import common.BaseAction;
import common.SelectedObjUtils;
import common.Utils;


public class CopyFilePaths extends BaseAction {
	private Object[] objects;
	public CopyFilePaths() {
		super();
	}
	
	public void run(IAction action) {
		try {
			objects = this.select.toArray();
			SelectedObjUtils so = new SelectedObjUtils(objects, this.shell);
			so.getFilePaths();
			so.copy2Clipboard();
			so.print();
		} catch (Exception e) {
			Utils.printOnConsole(e.getMessage());
			e.printStackTrace();
		}
	}
	
	

}
