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
        this.number = number;
        this.personName = personName;
        this.isHomeNumber = !number.startsWith("+") && (number.startsWith("07") || number.length() < 10);
    }
    public PhoneNumber(String number, String personName,boolean state)
    {
        this.number = number;
        this.personName = personName;
        this.isHomeNumber =state;
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

