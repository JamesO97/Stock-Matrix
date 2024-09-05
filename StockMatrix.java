package stockmatrix;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class StockMatrix 
{

    public static void main(String[] args) 
    {
        // The name of the file to open.
        // notice that the StockPrice_X_Data.txt is in the data package
        String fileName = "./src/Stock_Data.txt";
        
        //arraylist for storing stock price columns
        ArrayList<Double> x_stockPrices = new ArrayList<Double>();
        ArrayList<Double> ge_stockPrices = new ArrayList<Double>();
        ArrayList<Double> appl_stockPrices = new ArrayList<Double>();
        ArrayList<Double> goog_stockPrices = new ArrayList<Double>();
        ArrayList<Double> f_stockPrices = new ArrayList<Double>();
        
        
        ArrayList<Double> xCorrelations = new ArrayList<Double>(); 
        ArrayList<Double> geCorrelations = new ArrayList<Double>();  
        ArrayList<Double> applCorrelations = new ArrayList<Double>(); 
        ArrayList<Double> googCorrelations = new ArrayList<Double>(); 
        ArrayList<Double> fCorrelations = new ArrayList<Double>(); 
        

        try 
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =  new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =  new BufferedReader(fileReader);
              
            bufferedReader.readLine();//reads first line with header
            String[] splitMe ={};
            
            String currentLine;
            
            while ((currentLine = bufferedReader.readLine()) != null){
                
           
                 splitMe = currentLine.split(",");
                 
               
                 x_stockPrices.add(Double.parseDouble(splitMe[1]));
                 ge_stockPrices.add(Double.parseDouble(splitMe[2]));
                 appl_stockPrices.add(Double.parseDouble(splitMe[3]));
                 goog_stockPrices.add(Double.parseDouble(splitMe[4])); 
                 f_stockPrices.add(Double.parseDouble(splitMe[5]));  
            }
             

            // Always close files.
            bufferedReader.close(); 
            //------------------------------------------------------------------
            // Doing some calculations
            //------------------------------------------------------------------
            
        // handle errors if they arise
        } catch(FileNotFoundException ex){
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        } catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }//end try

       ArrayList<ArrayList<Double>> correlationMatrix = new ArrayList<>();
       
       xCorrelations.add(findCorrelation(x_stockPrices,x_stockPrices));
       xCorrelations.add(findCorrelation(x_stockPrices,ge_stockPrices));
       xCorrelations.add(findCorrelation(x_stockPrices,appl_stockPrices));
       xCorrelations.add(findCorrelation(x_stockPrices,goog_stockPrices));
       xCorrelations.add(findCorrelation(x_stockPrices,f_stockPrices));
       
       geCorrelations.add(findCorrelation(ge_stockPrices,x_stockPrices));
       geCorrelations.add(findCorrelation(ge_stockPrices,ge_stockPrices));
       geCorrelations.add(findCorrelation(ge_stockPrices,appl_stockPrices));
       geCorrelations.add(findCorrelation(ge_stockPrices,goog_stockPrices));
       geCorrelations.add(findCorrelation(ge_stockPrices,f_stockPrices));
       
       applCorrelations.add(findCorrelation(appl_stockPrices,x_stockPrices));
       applCorrelations.add(findCorrelation(appl_stockPrices,ge_stockPrices));
       applCorrelations.add(findCorrelation(appl_stockPrices,appl_stockPrices));
       applCorrelations.add(findCorrelation(appl_stockPrices,goog_stockPrices));
       applCorrelations.add(findCorrelation(appl_stockPrices,f_stockPrices));
       
       googCorrelations.add(findCorrelation(goog_stockPrices,x_stockPrices));
       googCorrelations.add(findCorrelation(goog_stockPrices,ge_stockPrices));
       googCorrelations.add(findCorrelation(goog_stockPrices,appl_stockPrices));
       googCorrelations.add(findCorrelation(goog_stockPrices,goog_stockPrices));
       googCorrelations.add(findCorrelation(goog_stockPrices,f_stockPrices));
       
       fCorrelations.add(findCorrelation(f_stockPrices,x_stockPrices));
       fCorrelations.add(findCorrelation(f_stockPrices,ge_stockPrices));
       fCorrelations.add(findCorrelation(f_stockPrices,appl_stockPrices));
       fCorrelations.add(findCorrelation(f_stockPrices,goog_stockPrices));
       fCorrelations.add(findCorrelation(f_stockPrices,f_stockPrices));
       
       
       for (int i = 0; i < 5; i++)
       {
           correlationMatrix.add(new ArrayList<>());
           for (int j = 0; j < 5; j++)
           {
               switch (i)
               {
                   case 0: 
                       correlationMatrix.get(i).add(xCorrelations.get(j));
                       break;
                   case 1: 
                       correlationMatrix.get(i).add(geCorrelations.get(j));
                       break;
                   case 2: 
                       correlationMatrix.get(i).add(applCorrelations.get(j));
                       break;
                   case 3: 
                       correlationMatrix.get(i).add(googCorrelations.get(j));
                       break;                      
                   case 4: 
                       correlationMatrix.get(i).add(fCorrelations.get(j));
                       break;                     
               }      
           }
       }
       
       
        for(int i=0; i < 5;i++)
        {
            System.out.print("[ ");
            for(double correlation: correlationMatrix.get(i))
            {
                System.out.printf("%.1f ", correlation);
       
            }
            System.out.println("]");
        }  
}
    
    
//------------------------------------------------------------------------------
// helper functions/
//------------------------------------------------------------------------------
   
    public static double findAverage(ArrayList<Double> prices)
    {
        
        double total = 0;
        for(int i = 0; i < prices.size(); i++){  
            total = total + prices.get(i);
        }
      double average = total/prices.size();
        
       return average;
    }//end findAverage()
    
    //--------------------------------------------------------------------------
    public static double findStandardDeviation(ArrayList<Double> prices)
    {
        double sum = 0.0, std = 0.0;
        for(double num : prices) {
            sum += num;
        }
        double mean = sum/prices.size();
        for(double num: prices) {
            std += Math.pow(num - mean, 2);
        }
        return Math.sqrt(std/(prices.size() - 1));
    }

    //--------------------------------------------------------------------------
    
    public static double findCorrelation(ArrayList<Double> firstPrices, ArrayList<Double> secondPrices )
    {
        
        double correlation = 0.0 ; 
        double sum = 0.0;
        for( int i = 0; i < firstPrices.size(); i++){
            sum = sum + ((firstPrices.get(i) - findAverage(firstPrices))* (secondPrices.get(i) - findAverage(secondPrices)));
        }
          correlation = 1 / (double)(firstPrices.size() - 1)* sum /( findStandardDeviation(firstPrices) * findStandardDeviation(secondPrices) );
                   
       return correlation;
    }//end findCorrelation()
    
    
}