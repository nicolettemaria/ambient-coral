package com.nicolettetovstashy.coral.sketch

import processing.core.PApplet
import processing.serial.Serial

var myPort: Serial? = null

/**
 * Gets the serial port the Arduino is on
 */
fun getSerial(applet: PApplet) {
    // List all the available serial ports
    val ports = Serial.list()
    // printArray(ports);
    var matchingPort: String? = null
    val predicate = "/dev/tty.usbserial"
    for (port in ports) {
        println("Port: $port")
        if (port.indexOf(predicate) > -1) {
            matchingPort = port
        }
    }
    checkNotNull(matchingPort) { "No serial port matched the predicate \"$predicate\"" }

    // Open the port you are using at the rate you want:
    myPort = Serial(applet, matchingPort, 9600)
}

/**
 * Gets outputted values (x) from the MicroBit
 */
fun getArduinoValues() {
    while (myPort!!.available() > 0) {
        // read line
        val inStr = myPort!!.readStringUntil(10)
        if (inStr != null) {
            // println(inStr);
            // get pot serial value
            getPotVal(inStr.trim { it <= ' ' })?.also {
                potVal = it
            }
        }
    }
}

fun getPotVal(inStr: String): Int? {
    val potStr = getSerialParameter("pot", inStr)
    if (potStr != null) {
        try {
            return potStr.toInt()
        } catch (e: NumberFormatException) {
        }
    }
    return null
}


/**
 * gets an arbitrary serial parameter from string
 */
fun getSerialParameter(param: String, inStr: String): String? {
    val paramPrefix = "$param:"
    var paramVal: String? = null
    if (inStr.length > paramPrefix.length && inStr.substring(0, paramPrefix.length) == paramPrefix) {
        val paramStr = inStr.substring(inStr.indexOf(':') + 1)
        paramVal = paramStr
    }
    return paramVal
}

fun sendOverSerial(str: String) {
    myPort!!.write(str)
}