package common;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.wst.jsdt.core.IJavaScriptElement;

import copymenu.Activator;

public class Utils {
	
	private static IPreferenceStore store;
	
	public static IPreferenceStore getPreferenceStore() {
		if(store == null) {
			store = Activator.getDefault().getPreferenceStore();
		}
		return store;
	}
	
	/** ���Ƶ�ճ����
	 * @param display
	 * @param value
	 */
	public static void copy2clipboard(Display display, Object obj) {
		Clipboard cb = new Clipboard(display);
		TextTransfer transfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { obj }, new TextTransfer[] { transfer });
		cb.dispose();
	}

	/** �������·��
	 * @param obj
	 * @return
	 */
	public static String getFullPath(Object obj) {
		String path = "";
		if ((obj instanceof IResource)) {
			IResource file = (IResource) obj;
			path = file.getFullPath().toString();
		} else if ((obj instanceof IJavaElement)) {
			IJavaElement element = (IJavaElement) obj;
			path = element.getResource().getFullPath().toString();
		} else if ((obj instanceof IJavaScriptElement)) {
			IJavaScriptElement element = (IJavaScriptElement) obj;
			path = element.getResource().getFullPath().toString();
		} else {
			path = obj.getClass().toString();
		}

		return path;
	}

	/** ���ؾ���·��, ·���ָ���ΪϵͳĬ��
	 * @param obj
	 * @return
	 */
	public static String getAbsolutePath(Object obj) {
		String path = "";
		if ((obj instanceof IResource)) {
			IResource file = (IResource) obj;
			path = file.getLocation().toOSString();
		} else if ((obj instanceof IJavaElement)) {
			IJavaElement element = (IJavaElement) obj;
			path = element.getResource().getLocation().toOSString();
		} else if ((obj instanceof IJavaScriptElement)) {
			IJavaScriptElement element = (IJavaScriptElement) obj;
			path = element.getResource().getLocation().toOSString();
		} else {
			path = obj.getClass().toString();
		}

		return path;
	}
	
	/** ��ӡ���ݵ�����̨
	 * @param message
	 */
	public static void printOnConsole(String message) {
		MessageConsole console = new MessageConsole(KeyValues.viewId, null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		MessageConsoleStream consoleStream = console.newMessageStream();
		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
		consoleStream.println(message);
	}

}
