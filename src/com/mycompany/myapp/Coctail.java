package com.mycompany.myapp;
import java.util.*;

public class Coctail
{
	String name;
	String descr;
	String glass;
	String ice;
	HashMap<String, String> ingrCl;
	String[] toping;
	Coctail(String name, String descr, String glass, String ice, HashMap<String, String> ingrCl, String[] toping){
		this.name = name;
		this.descr = descr;
		this.glass=glass;
		this.ice = ice;
		this.ingrCl = ingrCl;
		this.toping = toping;
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

	public void setIngrCl(HashMap<String, String> ingrCl)
	{
		this.ingrCl = ingrCl;
	}

	public HashMap<String, String> getIngrCl()
	{
		return ingrCl;
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
