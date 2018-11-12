import java.util.*;
import java.util.Map.Entry;
public class CarTracker
{

  private static IndexMinPQ<Double> mileageQueue = new IndexMinPQ<Double>(100);
  private static IndexMinPQ<Double> priceQueue = new IndexMinPQ<Double>(100);
  static int addCount = 0;
  static HashMap<String, Car> carsList = new HashMap<String, Car>();
  static HashMap<Car, Integer> indexList = new HashMap<Car, Integer>();
  static Car[] carsByIndex = new Car[100];
  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in);
    int selection = -1;

    System.out.println("Welcome to Car Tracker!");
    while (selection != 0)
    {
        System.out.println();
        System.out.println("1. Add a car");
        System.out.println("2. Update a car");
        System.out.println("3. Remove a car");
        System.out.println("4. Find a car by lowest price");
        System.out.println("5. Find a car by lowest mileage");
        System.out.println("6. Find lowest priced car by make and model");
        System.out.println("7. Find lowest mileage car by make and model");
        System.out.println("0. Quit");
        System.out.print("Selection: ");

        try {
            selection = input.nextInt();
        } catch (NoSuchElementException e) {
            selection = -1;
        } catch (IllegalStateException e) {
            selection = -1;
        }
        input.nextLine();

        switch (selection) {
            case 1:
                add();
                break;
            case 2:
                update();
                break;
            case 3:
                remove();
                break;
            case 4:
                lowestPrice();
                break;
            case 5:
                lowestMileage();
                break;
            case 6:
                makeAndModelPrice();
                break;
            case 7:
                makeAndModelMileage();
                break;
            case 8:
            	  printCars();
            case 0:
                break;
            default:
                // Invalid, just ignore and let loop again
                break;
        }
    }
  }

    public static void add()
    {
      String stringAns = new String();
      double dubAns = 0;
      Scanner carScan = new Scanner(System.in);
      Car newCar = new Car();

      System.out.println("What is the price?");
      dubAns = carScan.nextDouble();
      newCar.setPrice(dubAns);

      System.out.println("Mileage?");
      dubAns = carScan.nextDouble();
      newCar.setMileage(dubAns);
      carScan.nextLine();

      System.out.println("Make?");
      stringAns = carScan.nextLine();
      newCar.setMake(stringAns);

      System.out.println("Model?");
      stringAns = carScan.nextLine();
      newCar.setModel(stringAns);

      System.out.println("Color?");
      stringAns = carScan.nextLine();
      newCar.setColor(stringAns);

      System.out.println("Please enter a valid* vehicle identification number");
      System.out.println("*Valid indicates a 17 letter word containing numbers\nand capital letters not including I (i), O (o), or Q (q)");
      stringAns = carScan.nextLine();
      newCar.setVIN(stringAns);

      priceQueue.insert(addCount, newCar.getPrice());
      mileageQueue.insert(addCount, newCar.getMileage());
      carsList.put(newCar.getVIN(), newCar);
      indexList.put(newCar, addCount);
      carsByIndex[addCount] = newCar;
      addCount++;
    }

    public static void update()
    {
      Scanner vinScan = new Scanner(System.in);
      System.out.println("Please enter the VIN of the car you wish to update");
      String ans = vinScan.nextLine();
      Car carToChange = carsList.get(ans);
      System.out.println("Here is the car you requested");
      System.out.println(carToChange.toString());
      while(true)
      {
        System.out.println("What would you like to update?");
        System.out.println("1) the price of the car");
        System.out.println("2) the mileage of the car");
        System.out.println("3) the color of the car");
        int choice = vinScan.nextInt();
        vinScan.nextLine();
        if(choice==1)
        {
          int index = indexList.get(carToChange);
          indexList.remove(carToChange);
          System.out.println("Enter price: ");
          double starshipEnterPrice = vinScan.nextDouble();
          vinScan.nextLine();   //fix newLine issue
          carToChange.setPrice(starshipEnterPrice);
          priceQueue.changeKey(index, starshipEnterPrice);
          carsList.replace(ans, carToChange);
          indexList.put(carToChange, index);
          carsByIndex[index] = carToChange;
          break;
        }
        else if(choice==2)
        {
          int index = indexList.get(carToChange);
          indexList.remove(carToChange);
          System.out.println("Enter mileage: ");
          double mileageCyrus = vinScan.nextDouble();
          vinScan.nextLine();   //fix newLine issue
          carToChange.setMileage(mileageCyrus);
          mileageQueue.changeKey(index, mileageCyrus);
          carsList.replace(ans, carToChange);
          indexList.put(carToChange, index);
          carsByIndex[index] = carToChange;
          break;
        }
        else if(choice==3)
        {
          int index = indexList.get(carToChange);
          indexList.remove(carToChange);
          System.out.println("Enter color: ");
          String paperMarioColorSplash = vinScan.nextLine();
          carToChange.setColor(paperMarioColorSplash);
          carsList.replace(ans, carToChange);
          indexList.put(carToChange, index);
          carsByIndex[index] = carToChange;
          break;
        }
      }
    }

    public static void remove()
    {
      Scanner vinScan = new Scanner(System.in);
      System.out.println("Please enter the VIN of the car you wish to remove");
      String ans = vinScan.nextLine();
      Car carToRem = carsList.get(ans);
      while(true)
      {
        System.out.println("Here is the car you requested: ");
        System.out.println(carToRem.toString());
        System.out.println("Are you sure you want to delete it? y/n");
        String choice = vinScan.nextLine();
        if(choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no"))
        {
          System.out.println("Please enter the VIN of the car you wish to remove");
          ans = vinScan.nextLine();
          carToRem = carsList.get(ans);
        }
        else
        {
          break;
        }
      }
      int index = indexList.get(carToRem);
      indexList.remove(carToRem);
      priceQueue.delete(index);
      mileageQueue.delete(index);
      carsList.remove(ans, carToRem);
      carsByIndex[index] = null;
    }

    public static void lowestPrice()
    {
      int index = priceQueue.minIndex();
      Car foundCar = carsByIndex[index];
      System.out.println("The lowest priced car is a: " );
      System.out.println(foundCar.toString());
    }

    public static void lowestMileage()
    {
      int index = mileageQueue.minIndex();
      Car foundCar = carsByIndex[index];
      System.out.println("The lowest mileage car is: " );
      System.out.println(foundCar.getColor() + " " + foundCar.getMake() + " " + foundCar.getModel());
      System.out.println("Price: " + foundCar.getPrice() + " Mileage: " + foundCar.getMileage());
    }

    public static void makeAndModelPrice()
    {
  	  double minPrice = Double.MAX_VALUE; //done to make sure there is
                                          //nothing bigger than minPrice initially
      Iterator<Entry<String, Car>> itr = carsList.entrySet().iterator();
      ArrayList<Car> list = new ArrayList<Car>();
      Scanner scan = new Scanner(System.in);
      System.out.println("What is the make?");
      String make = scan.nextLine();
      System.out.println("Model?");
      String model = scan.nextLine();
      Car carWanted = new Car();
      while(itr.hasNext())
      {
    	  Car curCar = itr.next().getValue();
    	  if(curCar.getMake().equalsIgnoreCase(make) && curCar.getModel().equalsIgnoreCase(model))
    	  {
    		  list.add(curCar);
        	minPrice = Math.min(minPrice, curCar.getPrice());
    	  }
      }
      for(int i=0; i<list.size(); i++)
      {
    	  if(list.get(i).getPrice() == minPrice)
    	  {
    		  carWanted = list.get(i);
          break;
    	  }
      }
      System.out.println("The lowest priced " + carWanted.getMake() + " " + carWanted.getModel() + " costs: " + carWanted.getPrice());
    }

    public static void makeAndModelMileage()
    {
  	  double minMileage = Double.MAX_VALUE;
      Iterator<Entry<String, Car>> itr = carsList.entrySet().iterator();
      ArrayList<Car> list = new ArrayList<Car>();
      Scanner scan = new Scanner(System.in);
      System.out.println("What is the make?");
      String make = scan.nextLine();
      System.out.println("Model?");
      String model = scan.nextLine();
      Car carWanted = new Car();
      while(itr.hasNext())
      {
    	  Car curCar = itr.next().getValue();
    	  if(curCar.getMake().equalsIgnoreCase(make) && curCar.getModel().equalsIgnoreCase(model))
    	  {
    		  list.add(curCar);
        	minMileage = Math.min(minMileage, curCar.getMileage());
    	  }
      }
      for(int i=0; i<list.size(); i++)
      {
    	  if(list.get(i).getMileage() == minMileage)
    	  {
    		  carWanted = list.get(i);
    	  }
      }
      System.out.println("The lowest mileage " + carWanted.getMake() + " " + carWanted.getModel() + " has: " + carWanted.getMileage() + " miles");
    }

    public static void printCars()
    {
      Iterator<Entry<String, Car>> itr = carsList.entrySet().iterator();
      while(itr.hasNext())
      {
        System.out.println(itr.next().getValue().toString());
      }
    }


}
