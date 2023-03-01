/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import HibernateDao.GenericDao;
import Service.*;
import entity.Scene;
import inter.InterfaceDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class SceneController {
    @Autowired
    SceneService ws;
    @Autowired
    PlateauService ps;
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    @RequestMapping(value="/plannifier")
    public String  planning(Model mod,@RequestParam Map<String,String> allParams){
        Scene ray=new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),null,-1);
        try{
            
            mod.addAttribute("planning",ws.planning(ray));
        }catch(Exception e){
            
        }
        return "planning";
    }
    @RequestMapping(value="/addscene")
    public String  addScene(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        String request="redirect:/list?idwatch="+allParams.get("idwatch");
        try{
            mod.addAttribute("idwatch",Integer.parseInt(allParams.get("idwatch")));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "addScene";
    }
    @RequestMapping(value="/modifscene")
    public String  modifScene(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        
        try{
            Scene ra=new Scene(null,-1,-1, null,-1);
            ra.setIdscene(Integer.parseInt(allParams.get("idscene")));
            Scene vao2=((Scene)gen.find(ra).get(0));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("modif",vao2);
            String request="redirect:/list?idwatch="+Integer.toString(vao2.getIdwatch());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "updateScene";
    }
    @RequestMapping(value="/details")
    public String  details(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        
        try{
            Scene ra=new Scene(null,-1,-1, null,-1);
            ra.setIdscene(Integer.parseInt(allParams.get("idscene")));
            Scene vao2=((Scene)gen.find(ra).get(0));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("modif",vao2);
            String request="redirect:/list?idwatch="+Integer.toString(vao2.getIdwatch());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "detail";
    }
    @RequestMapping(value="/list")
    public String  list (Model mod,@RequestParam Map<String,String> allParams){
        int nbPage=3;
        String sessionrecherche="tsisy";
        int num =1;
        mod.addAttribute("idwatch",Integer.parseInt(allParams.get("idwatch")));
        
       //int recherche=0;
       
       if(allParams.get("numPage")!=""&&allParams.get("numPage")!=null){
           num=Integer.parseInt(allParams.get("numPage"));
       }
       if((allParams.get("recherche")!=""&&allParams.get("recherche")!=null)){
           try{
               num=1;
               sessionrecherche=allParams.get("recherche");
               mod.addAttribute("listScene",ws.pagination(new Scene(allParams.get("recherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(allParams.get("recherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, 0), nbPage));
               if(ws.pagination(new Scene(allParams.get("recherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               e.printStackTrace();
           }
     
       }else if((allParams.get("sessionrecherche")!=""&&allParams.get("sessionrecherche")!=null&&allParams.get("sessionrecherche").compareToIgnoreCase("tsisy")!=0)){
           try{
               sessionrecherche=allParams.get("sessionrecherche");
                mod.addAttribute("listScene",ws.pagination(new Scene(allParams.get("sessionrecherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(allParams.get("sessionrecherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, 0), nbPage));
               if(ws.pagination(new Scene(allParams.get("sessionrecherche"),-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               
           }
    }else {
           try{
               mod.addAttribute("listScene",ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num, 0), nbPage));
               if(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),null,-1), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){
               
           }
        }
       mod.addAttribute("sessionrecherche",sessionrecherche);
       mod.addAttribute("nbPage",nbPage);
       mod.addAttribute("numPage",num);
       
       return "resultScene";
    }
}
