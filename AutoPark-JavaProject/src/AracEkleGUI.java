import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class AracEkleGUI {

	protected Shell shell;
	private Text txtPlate;
	private static AutoPark otopark;
	private static Date baslangic,bitis;
	private static String plaka;
	private static int gun,ay,yil;
	
	public AracEkleGUI(AutoPark otopark) {
		this.otopark=otopark;
		this.open();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AracEkleGUI window = new AracEkleGUI(otopark);
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		shell.setSize(450, 338);
		shell.setText("Üye Giriþ");
		
		DateTime BaslangicT = new DateTime(shell, SWT.BORDER);
		BaslangicT.setBounds(165, 75, 133, 28);
		
		
		
		DateTime BitisT = new DateTime(shell, SWT.BORDER);
		BitisT.setBounds(165, 127, 133, 28);
		
		
		
		Label lblBaslangicTarihi = new Label(shell, SWT.NONE);
		lblBaslangicTarihi.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblBaslangicTarihi.setBounds(10, 75, 133, 28);
		lblBaslangicTarihi.setText("Baþlangýç Tarihi :");
		
		Label lblUyeGirisi = new Label(shell, SWT.NONE);
		lblUyeGirisi.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblUyeGirisi.setFont(SWTResourceManager.getFont("Arial", 16, SWT.BOLD));
		lblUyeGirisi.setBounds(20, 10, 284, 39);
		lblUyeGirisi.setText("Yeni Üye Giriþi");
		
		Label lblBitisTarihi = new Label(shell, SWT.NONE);
		lblBitisTarihi.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblBitisTarihi.setBounds(10, 127, 133, 28);
		lblBitisTarihi.setText("Bitiþ Tarihi :");
		
		txtPlate = new Text(shell, SWT.BORDER);
		txtPlate.setBounds(165, 171, 133, 28);
		
		
		Label lblPlaka = new Label(shell, SWT.NONE);
		lblPlaka.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblPlaka.setBounds(10, 171, 133, 28);
		lblPlaka.setText("Plaka :");
		
		Button btnEkle = new Button(shell, SWT.NONE);
		btnEkle.addSelectionListener(new SelectionAdapter() {
			private IslemGerceklesmeGUI islem;

			@Override
			public void widgetSelected(SelectionEvent e) {

				gun=BaslangicT.getDay();
				ay=BaslangicT.getMonth()+1;			//aylarý sýfýrdan baþlatýyor.
				yil=BaslangicT.getYear();
				baslangic=new Date(gun,ay,yil);
				
				gun=BitisT.getDay();
				ay=BitisT.getMonth()+1;
				yil=BitisT.getYear();
				bitis=new Date(gun, ay, yil);
			
				
				
				plaka=txtPlate.getText();
				
				Subscription kayit=new Subscription(baslangic, bitis, plaka);
				SubscribedVehicle arac=new SubscribedVehicle(plaka, kayit);
				Date today=Date.getToday();
				
				if(!txtPlate.getText().isBlank()&&!baslangic.isEqualWith(bitis)&&!today.isBeforethan(bitis)&&bitis.isBeforethan(baslangic)&&otopark.addVehicle(arac)) {
					islem=new IslemGerceklesmeGUI("baþarýyla gerçekleþti!");	//Eðer kutular boþ geçilmediyse ve ekleme iþlemi gerçekleþtiyse.
				}else
					islem=new IslemGerceklesmeGUI("gerçekleþmedi!");
			}
		});
		btnEkle.setBounds(208, 230, 90, 30);
		btnEkle.setText("Ekle");
		
		Button btnCikis = new Button(shell, SWT.NONE);
		btnCikis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCikis.setBounds(332, 230, 90, 30);
		btnCikis.setText("Çýkýþ");

	}
}
