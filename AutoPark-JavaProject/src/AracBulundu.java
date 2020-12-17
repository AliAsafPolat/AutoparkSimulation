import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class AracBulundu {

	protected Shell shell;
	private static String bilgi;
	
	public AracBulundu(String bilgi) {
		this.bilgi=bilgi;
		this.open();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AracBulundu window = new AracBulundu(bilgi);
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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		
		shell.setSize(450, 184);
		shell.setText("SWT Application");
		
		Label lblBilgiYazisi = new Label(shell, SWT.NONE);
		lblBilgiYazisi.setBounds(51, 49, 313, 20);
		lblBilgiYazisi.setText("Aradýðýnýz aracýn otopark üyeliði "+bilgi);
		
		Button btnTamam = new Button(shell, SWT.NONE);
		btnTamam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnTamam.setBounds(164, 97, 90, 30);
		btnTamam.setText("Tamam");

	}
}
