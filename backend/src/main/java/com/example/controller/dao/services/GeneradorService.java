package com.example.controller.dao.services;

import com.example.controller.dao.GeneradorDao;
import com.example.controller.tda.LinkedList;
import com.example.models.Generador;

import java.util.List;

public class GeneradorService {
    private GeneradorDao generadorDao;

    public GeneradorService() {
        this.generadorDao = new GeneradorDao();
    }

    public Boolean save() throws Exception {
        return generadorDao.save();
    }

    public Boolean update() throws Exception {
        return generadorDao.update();
    }

    @SuppressWarnings("rawtypes")
    public LinkedList listAll() {
        return generadorDao.getListAll();
    }

    public Generador getGenerador() {
        return generadorDao.getGenerador();
    }

    public void setGenerador(Generador generador) {
        generadorDao.setGenerador(generador);
    }


    public Generador get(int id) {
        return generadorDao.get(id);
    }

    public Boolean delete(Integer id) {
        return generadorDao.delete(id);
    }


    @SuppressWarnings("rawtypes")
    public List getAllGeneradores() {
        return generadorDao.getListAll().toList();
    }

   
    public void mergeSort(List<Generador> generadores, String criterio, boolean ascd) {
        generadorDao.mergeSort(generadores, criterio, ascd);
    }


    public void shellSort(List<Generador> generadores, String criterio, boolean ascd) {
        generadorDao.shellSort(generadores, criterio, ascd);
    }

    public void quickSort(List<Generador> generadores, String criterio, boolean ascd) {
        generadorDao.quickSort(generadores, criterio, ascd);
    }
}





