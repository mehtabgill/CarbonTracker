package ca.cmpt276.carbontracker;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/*
 * DataReader class that read in data from raw resources files and
 * generate a CarCollection of all cars and necessary information
 * for UI display
 */
public class DataReader {
    private static BufferedReader reader;

    private static CarCollection carList = new CarCollection();
    private static ArrayList<String> carMakeList = new ArrayList<>();
    public static CarCollection getCarList(InputStream is) {
        if(carList.size() == 0) {
            readCarData(is);
        }
        return carList;
    }

    public static ArrayList<String> getCarMakeList(InputStream is){
        if(carMakeList.size() == 0) {
            readCarMakeData(is);
        }
        return carMakeList;
    }

    private static void readCarMakeData(InputStream is){
        String line = "";
        reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        try{
            while((line = reader.readLine()) != null){
                carMakeList.add(line);
            }
        }catch (IOException e){
            Log.wtf("DataReader", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    private static void readCarData(InputStream is){
        if (carList.size() > 0)
        {
            return;
        }
        String line = "";
        reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        try{
            //read the first line with header
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");
                if(tokens[1].contains("\"")){
                    tokens[0] = tokens[0] + tokens[1];
                    for(int i = 1; i < 9; i++){
                        tokens[i] = tokens[i+1];
                    }
                }
                String additionalInfo = " ";
                if(tokens[3].length() > 0){
                    float displacement = 0.0f;
                    if(tokens[3].equals("NA")){
                        displacement = -1.0f;
                    } else
                    {
                       displacement =  Float.parseFloat(tokens[3]);
                    }
                    additionalInfo = (displacement + " L " + tokens[4]);
                }
                Car car = new Car(tokens[0], tokens[1], Integer.parseInt(tokens[2]), additionalInfo);
                car.setFuelType(tokens[5]);
                car.setMilesPerGallonCity(Integer.parseInt(tokens[6]));
                car.setMilesPerGallonHway(Integer.parseInt(tokens[7]));
                carList.add(car);
            }
        }
        catch (IOException e){
            Log.wtf("DataReader", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            Log.wtf("DataReader", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}
