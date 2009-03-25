package com.github.eclipse.dsl4eclipse;

import static org.junit.Assert.assertEquals;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.junit.Before;
import org.junit.Test;

public class WorkbenchTest {

	private Workbench workbench;

	@Before
	public void before() throws Exception {
		workbench = new Workbench();
	}

	@Test
	public void canOpenAView() throws Exception {
		IViewPart view = workbench.openViewWithName("Navigator");
		assertEquals("Navigator", view.getTitle());
	}
	
	@Test
	public void throwsPartInitExceptionOnOpeningViewByIdThatDoesNotExist() throws Exception {
		try {
			workbench.openViewWithName("this does not exist");
		} catch (PartInitException e) {
			assertEquals("Could not create view: this does not exist", e.getMessage());
		}
	}

	@Test
	public void throwsPartInitExceptionOnOpeningViewByNameThatDoesNotExist() throws Exception {
		try {
			workbench.openViewWithId("this.does.not.exist");
		} catch (PartInitException e) {
			assertEquals("Could not create view: this.does.not.exist", e.getMessage());
		}
	}
}
