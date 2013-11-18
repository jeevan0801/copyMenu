package copymenu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import common.FileUtils;
import common.KeyValues;
import common.Utils;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CreateHotFixHandler extends AbstractHandler {

	private String homeDir;
	private String warDir;
	private String treeDir;
	private String webProjectDir;
	private String projectName;
	private String inText;
	private FileUtils fu;
	
	private String getAndFormat(String keyName) throws Exception{
		String value = Utils.getPreferenceStore().getString(keyName);
		if(value.indexOf("\\") > -1) {// windows路径都改为linux的
			value.replaceAll("\\", KeyValues.pathSplit);
		}
		if(!value.endsWith(KeyValues.pathSplit)){
			value += KeyValues.pathSplit;
		}
		return value;
	}
	
	
	public CreateHotFixHandler() throws Exception{
		homeDir = getAndFormat(KeyValues.homeDir);
		warDir = getAndFormat(KeyValues.warDir);
		treeDir = getAndFormat(KeyValues.treeDir);
		webProjectDir = getAndFormat(KeyValues.webProjectDir);
		projectName = Utils.getPreferenceStore().getString(KeyValues.projectName);
		inText = Utils.getPreferenceStore().getString(KeyValues.inText);
		
		fu = new FileUtils(homeDir, warDir, treeDir, webProjectDir, projectName, inText);
	}
	
	public String createPKG() throws Exception{
		if(fu != null) {
			return fu.createPKG();
		}
		return null;
	}

	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
			String result = createPKG();
			Utils.printOnConsole(result);
		} catch (Exception e) {
			Utils.printOnConsole(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
}
