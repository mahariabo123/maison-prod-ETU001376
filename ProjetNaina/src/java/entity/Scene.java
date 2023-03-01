/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import HibernateDao.GenericDao;
import annotation.Attribut;
import annotation.NomTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name="Scene")
@NomTable(nom="scene")
public class Scene {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idscene")
    @SequenceGenerator(name="idscene", sequenceName="idscene", allocationSize=1)
    @Attribut(columnName = "idscene",isprimarykey = true)
    int idscene;
    @Attribut(columnName = "intitule")
    String intitule;
    @Attribut(columnName = "idplateau")
    int idplateau;
    @Attribut(columnName = "idwatch")
    int idwatch;
    @Attribut(columnName = "dure")
    String dure;
    @Attribut(columnName = "numero")
    int numero;
    

    public Scene(String intitule, int idplateau,int idwatch,String dure,int numero) {
        this.idscene = -1;
        this.intitule = intitule;
        this.idplateau = idplateau;
        this.idwatch = idwatch;
        this.dure=dure;
        this.numero=numero;
    }

    public Scene() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }
    

    public int getIdwatch() {
        return idwatch;
    }

    public void setIdwatch(int idwatch) {
        this.idwatch = idwatch;
    }
    
    
    public int getIdscene() {
        return idscene;
    }

    public void setIdscene(int idscene) {
        this.idscene = idscene;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getIdplateau() {
        return idplateau;
    }

    public void setIdplateau(int idplateau) {
        this.idplateau = idplateau;
    }
    
    
    public String plateau()throws Exception{
        Plateau ray=new Plateau(null);
        ray.setIdplateau(this.getIdplateau());
        return ((Plateau)new GenericDao().find(ray).get(0)).getIntitule();
    }
    
    
    
    
}
