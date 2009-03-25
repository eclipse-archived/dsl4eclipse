package com.github.eclipse.dsl4eclipse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
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
	
	@Test
	public void canOpenPerspectiveByNameThatDoesNotExistThrowsException() throws Exception {
		try {
			workbench.openPerspectiveWithName("foo");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), startsWith("Cannot select perspective foo. The valid perspective names are:"));
			assertThat(e.getMessage(), containsString("Debug"));
			assertThat(e.getMessage(), containsString("Java Browsing"));
		}
	}
	
	@Test
	public void canOpenPerspectiveByIdThatDoesNotExistThrowsException() throws Exception {
		try {
			workbench.openPerspectiveWithId("foo.perspective");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), startsWith("Cannot select perspective foo.perspective. The valid perspective ids are:"));
			assertThat(e.getMessage(), containsString("org.eclipse.debug.ui.DebugPerspective"));
			assertThat(e.getMessage(), containsString("org.eclipse.jdt.ui.JavaBrowsingPerspective"));
		}
	}
}
