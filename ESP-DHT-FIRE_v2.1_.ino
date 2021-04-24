

#include <ArduinoJson.h>
#include <ESP8266WiFi.h>    //esp8266 library
#include <FirebaseArduino.h>     //firebase library
#include <DHT.h> // dht11 temperature and humidity sensor library

#define FIREBASE_HOST "squadz-ae446-default-rtdb.firebaseio.com"  // the project name address from firebase id
#define FIREBASE_AUTH "r7vQNtDiVvr7Zu8sXV1WYXEhG6KBmsVa0khj0oaT"  // the secret key generated from firebase

#define WIFI_SSID ""                  // wifi name 
#define WIFI_PASSWORD ""                 //password of wifi 
 
#define DHTPIN 2                // what digital pin we're connected to
#define DHTTYPE DHT11              // select dht type as DHT 11 or DHT22
DHT dht(DHTPIN, DHTTYPE); 
                                                    

void setup() {
 

  Serial.begin(9600);
  delay(1000);                
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);    
  Serial.print("\nConnecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
      Serial.print(".");
      delay(500);
  }
  Serial.println();
  Serial.print("\nConnected to ");
  Serial.println(WIFI_SSID);
  Serial.print("\nIP Address is : ");   

  Serial.println(WiFi.localIP());                  //print local IP address
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  
  //for testing purpose
  if (Firebase.failed()) {
     Serial.print("\nconnection failed:");
     Serial.println(Firebase.error());
     return;
 }
 else(Serial.print("\nFirebase Authenticated"));// connected to firebase

 //Start reading dht sensor
 dht.begin();
}

void loop() { 

   if (Firebase.failed())
    {
      Serial.println("\nstreaming error");
      Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
      Serial.println(Firebase.error());
      delay(10);
      if (Firebase.failed())
      {
        Serial.println("\nError connecting firebase!");
        ESP.reset();
      }
     return;
    }
  
  int h = dht.readHumidity();       // Reading temperature or humidity 
  int t = dht.readTemperature();   // Read temperature as Celsius (the default)
  if (isnan(h) || isnan(t)) {  // Check if any reads failed and exit early (to try again).
    Serial.println(F("\nFailed to read from DHT sensor!")); 
    return;  
  }
  
  String location = "205 Humber College Blvd, Etobicoke";
  
  String latitude = "43.73127225142923";  
  String longitude = "-79.6080280720933";
  

  int dayD = 23;
  int dayY = 2021;
  String dayW[7] = {      "Sunday", "Monday", "Tuesday",
                    "Wednesday", "Thursday", "Friday", "Saturday" };                 
  String dayM[12] = { "January", "February", "March",
                     "April", "May", "June",
                   "July", "August", "September",
                  "October", "November", "December"  };
                
  String fireHumid = String(h)+ ("%"); /*convert integer humidity to string humidity */      Serial.print("\nHumidity: ");  Serial.print(h);Serial.println("%");
  String fireTemp = String(t)+("°C "); /*convert integer temperature to string temperature*/ Serial.print("\nTemperature: ");  Serial.print(t);  Serial.println("°C ");         
  String dayS = dayW[2] + (", ") + dayM[2]+ (", ")+ String(dayD) + (", ") + String(dayY);     Serial.print("\nDate: " + dayS);       
  String lonGitude = longitude;Serial.print("\nLong: " + lonGitude);
  String laTitude = latitude;Serial.print("\nLat: " + laTitude);

      
  //------------------New string set to designate Machine A its values--------------------|
  Firebase.setString("Weather/MachineA/Humidity", fireHumid);//setup path and send readings
  Firebase.setString("Weather/MachineA/Temperature",fireTemp);//setup path and send readings
  Firebase.setString("Weather/MachineA/Location", location);
  Firebase.setString("Weather/MachineA/Date", dayS);
  Firebase.setString("Weather/MachineA/Longitude", lonGitude);
  Firebase.setString("Weather/MachineA/Latitude", laTitude);
  
  delay(60000);
   
   
}
