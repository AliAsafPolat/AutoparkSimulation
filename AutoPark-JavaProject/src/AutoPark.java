import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class AutoPark {

	private final String db_isim = "otopark";
	private final String db_sifre = "admin";
	private String name;
	private ArrayList <SubscribedVehicle> SubscribedVehicles; 
	private ArrayList <ParkRecord> ParkRecords;
	private double hourlyFee;
	private double incomeDaily;
	private int capacity;
	private int size;
	
	public AutoPark(String name,double fee,int capacity) {
		this.hourlyFee=fee;
		this.name=name;
		SubscribedVehicles=new ArrayList<SubscribedVehicle>();
		ParkRecords=new ArrayList<ParkRecord>();
		
		if(capacity>0)				//sýfýrýn altýnda kapasite olamaz.
		this.capacity=capacity;
		else this.capacity=0;
		this.size=capacity;
		
	}
	
	public java.sql.Connection database_baglan() {
		Connection c = null;
		try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/otopark",
	            "postgres", "admin");
	         
	         System.out.println("Opened database successfully");
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
		
		return c;
    }
	
public SubscribedVehicle searchVehicle(String plaka) {
		
	try {
        java.sql.Connection conn = database_baglan();

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE plaka= '"+plaka+'\'');

        rs.next();
        String baslangic = rs.getString(2);
        String bitis = rs.getString(3);
        String[] arrOfStr_baslangic = baslangic.split("-", 3);
        String[] arrOfStr_bitis = bitis.split("-", 3);
        System.out.println(arrOfStr_baslangic[0] + " - "+ arrOfStr_baslangic[1] + " - " + arrOfStr_baslangic[2]);
        
        Date bas = new Date(Integer.parseInt(arrOfStr_baslangic[0]),Integer.parseInt(arrOfStr_baslangic[1])
        		, Integer.parseInt(arrOfStr_baslangic[2]));
        
        
        Date son = new Date(Integer.parseInt(arrOfStr_bitis[0]),Integer.parseInt(arrOfStr_bitis[1])
        		, Integer.parseInt(arrOfStr_bitis[2]));
        
        Subscription s = new Subscription(bas, son);
        
        SubscribedVehicle v = new SubscribedVehicle(plaka);
        v.setSubscription(s);
        
        rs.close();
        st.close();
        conn.close();
        return v;
    }catch(Exception ex) {
        System.out.println(ex.getMessage());
    }
    return null;
		
		
	}
	/* Düzeltildi
	public SubscribedVehicle searchVehicle2(String plate) {
		for(SubscribedVehicle s:SubscribedVehicles)				//Eðer kayýtlýlarda bulursa döndür.
			if(s.getPlate().compareTo(plate)==0)
				return s;
		
		return null;
	}*/
	
    public boolean isParked(String plaka) {
    	try {
            java.sql.Connection conn = database_baglan();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM icerdeki_araclar WHERE plaka= '"+plaka+'\'');
            boolean val = false;
            if(rs.next()!=false) {
            	val = true;
            }
            rs.close();
            st.close();
            conn.close();
            return val;
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    	return false;
    }


    /* Düzeltildi
	public boolean isParked2(String plate) {						//Park edilip edilmediðini kontrol eder.
		for(ParkRecord x:ParkRecords)
			if(x.getVehicle().getPlate().compareTo(plate)==0)
				if(x.getExitTime()==null)							//Park recorda giriþ girilip çýkýþ girilmediyse demek ki
					return true;									//araç hala içeridedir.
		
		return false;
	}*/
	
    public boolean addVehicle(SubscribedVehicle subcribedVehicle) {
		SubscribedVehicle araç=searchVehicle(subcribedVehicle.getPlate());
		System.out.print("Buraya geldi");
		if(araç!=null) {
			System.out.print("Buraya geldi");
			if(!araç.getSubscription().isValid()) {
				
				try {
		            java.sql.Connection conn = database_baglan();
		            
		            Statement st = conn.createStatement();
		            st.executeUpdate("DELETE FROM subscription WHERE plaka= '"+araç.getPlate()+'\'');
		            
		            String SQL = "INSERT INTO subscription(plaka,baslangic,bitis) VALUES('"+subcribedVehicle.getPlate()
		            +"','"+subcribedVehicle.getSubscription().getBegin().getStringVal()+"','"+subcribedVehicle.getSubscription().getEnd().getStringVal()+
		            "')";
		            st.executeUpdate(SQL);
		            System.out.print(SQL);
		            st.close();
		            conn.close();
		            
		            SubscribedVehicles.remove(araç);		//Eðer aracýn daha önceden üyeliði var ve bittiyse yenile
					SubscribedVehicles.add(subcribedVehicle);				//Öncekini çýkar yeni gelen objeyi ekle..
		            
		            return true;
		        }catch(Exception ex) {
		            System.out.println(ex.getMessage());
		            return false;
		        }
				
			}else
				return false;
		}
		
		try {
            java.sql.Connection conn = database_baglan();
            
            Statement st = conn.createStatement();
            String SQL = "INSERT INTO subscription(plaka,baslangic,bitis) VALUES('"+subcribedVehicle.getPlate()
            +"','"+subcribedVehicle.getSubscription().getBegin().getStringVal()+"','"+subcribedVehicle.getSubscription().getEnd().getStringVal()+
            "')";
            st.executeUpdate(SQL);
            System.out.print(SQL);
            st.close();
            conn.close();
            
            SubscribedVehicles.add(subcribedVehicle);				//Öncekini çýkar yeni gelen objeyi ekle..
      
            return true;
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
					
	}
    
    /* Düzeltildi
    // Üyelik Açýyor
	public boolean addVehicle2(SubscribedVehicle subcribedVehicle) {
		SubscribedVehicle araç=searchVehicle(subcribedVehicle.getPlate());
		if(araç!=null) {
			if(!araç.getSubscription().isValid()) {
				SubscribedVehicles.remove(araç);		//Eðer aracýn daha önceden üyeliði var ve bittiyse yenile
				SubscribedVehicles.add(subcribedVehicle);				//Öncekini çýkar yeni gelen objeyi ekle..
				return true;	
			}else
				return false;
		}
		
		SubscribedVehicles.add(subcribedVehicle);						//Eðer üyeliði yoksa direkt ekle.
		return true;
	}
	*/
    
	public boolean vehicleEnters(String plate,Time enter,boolean isOfficial) {	//Otoparka araç giriþidir.
		int arac_tipi=-1;
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT bosyer FROM otopark_bilgileri");

	        rs.next();
	        size = rs.getInt(1);
	        
	        rs.close();
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		if( size > 0 && !isParked(plate) ) {				//Eðer araba þuan park halinde deðilse ve otoparkta yer varsa gir.
			
			if(isOfficial) {
				OfficialVehicle of=new OfficialVehicle(plate);
				size--;
				arac_tipi = 0;
				//ParkRecord record=new ParkRecord(enter,of);
				//ParkRecords.add(record);
				return true;
			}
			
			SubscribedVehicle araba=searchVehicle(plate);
			if(araba!=null) { // üyelikli
				arac_tipi=2;
				//ParkRecord rec=new ParkRecord(enter,araba);	//Eðer araba daha önce geldiyse onun kaydýne ekle
				//ParkRecords.add(rec);
			}else { 	// üyeliksiz arac
				arac_tipi=1;						
				//RegularVehicle regVehicle=new RegularVehicle(plate);
				//ParkRecord record=new ParkRecord(enter, regVehicle);
				//ParkRecords.add(record);
			}
			size--;
			
			try {
		        java.sql.Connection conn = database_baglan();

		        String sql = "UPDATE otopark_bilgileri set bosyer="+String.valueOf(size);
		        Statement st = conn.createStatement();
		        st.executeUpdate(sql);
		        
		        st = conn.createStatement();
	            String SQL = "INSERT INTO icerdeki_araclar(plaka,tip,giris) VALUES('"+plate+ "'," + String.valueOf(arac_tipi)+
	            		",'"+enter.getTimeString()+"')";
	            st.executeUpdate(SQL);

		        st.close();
		        conn.close();
		    }catch(Exception ex) {
		        System.out.println(ex.getMessage());
		    }
			
			return true;
		}
		
		return false;
	}
	
	public boolean vehicleExits(String plate,Time exit) {
		System.out.println("Fonksiyon giris.");
		// icerdeki araçlardan plate bilgisine göre araç çekicez.
		// zaman bilgisini parse edicez
		// zaman bilgisnin sonra mý kontrolünü
		int arac_tip = -1;
		String giris_saati ="";
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT * FROM icerdeki_araclar WHERE plaka='"+plate+"'");

	        rs.next();
	        arac_tip = rs.getInt(2);
	        giris_saati = rs.getString(3);
	        
	        System.out.println("dsfsdf giris.");
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		String[] arrOfStr_giris = giris_saati.split(":", 2);
		Time girisZamani = new Time(Integer.parseInt(arrOfStr_giris[0]),Integer.parseInt(arrOfStr_giris[1]));
		
		SubscribedVehicle v = searchVehicle(plate);
		
		//ParkRecord record=getParkRecord(plate);
		if(girisZamani.isAfterThan(exit)) 
			return false;
		
		//record.setExitTime(exit);
		size++;
		if( arac_tip == 0 || 
				( v!=null && v.getSubscription().isValid()))
			incomeDaily += 0;                				// do not take money from subscriptions or official ones
		else
			incomeDaily += girisZamani.getDifference(exit) * hourlyFee;
	
		try {
	        java.sql.Connection conn = database_baglan();
	        System.out.println("Girdi");
	        String sql = "UPDATE otopark_bilgileri set bosyer="+String.valueOf(size) + ", kazanc="+String.valueOf(incomeDaily);
	        Statement st = conn.createStatement();
	        st.executeUpdate(sql);
	        
	        //st = conn.createStatement();
            st.executeUpdate("DELETE FROM icerdeki_araclar WHERE plaka= '"+plate+'\'');
            System.out.println("Çýktý");
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		return true;	
	}
	/*
	private ParkRecord getParkRecord(String plate){
	    for(ParkRecord parkRecord : ParkRecords)
	        if(parkRecord.getVehicle().getPlate().equalsIgnoreCase(plate) && parkRecord.getExitTime() == null  )
	            return parkRecord;
	    return null;
	}
	*/
	public String iceridekiAraclar() {
		String res = "";
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT * FROM icerdeki_araclar");

	        while (rs.next()) {
	            res = res+ rs.getString(1) + "\n";
	        }
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		if(res!="")
			return res;
		else
			return"Otoparkta araç bulunmamaktadýr.";
		
	}
	
	public Double getIncomeDaily() {
		return incomeDaily;
	}
	
	public String toString() {
		String taným = this.name + " Otoparký\n\nKayýtlý Araçlar :\n";
	
		String res = "";
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT * FROM subscription");

	        while (rs.next()) {
	            res = res+ rs.getString(1) + "\n";
	        }
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		return res;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	/*
	public ArrayList<SubscribedVehicle> getSubscribedVehicles() {
		return SubscribedVehicles;
	}
	
	public void setSubscribedVehicles(ArrayList<SubscribedVehicle> subscribedVehicles) {
		SubscribedVehicles = subscribedVehicles;
	}
	
	public ArrayList<ParkRecord> getParkRecords() {
		return ParkRecords;
	}
	
	public void setParkRecords(ArrayList<ParkRecord> parkRecords) {
		ParkRecords = parkRecords;
	}*/
	
	public Double getHourlyFee() {
		Double hourlyFee=(double)-1;
		
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT saatlikucret FROM otopark_bilgileri");

	        rs.next();
	        hourlyFee = (double) rs.getInt(1);
	        
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		return hourlyFee;
	}
	
	public void setHourlyFee(double hourlyFee) {
		this.hourlyFee = hourlyFee;
		try {
	        java.sql.Connection conn = database_baglan();
	        String sql = "UPDATE otopark_bilgileri set saatlikucret="+String.valueOf(hourlyFee);
	        Statement st = conn.createStatement();
	        st.executeUpdate(sql);
	        
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
	}
	
	public Integer getCapacity() {
		Integer capacity = -1;
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT kapasite FROM otopark_bilgileri");

	        rs.next();
	        capacity = rs.getInt(1);
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		return capacity;
	}
	
	public boolean setCapacity(int capacity) {
		if(this.capacity>capacity)
			return false;
		
		this.capacity = capacity;
		
		try {
	        java.sql.Connection conn = database_baglan();
	        String sql = "UPDATE otopark_bilgileri set kapasite="+String.valueOf(capacity);
	        Statement st = conn.createStatement();
	        st.executeUpdate(sql);
	        
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		return true;
	}
	
	public Integer getSize() {
		Integer size_ = -1;
		try {
	        java.sql.Connection conn = database_baglan();

	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("SELECT bosyer FROM otopark_bilgileri");

	        rs.next();
	        size_ = rs.getInt(1);
	        
	        rs.close();
	        st.close();
	        conn.close();
	        
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
		
		return size_;
	}
	
	public void setSize(int capCount) {
		size = capCount;
		try {
	        java.sql.Connection conn = database_baglan();
	        String sql = "UPDATE otopark_bilgileri set bosyer="+String.valueOf(capCount);
	        Statement st = conn.createStatement();
	        st.executeUpdate(sql);
	        
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	public void setIncomeDaily(double incomeDaily) {
		this.incomeDaily = incomeDaily;
		try {
	        java.sql.Connection conn = database_baglan();
	        String sql = "UPDATE otopark_bilgileri set kazanc="+String.valueOf(incomeDaily);
	        Statement st = conn.createStatement();
	        st.executeUpdate(sql);
	        
	        st.close();
	        conn.close();
	    }catch(Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	


}
