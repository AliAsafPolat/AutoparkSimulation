import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class IceridekiAraclarGUI {

	protected Shell shell;
	private static AutoPark otopark;
	
	public IceridekiAraclarGUI(AutoPark otopark) {
		this.otopark=otopark;
		this.open();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IceridekiAraclarGUI window = new IceridekiAraclarGUI(otopark);
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		shell.setSize(450, 465);
		shell.setText("SWT Application");
		
		Label lblParkHalindekiAraclar = new Label(shell, SWT.NONE);
		lblParkHalindekiAraclar.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		lblParkHalindekiAraclar.setFont(SWTResourceManager.getFont("Arial", 14, SWT.BOLD));
		lblParkHalindekiAraclar.setBounds(10, 10, 298, 47);
		lblParkHalindekiAraclar.setText("Park Halindeki Araçlar");
		
		Label lblAraclar = new Label(shell, SWT.NONE);
		lblAraclar.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		lblAraclar.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		lblAraclar.setBounds(10, 63, 298, 345);
		lblAraclar.setText(otopark.iceridekiAraclar());
		Button btnTamam = new Button(shell, SWT.NONE);
		btnTamam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblAraclar.setText("");
			shell.dispose();
			}
		});
		
		btnTamam.setBounds(314, 378, 90, 30);
		btnTamam.setText("Tamam");
	}

}
