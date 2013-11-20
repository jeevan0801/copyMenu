package common;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	private static MessageConsole console;
	
	public static IPreferenceStore getPreferenceStore() {
		if(store == null) {
			store = Activator.getDefault().getPreferenceStore();
		}
		return store;
	}
	
	/** 复制到粘贴板
	 * @param display
	 * @param value
	 */
	public static void copy2clipboard(Display display, Object obj) {
		Clipboard cb = new Clipboard(display);
		TextTransfer transfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { obj }, new TextTransfer[] { transfer });
		cb.dispose();
	}

	/** 返回相对路径
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

	/** 返回绝对路径, 路径分隔符为系统默认
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
	
	/** 打印数据到控制台
	 * @param message
	 */
	public static void printOnConsole(String message) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = sdf.format(now);
		
		if (console == null) {
			console = new MessageConsole(KeyValues.viewId, null);
		}
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		MessageConsoleStream consoleStream = console.newMessageStream();
		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
		consoleStream.println("=============================================="+nowStr+"==============================================");
		consoleStream.println(message);
	}

}
