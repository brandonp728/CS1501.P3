public class Car implements Comparable<Car>
{
  private double price, mileage;
  private String make, model, color, VIN;

  public Car()
  {
    price=0;
    mileage=0;
    make="";
    model="";
    VIN="";
    color="";
  }

  public Car(double p, double m, String mak, String mod, String v, String c)
  {
    price=p;
    mileage=m;
    make=mak;
    model=mod;
    VIN=v;
    color=c;
  }

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double p)
  {
    price = p;
  }

  public double getMileage()
  {
    return mileage;
  }

  public void setMileage(double m)
  {
    mileage = m;
  }

  public String getMake()
  {
    return make;
  }

  public void setMake(String m)
  {
    make = m;
  }

  public String getModel()
  {
    return model;
  }

  public void setModel(String m)
  {
    model = m;
  }

  public String getColor()
  {
    return color;
  }

  public void setColor(String c)
  {
    color = c;
  }

  @Override
  public String toString()
  {
    String str = getColor() + " " + getMake() + " " + getModel() + "\nPrice: " + getPrice() + " Mileage: " + getMileage();
	  return str;
  }

public String getVIN()
  {
    return VIN;
  }

  public void setVIN(String v)
  {
    VIN = v;
  }

  @Override
  public int compareTo(Car p) {
	  return 0;
  }


}
