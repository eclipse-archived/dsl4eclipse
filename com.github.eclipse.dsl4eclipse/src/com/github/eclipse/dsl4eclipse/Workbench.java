package com.github.eclipse.dsl4eclipse;

import static com.github.eclipse.dsl4eclipse.utils.UIThreadRunnable.syncExec;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

import com.github.eclipse.dsl4eclipse.utils.Result;

public class Workbench {

	public IViewPart openViewWithName(String viewName) throws Exception {
		IViewDescriptor[] views = workbench().getViewRegistry().getViews();
		for (IViewDescriptor view : views) {
			if (view.getLabel().equals(viewName)) {
				return openViewWithId(view.getId());
			}
		}
		throw new PartInitException("Could not create view: " + viewName);
	}

	public IViewPart openViewWithId(final String id) throws Exception {
		return syncExec(new Result<IViewPart>() {
			public IViewPart run() throws Exception {
				return activeWorkbenchWindow().getActivePage().showView(id);
			}
		});
	}

	private IWorkbenchWindow activeWorkbenchWindow() throws Exception {
		return syncExec(new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() throws Exception {
				return workbench().getActiveWorkbenchWindow();
			}
		});
	}

	private IWorkbench workbench() {
		return PlatformUI.getWorkbench();
	}

}
