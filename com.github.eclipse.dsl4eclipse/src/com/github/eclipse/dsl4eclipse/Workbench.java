package com.github.eclipse.dsl4eclipse;

import static com.github.eclipse.dsl4eclipse.utils.UIThreadRunnable.syncExec;

import java.text.MessageFormat;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

import com.github.eclipse.dsl4eclipse.utils.Result;
import com.github.eclipse.dsl4eclipse.utils.StringConverter;
import com.github.eclipse.dsl4eclipse.utils.StringUtils;

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
				return activePage().showView(id);
			}
		});
	}

	public IWorkbenchWindow activeWorkbenchWindow() throws Exception {
		return syncExec(new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() throws Exception {
				return workbench().getActiveWorkbenchWindow();
			}
		});
	}

	public IWorkbench workbench() {
		return PlatformUI.getWorkbench();
	}
	
	public void openPerspectiveWithName(String perspectiveName) throws Exception {
		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
		for (IPerspectiveDescriptor perspective : perspectives) {
			if (perspectiveNameMatches(perspective, perspectiveName)) {
				activePage().setPerspective(perspective);
			}
		}

		String availablePerspectives = StringUtils.join(perspectives, ", ", new StringConverter() {
			public String toString(Object object) {
				return ((IPerspectiveDescriptor) object).getLabel();
			}
		});

		throw new IllegalArgumentException(MessageFormat.format("Cannot select perspective {0}. The valid perspective names are: {1}", perspectiveName, availablePerspectives));
	}
	
	public void openPerspectiveWithId(String perspectiveName) throws Exception {
		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
		for (IPerspectiveDescriptor perspective : perspectives) {
			if (perspectiveNameMatches(perspective, perspectiveName)) {
				activePage().setPerspective(perspective);
			}
		}

		String availablePerspectives = StringUtils.join(perspectives, ", ", new StringConverter() {
			public String toString(Object object) {
				return ((IPerspectiveDescriptor) object).getId();
			}
		});

		throw new IllegalArgumentException(MessageFormat.format("Cannot select perspective {0}. The valid perspective ids are: {1}", perspectiveName, availablePerspectives));
	}

	private IWorkbenchPage activePage() throws Exception {
		return activeWorkbenchWindow().getActivePage();
	}
	
	private boolean perspectiveNameMatches(final IPerspectiveDescriptor perspective, String perspectiveName) {
		String perspectiveLabel = perspective.getLabel();
		return perspectiveLabel.equals(perspectiveName) || perspective.getLabel().equals(perspectiveName + " (default)");
	}


}
