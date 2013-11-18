package copymenu.popup.actions;

import org.eclipse.jface.action.IAction;

import common.BaseAction;
import common.SelectedObjUtils;
import common.Utils;
import copymenu.handlers.CreateHotFixHandler;

public class CopyAndCreate extends BaseAction {

	@Override
	public void run(IAction arg0) {
		try {
			copyAndCreate();
			
		} catch (Exception e) {
			Utils.printOnConsole(e.getMessage());
			e.printStackTrace();
		}
	}

	public void copyAndCreate() throws Exception {
		Object[] objects = this.select.toArray();
		SelectedObjUtils so = new SelectedObjUtils(objects, this.shell);
		so.getFilePaths();
		so.copy2Clipboard();
		so.copy2File();
		
		CreateHotFixHandler chf = new CreateHotFixHandler();
		String result = chf.createPKG();
		Utils.printOnConsole(result);
	}

}
