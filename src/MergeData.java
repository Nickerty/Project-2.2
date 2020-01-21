import com.google.gson.*;

import java.util.*;

public class MergeData implements Runnable {

    int temp = 0;
    private ArrayList<Weatherstation> weatherstations = new ArrayList<Weatherstation>();

    public MergeData() {
    }

    @Override
    public void run() {
        while(true){
            System.out.println(this.temp);
            try{
                Thread.sleep(10000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addData(ArrayList<Weatherstation> data){
        weatherstations.addAll(data);
    }

    public String printIt(){
            String data = new Gson().toJson(weatherstations);
            return data;
    }



}
