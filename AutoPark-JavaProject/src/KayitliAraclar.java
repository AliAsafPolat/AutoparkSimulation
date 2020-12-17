import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class KayitliAraclar {

	protected Shell shell;
	private static String yazý;
	
	public KayitliAraclar(String x) {
		this.yazý=x;
		this.open();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			KayitliAraclar window = new KayitliAraclar(yazý);
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
		shell.setSize(506, 460);
		shell.setText("SWT Application");
		
		Label lblOtoparkaKaytlAralar = new Label(shell, SWT.NONE);
		lblOtoparkaKaytlAralar.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblOtoparkaKaytlAralar.setBounds(10, 10, 269, 42);
		lblOtoparkaKaytlAralar.setText("Otoparka Kayýtlý Araçlar");
		
		Label lblAraçlar = new Label(shell, SWT.NONE);
		lblAraçlar.setBounds(20, 58, 437, 306);
		lblAraçlar.setText(yazý);
		
		Button btnTamam = new Button(shell, SWT.NONE);
		btnTamam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			shell.dispose();
			}
		});
		btnTamam.setBounds(367, 370, 90, 30);
		btnTamam.setText("Tamam");
	}
}
