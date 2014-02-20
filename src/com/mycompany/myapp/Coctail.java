package com.mycompany.myapp;
import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class Coctail 
{
	String name;
	String descr;
	String glass;
	String ice;
	
	String[] toping;
	String[] method;
	String[] ingr;
	String[] cl;
	
	
	Coctail(String name, String descr, String glass, String ice, String[] ingr, String[] cl, String[] toping, String[] method){
		this.name = name;
		this.descr = descr;
		this.glass=glass;
		this.ice = ice;
		this.cl = cl;
		this.ingr = ingr;
		this.toping = toping;
		this.method = method;
	}

	public String[] getIngr() {
		return ingr;
	}

	public void setIngr(String[] ingr) {
		this.ingr = ingr;
	}

	public String[] getCl() {
		return cl;
	}

	public void setCl(String[] cl) {
		this.cl = cl;
	}

	
	public String[] getMethod() {
		return method;
	}

	public void setMethod(String[] method) {
		this.method = method;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
	}

	public String getDescr()
	{
		return descr;
	}

	public void setGlass(String glass)
	{
		this.glass = glass;
	}

	public String getGlass()
	{
		return glass;
	}

	public void setIce(String ice)
	{
		this.ice = ice;
	}

	public String getIce()
	{
		return ice;
	}

	

	public void setToping(String[] toping)
	{
		this.toping = toping;
	}

	public String[] getToping()
	{
		return toping;
	}

	
}
