/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import annotation.*;
import entity.*;
import java.util.*;
import java.lang.reflect.*;
import java.sql.Timestamp;
import java.time.*;
import java.lang.*;

/**
 *
 * @author USER
 */
public class Util {
    public static long strToMillis(String ray){
        int[]tab=traduction(ray.split(":"));
        long total=0;
        for (int i = 0; i < tab.length; i++) {
            if(i==0){
                total+=tab[i]*3600*1000;
            }else if(i==1){
                total+=tab[i]*60*1000;
            }else if(i==2){
                total+=tab[i]*1000;
            }
        }
        return total;
        
    }
    public static int[] traduction(String[]tab){
        int[]t=null;
        if(tab.length>0){
            t=new int[tab.length];
            for (int i = 0; i < t.length; i++) {
                t[i]=Integer.parseInt(tab[i]);
            }
        }
        return t;
    }
    public static  ArrayList<Watch> castWatch(ArrayList<Object>ray){
        ArrayList<Watch>ar=new ArrayList<Watch>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Watch){
                    ar.add((Watch)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Scene> castScene(ArrayList<Object>ray){
        ArrayList<Scene>ar=new ArrayList<Scene>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Scene){
                    ar.add((Scene)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Act> castAct(ArrayList<Object>ray){
        ArrayList<Act>ar=new ArrayList<Act>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Act){
                    ar.add((Act)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Plateau> castPlateau(ArrayList<Object>ray){
        ArrayList<Plateau>ar=new ArrayList<Plateau>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Plateau){
                    ar.add((Plateau)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Acteur> castActeur(ArrayList<Object>ray){
        ArrayList<Acteur>ar=new ArrayList<Acteur>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Acteur){
                    ar.add((Acteur)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Sentiment> castSentiment(ArrayList<Object>ray){
        ArrayList<Sentiment>ar=new ArrayList<Sentiment>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Sentiment){
                    ar.add((Sentiment)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static String FtoBase64(byte[]bytes){
       
       return Base64.getEncoder().encodeToString(bytes);
    }
    public static Date toGod(String dt){
        ZonedDateTime dateTime  = ZonedDateTime.parse(dt+":00.000Z");
        LocalDateTime localDateTime  = dateTime.toLocalDateTime();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());
		return date;
    }
    public static Timestamp toT(String dt){
        ZonedDateTime dateTime  = ZonedDateTime.parse(dt+":00.000Z");
        LocalDateTime localDateTime  = dateTime.toLocalDateTime();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());
        return timestamp;
    }
    public static Object traductionParameterDynamique(Map<String,String> attributValue)throws Exception{
        Class ray=Class.forName(attributValue.get("class"));
        Object temp=ray.getDeclaredConstructor().newInstance();
        Field[]fields=ray.getDeclaredFields();
        Method[]methods=ray.getDeclaredMethods();
       for (int i=0;i<fields.length ;i++) 
       {
               for (int j=0;j<methods.length ;j++ ) 
               {
                   if (("set"+fields[i].getName()).compareToIgnoreCase(methods[j].getName())==0)
                   {
                           if(attributValue!=null){
                                       if(attributValue.get(fields[i].getName())!=null){
                                               if (fields[i].getType().getName()=="int")
                                               {
                                                   try{
                                                       int a=Integer.parseInt(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){
                                                       
                                                   }              
                                               }else if (fields[i].getType().getName()=="float")
                                               {
                                                   try{
                                                       float a=Float.parseFloat(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="double")
                                               {
                                                   try{
                                                       double a=Double.parseDouble(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="long")
                                               {
                                                   try{
                                                       long a=Long.parseLong(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="java.lang.String")
                                               {
                                                   try{
                                                       methods[j].invoke(temp,attributValue.get(fields[i].getName()));
                                                   }catch(Exception er){

                                                   }              
                                               }else if(fields[i].getType().getName()=="java.sql.Timestamp"){
                                                try{
                                                    java.sql.Timestamp a=toT(attributValue.get(fields[i].getName()));
                                                    methods[j].invoke(temp,a);
                                                }catch(Exception er){

                                                }  
                                            }
                                            else if(fields[i].getType().getName()=="java.util.Date"){
                                                try{
                                                    Date a=toGod(attributValue.get(fields[i].getName()));
                                                    methods[j].invoke(temp,a);
                                                }catch(Exception er){

                                                }  
                                            }
                                       }
                           }
                           
                   }        
               }    
       }
       return temp;

    }
}
