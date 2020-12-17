import static org.junit.Assert.*;
import org.junit.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class JUnitTestleri {

	protected static AutoPark otopark;
	protected static SubscribedVehicle v1;
	protected static SubscribedVehicle[] subscribedVehicles;
	protected static Time time1,time2;
	protected static Date date1,date2;
	
	
	@BeforeAll
	public static void setUpClass() {//her test için öncesi
		
		System.out.println("Before Each!");
		subscribedVehicles = new SubscribedVehicle[5];
		subscribedVehicles[0]=new SubscribedVehicle("10 KC 707");
		subscribedVehicles[1]=new SubscribedVehicle("15 RC 907");
		subscribedVehicles[2]=new SubscribedVehicle("16 IC 807");
		subscribedVehicles[3]=new SubscribedVehicle("34 SS 076");
		subscribedVehicles[4]=new SubscribedVehicle("34 YT 206");
		
		time1=new Time(1,15);
		time2=new Time(4,15);
		
		date1=new Date(10, 10, 2010);
		date2=new Date(15, 12, 2020);	
		
	}
	
	@BeforeEach
	public void setOp() throws  Exception {
		System.out.println("Set kýsmý");
		otopark=new AutoPark("Asaf",5,3);
	}

	@Test
	void OtoparkaArabaGirisi() {
		System.out.println("Araba ekleme");
		assertTrue(otopark.vehicleEnters(subscribedVehicles[0].getPlate(),time1, subscribedVehicles[0].isSpecial()));
		assertTrue(otopark.vehicleEnters(subscribedVehicles[1].getPlate(),time1, subscribedVehicles[1].isSpecial()));
		assertTrue(otopark.vehicleEnters(subscribedVehicles[2].getPlate(),time1, subscribedVehicles[2].isSpecial()));
		assertFalse(otopark.vehicleEnters(subscribedVehicles[3].getPlate(),time1, subscribedVehicles[3].isSpecial()));
	}
	
	@Test
	void OtoparkaParkKontrolu() {
		System.out.println("Araba parketme");
		assertTrue(otopark.vehicleEnters(subscribedVehicles[1].getPlate(), time1, subscribedVehicles[1].isSpecial()));
		assertTrue(otopark.isParked(subscribedVehicles[1].getPlate()));	
	}
	
	@Test 
	void OtoparktanArabaCikisi() {
		System.out.println("Araba Çýkýþý");
		assertTrue(otopark.vehicleEnters(subscribedVehicles[1].getPlate(), time1, subscribedVehicles[1].isSpecial()));
		assertTrue(otopark.isParked(subscribedVehicles[1].getPlate()));
		assertTrue(otopark.vehicleExits(subscribedVehicles[1].getPlate(), time2));
		assertFalse(otopark.isParked(subscribedVehicles[1].getPlate()));
	}
	
	@Test
	void OtoparkaYeniKullaniciEkleme() {
		System.out.println("Yeni kullanýcý ekleme");
		Subscription subs1=new Subscription(date1,date2);							//Kayýtlý kullanýcýlarý tuttuðu için eðer üyeliði yoksa 
		subscribedVehicles[3].setSubscription(subs1);								//veya geçersizse searchVehicledan null dönecektir.
		assertTrue(otopark.addVehicle(subscribedVehicles[3]));
		assertSame(subscribedVehicles[3], otopark.searchVehicle(subscribedVehicles[3].getPlate()));
		
	}
	
	@Test
	void ParkSuresiKontrolu() {
		RegularVehicle vec=new RegularVehicle("34 SR 1225");
		ParkRecord rec=new ParkRecord(time1,vec);
		rec.setExitTime(time2);
		assertEquals(3, rec.getParkingDuration());			//3 saat olduðundan dakika hesabýnda 180 geliyor.
	}
	
	@Test
	void UyelikGecerliligiKontrolu() {
		Subscription uyelik=new Subscription(date1, date2);
		assertTrue(uyelik.isValid());
		date1=new Date(10,10,2000);
		date2=new Date(10,05,2002);
		
		Subscription uyelik2=new Subscription(date1, date2);
		assertFalse(uyelik2.isValid());
		
	}
	@Test
	void ZamanKontrolleri() {
		assertEquals(3,time1.getDifference(time2));   	//Time1= 1:15   Time2= 4:15
	}
	
	@Test 
	void ZamanKontrolleri_After() {
		assertTrue(time2.isAfterThan(time1));				//time2 > time1 mi?
	}
	
	@AfterEach
	public void tearDown() {	//method sonrasý
		System.out.println("Bitti!");
	}
	
	@AfterAll					//her test için sonrasý
	public static void tearDownClass() {
		System.out.println("Class Bitti!");
		
	}
	
}
