package common;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class BaseAction implements IObjectActionDelegate {
	protected Shell shell;
	protected IStructuredSelection select;
	
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		if ((selection instanceof IStructuredSelection)) {
			this.select = ((IStructuredSelection) selection);
		}
	}
	
	

}
