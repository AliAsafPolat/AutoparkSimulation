import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class IslemGerceklesmeGUI {

	protected Shell shell;
	private static String islem;
	
	public IslemGerceklesmeGUI(String iþlem) {
		this.islem=iþlem;
		this.open();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IslemGerceklesmeGUI window = new IslemGerceklesmeGUI(islem);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		/** take the primary monitor */
	    Monitor primary = display.getPrimaryMonitor();
	    
	    /** get the size of the screen */
	    Rectangle bounds = primary.getBounds();
	    
	    /** get the size of the window */
	    Rectangle rect = shell.getBounds();

	    /** calculate the centre */
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;

	    /** set the new location */
	    shell.setLocation(x, y);
		shell.open();
		shell.layout();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 188);
		shell.setText("Uyarý!");
		
		Label lblIslem = new Label(shell, SWT.NONE);
		lblIslem.setBounds(34, 39, 345, 26);
		lblIslem.setText("Ýþleminiz "+islem);
		
		Button btnTamam = new Button(shell, SWT.NONE);
		btnTamam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnTamam.setBounds(164, 101, 90, 30);
		btnTamam.setText("Tamam");

	}

}
