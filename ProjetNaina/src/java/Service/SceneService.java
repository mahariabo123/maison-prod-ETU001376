/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import HibernateDao.GenericDao;
import entity.*;
import inter.InterfaceDao;
import util.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
/**
 *
 * @author USER
 */
@Component
public class SceneService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Scene> all(Scene ray)throws Exception{
        ArrayList<Scene>list=new ArrayList<>();
        try{
            list=Util.castScene(gen.find(ray)); 
        }catch(Exception e){
            
        }
        return list;
    }
    public ArrayList<ArrayList<Scene>> planning(Scene ray)throws Exception{
        ArrayList<ArrayList<Scene>>fin=new ArrayList<ArrayList<Scene>>();
        ArrayList<Scene>list=new ArrayList<>();
        try{
            list=Util.castScene(gen.find(ray)); 
        }catch(Exception e){
            
        }
        Horaire h=(Horaire)(gen.find(new Horaire(-1)).get(0));
        if(list.size()>0){
            long heure=(long)(h.getHeure()*3600*1000);
            long copy=(long)(h.getHeure()*3600*1000);
            ArrayList<Scene>vao=new ArrayList<>();
            int i=0;
            while(i<list.size()){
                heure=heure-Util.strToMillis(list.get(i).getDure());
                if(heure<0){
                    int j=i;
                    heure=copy;
                    i=j;
                    fin.add(vao);
                    vao=new ArrayList<>();
                    continue;
                }else{
                    if(i==list.size()-1){
                        vao.add(list.get(i));
                        fin.add(vao);
                        vao=new ArrayList<>();
                    }else{
                        vao.add(list.get(i));
                    }
                    
                }
                i++;
            }
            
        }
        return fin;
    }
    
    public ArrayList<Scene> pagination(Scene ra,int page,int nbPage)throws Exception{
       
        String str="";
       
           str="select * from Scene WHERE idwatch="+Integer.toString(ra.getIdwatch());
       
           if(ra.getIntitule()!=null){
                  str="select * from Scene WHERE  LOWER(intitule) like '%"+ra.getIntitule().toLowerCase()+"%' and idwatch="+Integer.toString(ra.getIdwatch());
           }
        
           
       
        ArrayList<Object>list=gen.pagination2(ra,str,page,nbPage);   
        ArrayList<Scene>liste=new ArrayList<Scene>();
        for (int i = 0; i <list.size(); i++) {
            liste.add((Scene)list.get(i));
        }
        return liste;
    }
    public int nombreDePage(ArrayList<Scene>ray,int nbPage)throws Exception{
        int mod=(ray.size()%nbPage);
        int valiny=(int)(ray.size()/nbPage);
        if(mod==0){
            return valiny;
        }
        return valiny+1;
           
    }
}
