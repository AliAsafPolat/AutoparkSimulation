import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class AutoParkGUI {

	protected Shell shell;

	private static Double gelenPara;
	private static AutoPark otopark;
	private Text txtSaatlikUcret,txtSaat,txtDakika,txtPlaka,txtCapArtýr;
	private static int saat,dakika;
	private static Button btnResmiArac;
	private static Label lblPara,EnterBosYer;
	
		public AutoParkGUI(AutoPark otopark,Double para) {
		this.gelenPara=para;
		this.otopark=otopark;

	}
	
	public AutoParkGUI() {
		
	}
	
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		
		try {
			AutoParkGUI window = new AutoParkGUI(otopark,gelenPara);
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
	    
	    Label lblMaxKapasite = new Label(shell, SWT.NONE);
	    lblMaxKapasite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	    lblMaxKapasite.setBounds(615, 9, 101, 20);
	    lblMaxKapasite.setText("Max Kapasite :");
	    
	    Label lblCapEnter = new Label(shell, SWT.NONE);
	    lblCapEnter.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	    lblCapEnter.setBounds(728, 9, 70, 20);
	    lblCapEnter.setText(otopark.getCapacity().toString());
	    
	    Label lblMaxKapasiteArttr = new Label(shell, SWT.NONE);
	    lblMaxKapasiteArttr.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	    lblMaxKapasiteArttr.setBounds(615, 307, 157, 20);
	    lblMaxKapasiteArttr.setText("Max Kapasite Artt\u0131r :");
	    
	    txtCapArtýr = new Text(shell, SWT.BORDER);
	    txtCapArtýr.setBounds(696, 333, 78, 26);
	    
	    Button btnArttýr = new Button(shell, SWT.NONE);
	    btnArttýr.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		if(!txtCapArtýr.getText().isBlank())
	    			if(otopark.setCapacity(Integer.parseInt(txtCapArtýr.getText())))
	    				lblCapEnter.setText(txtCapArtýr.getText());
	    	}
	    });
	    
	    btnArttýr.setBounds(684, 376, 90, 30);
	    btnArttýr.setText("Arttýr");
	    
	    Label lblBosYer = new Label(shell, SWT.NONE);
	    lblBosYer.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	    lblBosYer.setBounds(615, 34, 83, 20);
	    lblBosYer.setText("Boþ Yer :");
	    
	    EnterBosYer = new Label(shell, SWT.NONE);
	    EnterBosYer.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	    EnterBosYer.setBounds(728, 35, 70, 20);
		EnterBosYer.setText(otopark.getSize().toString());
		
		Button btnIeridekiAralar = new Button(shell, SWT.NONE);
		btnIeridekiAralar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IceridekiAraclarGUI araçlar=new IceridekiAraclarGUI(otopark);
			}
		});
		
		btnIeridekiAralar.setBounds(59, 359, 148, 30);
		btnIeridekiAralar.setText("Ýçerideki Araçlar");
		
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		shell.setSize(839, 563);
		shell.setText("Otopark");
		//shell.pack();
		
		Label lblOtoparkYazisi = new Label(shell, SWT.NONE);
		lblOtoparkYazisi.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblOtoparkYazisi.setFont(SWTResourceManager.getFont("Arial", 16, SWT.BOLD));
		lblOtoparkYazisi.setBounds(0, 0, 439, 54);
		lblOtoparkYazisi.setText(this.otopark.getName()+" Otopark");
		
		Button btnTasitAra = new Button(shell, SWT.NONE);
		btnTasitAra.addSelectionListener(new SelectionAdapter() {
			private AracBulundu AracBulundu;

			@Override 
			public void widgetSelected(SelectionEvent e) {
				IslemGerceklesmeGUI islem;
				SubscribedVehicle x=otopark.searchVehicle(txtPlaka.getText());
				if(txtPlaka.getText().isBlank())
					islem=new IslemGerceklesmeGUI("gerçekleþmedi.");
				else if(x!=null&&x.getSubscription().isValid())
					AracBulundu =new AracBulundu("vardýr.");
				else
					AracBulundu =new AracBulundu("yoktur.");
			}
		});
		
		btnTasitAra.setBounds(59, 118, 148, 30);
		btnTasitAra.setText("Taþýt Ara");
		
		Button btnParkDurumu = new Button(shell, SWT.NONE);
		btnParkDurumu.addSelectionListener(new SelectionAdapter() {
			private isParkedGUI parkDurumu;
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				IslemGerceklesmeGUI islem;
				if(txtPlaka.getText().isBlank())
					islem=new IslemGerceklesmeGUI("gerçekleþmedi.");
				else if(otopark.isParked(txtPlaka.getText()))
					 parkDurumu=new isParkedGUI("dir.");
				else
					 parkDurumu=new isParkedGUI(" deðildir.");
			}
		});
		
		btnParkDurumu.setBounds(59, 154, 148, 30);
		btnParkDurumu.setText("Park Durumu Goster");
		
		Button btnAracEkle = new Button(shell, SWT.NONE);
		btnAracEkle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AracEkleGUI ekle=new AracEkleGUI(otopark);
				//ekle.open();
			}
		});
		btnAracEkle.setBounds(59, 287, 148, 30);
		btnAracEkle.setText("Araç Ekle");
		
		Button btnOtoparkGiris = new Button(shell, SWT.NONE);
		btnOtoparkGiris.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//OtoparkGiriþGUI giriþ=new OtoparkGiriþGUI(otopark);
				IslemGerceklesmeGUI islem;
				
				if(!txtDakika.getText().isBlank()&&!txtSaat.getText().isBlank()) {
					dakika=Integer.valueOf(txtDakika.getText());
					saat=Integer.valueOf(txtSaat.getText());
				}
					
				Time zaman=new Time(saat,dakika);
				String plaka=txtPlaka.getText();
				
				if(!txtPlaka.getText().isBlank() && otopark.vehicleEnters(plaka, zaman,btnResmiArac.getSelection())&&!txtDakika.getText().isBlank()&&!txtSaat.getText().isBlank()) {
					islem=new IslemGerceklesmeGUI(" baþarýyla gerçekleþti.");
					EnterBosYer.setText(otopark.getSize().toString());
				}else
					islem=new IslemGerceklesmeGUI(" gerçekleþmedi!");
				
				btnResmiArac.setSelection(false);
			}
		});
		
		btnOtoparkGiris.setBounds(363, 201, 148, 30);
		btnOtoparkGiris.setText("Otopark Giriþ");
		
		Button btnOtoparkCikis = new Button(shell, SWT.NONE);
		btnOtoparkCikis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IslemGerceklesmeGUI islem;
				if(!txtDakika.getText().isBlank()&&!txtSaat.getText().isBlank()) {
					dakika=Integer.valueOf(txtDakika.getText());
					saat=Integer.valueOf(txtSaat.getText());
				}
				
				Time zaman=new Time(saat,dakika);
				String plaka=txtPlaka.getText();
				
				if(!txtPlaka.getText().isBlank()&&otopark.vehicleExits(plaka, zaman)&&!txtDakika.getText().isBlank()&&!txtSaat.getText().isBlank()) {
					islem=new IslemGerceklesmeGUI(" baþarýyla gerçekleþti.");
					EnterBosYer.setText(otopark.getSize().toString());
				}else
					islem=new IslemGerceklesmeGUI(" gerçekleþmedi!");
				
				btnResmiArac.setSelection(false);
				lblPara.setText(otopark.getIncomeDaily().toString());
			}
		});
		
		btnOtoparkCikis.setBounds(363, 237, 148, 30);
		btnOtoparkCikis.setText("Otopark Çýkýþ");
		
		Button btnOtoparkBilgileri = new Button(shell, SWT.NONE);
		btnOtoparkBilgileri.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			KayitliAraclar araclar=new KayitliAraclar(otopark.toString());
			}
		});
		
		btnOtoparkBilgileri.setBounds(59, 323, 148, 30);
		btnOtoparkBilgileri.setText("Otopark Bilgileri");
		
		Label lblGuncelKazanc = new Label(shell, SWT.NONE);
		lblGuncelKazanc.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblGuncelKazanc.setBounds(615, 60, 107, 20);
		lblGuncelKazanc.setText("Güncel Kazanç :");
		
		txtPlaka = new Text(shell, SWT.BORDER);
		txtPlaka.setBounds(59, 86, 148, 26);
		
		Label lblPlakaGiriniz = new Label(shell, SWT.NONE);
		lblPlakaGiriniz.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblPlakaGiriniz.setBounds(59, 60, 116, 20);
		lblPlakaGiriniz.setText("Plaka Giriniz :");
		
		lblPara = new Label(shell, SWT.NONE);
		lblPara.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblPara.setBounds(728, 60, 70, 20);
		lblPara.setText(otopark.getIncomeDaily().toString());
		
		Label lblOthers = new Label(shell, SWT.NONE);
		lblOthers.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblOthers.setBounds(59, 261, 101, 20);
		lblOthers.setText("Diðer Ýþlemler :");
		
		Label lblSaatlikucret = new Label(shell, SWT.NONE);
		lblSaatlikucret.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblSaatlikucret.setBounds(615, 89, 107, 20);
		lblSaatlikucret.setText("Saatlik Ücret :");
		
		Label lblSaatlikEnt = new Label(shell, SWT.NONE);
		lblSaatlikEnt.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblSaatlikEnt.setBounds(728, 89, 70, 20);
		lblSaatlikEnt.setText(otopark.getHourlyFee().toString());
		
		
		Label lblGuncelle = new Label(shell, SWT.NONE);
		lblGuncelle.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblGuncelle.setBounds(617, 169, 157, 20);
		lblGuncelle.setText("Saatlik Ücret Güncelle :");
		
		txtSaatlikUcret = new Text(shell, SWT.BORDER);
		txtSaatlikUcret.setBounds(696, 203, 78, 30);
		
		
		Button btnGuncelle = new Button(shell, SWT.NONE);
		btnGuncelle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text=txtSaatlikUcret.getText();
				if(!txtSaatlikUcret.getText().isBlank()) {
					Double deger=Double.parseDouble(text);
					if(deger>0) {
						otopark.setHourlyFee(deger);
						lblSaatlikEnt.setText(otopark.getHourlyFee().toString());
					}
				}
			}
		});
		btnGuncelle.setBounds(684, 246, 90, 30);
		btnGuncelle.setText("Güncelle");
		
		Button btnTamam = new Button(shell, SWT.NONE);
		btnTamam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		
		btnTamam.setBounds(684, 458, 90, 30);
		btnTamam.setText("Tamam");
		
		Label lblSaat = new Label(shell, SWT.NONE);
		lblSaat.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblSaat.setBounds(363, 60, 70, 20);
		lblSaat.setText("Saat");
		
		Label lblDakika = new Label(shell, SWT.NONE);
		lblDakika.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblDakika.setBounds(456, 60, 70, 20);
		lblDakika.setText("Dakika");
		
		txtSaat = new Text(shell, SWT.BORDER);
		txtSaat.setBounds(348, 86, 78, 26);
		
		txtDakika = new Text(shell, SWT.BORDER);
		txtDakika.setBounds(448, 86, 78, 26);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		label.setBounds(432, 89, 11, 30);
		label.setText(":");
		
		btnResmiArac = new Button(shell, SWT.RADIO);	
		btnResmiArac.setBounds(348, 136, 111, 20);
		btnResmiArac.setText("Resmi Araç");

	}
}
