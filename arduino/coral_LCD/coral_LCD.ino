/*
  SparkFun Inventor’s Kit
  Circuit 4A-HelloWorld

  The LCD will display the words "Hello World" and show how many seconds have passed since
  the RedBoard was last reset.

  This sketch was written by SparkFun Electronics, with lots of help from the Arduino community.
  This code is completely free for any use.

  View circuit diagram and instructions at: https://learn.sparkfun.com/tutorials/sparkfun-inventors-kit-experiment-guide---v41
  Download drawings and code at: https://github.com/sparkfun/SIK-Guide-Code
*/

#include <LiquidCrystal.h>          //the liquid crystal library contains commands for printing to the display

int potPosition;                  //this variable will hold a value based on the position of the potentiometer

LiquidCrystal lcd(13, 12, 11, 10, 9, 8);   // tell the RedBoard what pins are connected to the display

void setup() {
  Serial.begin(9600);             //start a serial connection with the computer
  pinMode(5, OUTPUT);

  lcd.begin(16, 2);                 //tell the lcd library that we are using a display that is 16 characters wide and 2 characters high
  lcd.clear();                      //clear the display
}

void loop() {
  potPosition = analogRead(A1);    //set potPosition to a number between 0 and 1023 based on how far the knob is turned
  String prefix = "pot:";
  Serial.println(prefix + potPosition);     //print the value of potPosition in the serial monitor on the computer

//  analogWrite(A0, 101);            // set perceptible brightness; 

  String dateFromSerial = Serial.readStringUntil('\n');
  String baaFromSerial = Serial.readStringUntil('\n');
  
//  analogWrite(A0, potPosition);
//  int potValue = map(potPosition, 0, 1023, 0, 255);
//  Serial.println(potValue);
//  int x = map(120, 0, 1023, 0, 255);
//  analogWrite(A0, map(potPosition, 0, 1023, 0, 255));
//  analogWrite(A0, potPosition / 4);
  analogWrite(5, potPosition / 4);

  lcd.setCursor(0, 0);              //set the cursor to the 0,0 position (top left corner)
  lcd.print(dateFromSerial);       //print hello, world! starting at that position

  lcd.setCursor(0, 1);              //move the cursor to the first space of the bottom row
  String baaStr = "BAA: " + baaFromSerial;
  lcd.print(baaStr);
  // lcd.print(millis() / 1000);       //print the number of seconds that have passed since the last reset
}
