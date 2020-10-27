package com.phone_detector;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
    protected String number;
    protected String personName;
    protected boolean isHomeNumber;

    public PhoneNumber()
    {

    }

    public PhoneNumber(String number, String personName)
    {
        setNumber(number);
        this.personName = personName;
        this.isHomeNumber = !number.startsWith("+") && (number.startsWith("07") || number.length() < 10);
    }
    public PhoneNumber(String number, String personName,boolean state)
    {
        setNumber(number);
        this.personName = personName;
        this.isHomeNumber = !number.startsWith("+") && (number.startsWith("07") || number.length() < 10);
    }

    public void setNumber(String number)
    {
        if(number.startsWith("+972"))
            number = "0" + number.substring(4);
        this.number = number;
    }

    public void SetPersonName(String personName)
    {
        this.personName = personName;
    }


    public String getNumber() {
        return number;
    }

    public String getPersonName() {
        return this.personName;
    }

    public boolean getHomeState()
    {
        return this.isHomeNumber;

    }
}

