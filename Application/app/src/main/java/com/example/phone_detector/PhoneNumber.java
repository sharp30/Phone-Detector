package com.example.phone_detector;

import java.io.Serializable;

public class PhoneNumber implements Serializable
{
    protected String number;
    protected String personName;

    public PhoneNumber(String number,String personName)
    {
        this.number = number;
        this.personName = personName;
    }
    public void SetPersonName(String personName)
    {
        this.personName = personName;
    }

    public String getNumber()
    {
        return number;
    }

    public String getPersonName()
    {
        return this.personName;
    }

}
