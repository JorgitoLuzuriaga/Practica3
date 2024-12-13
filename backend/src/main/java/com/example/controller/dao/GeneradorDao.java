package com.example.controller.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.tda.LinkedList;
import com.example.models.Generador;
import com.example.controller.excepcion.ListEmptyException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneradorDao extends AdapterDao<Generador> {
    private Generador generador;
    private static final String FILE_PATH = "/home/george/Escritorio/PracticaNro3/backend/media/generadores.json";  
    @SuppressWarnings("rawtypes")
    private LinkedList listAll;

    public GeneradorDao() {
        super(Generador.class);
    }

    public Generador getGenerador() {
        if (generador == null) {
            generador = new Generador();
        }
        return this.generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }

    @SuppressWarnings("rawtypes")
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = loadGeneradoresFromFile();
        }
        return listAll;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private LinkedList loadGeneradoresFromFile() {
        LinkedList generadores = new LinkedList();  
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Generador> generadoresList = objectMapper.readValue(new File(FILE_PATH), objectMapper.getTypeFactory().constructCollectionType(List.class, Generador.class));

            for (Generador generador : generadoresList) {
                generadores.add(generador);  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generadores;
    }

    public Boolean save() throws Exception {
        @SuppressWarnings("unused")
        Integer id = getListAll().getSize() + 1;
        this.persist(this.generador);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getGenerador(), getGenerador().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) {
        @SuppressWarnings("rawtypes")
        LinkedList list = getListAll();
        try {
            if (list.isEmpty()) {
                return false; 
            }
            for (int i = 0; i < list.getSize(); i++) {
                Generador p = (Generador) list.get(i);
                if (p.getId() == id) {
                    list.delete(i); 
                    this.listAll = list; 
                    return true; 
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public Generador get(int id) {
        @SuppressWarnings("rawtypes")
        LinkedList list = getListAll();
        try {
            for (int i = 0; i < list.getSize(); i++) {
                Generador p = (Generador) list.get(i);
                if (p.getId() == id) {
                    return p;
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return null; 
    }


    private boolean compare(Generador generador1, Generador generador2, String criterio, boolean ascd) {
        int comparison;
        if (criterio.equals("id")) {
            comparison = Integer.compare(generador1.getId(), generador2.getId());
        } else {
            comparison = generador1.getModelo().compareTo(generador2.getModelo());
        }
        return ascd ? comparison > 0 : comparison < 0;
    }
    
    public void shellSort(List<Generador> generadores, String criterio, boolean ascd) {
        int n = generadores.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Generador temp = generadores.get(i);
                int j;
                for (j = i; j >= gap && compare(generadores.get(j - gap), temp, criterio, ascd); j -= gap) {
                    generadores.set(j, generadores.get(j - gap));
                }
                generadores.set(j, temp);
            }
        }
    }

    public void quickSort(List<Generador> generadores, String criterio, boolean ascd) {
        quickSort(generadores, 0, generadores.size() - 1, criterio, ascd);
    }

    private void quickSort(List<Generador> generadores, int low, int high, String criterio, boolean ascd) {
        if (low < high) {
            int pi = partition(generadores, low, high, criterio, ascd);
            quickSort(generadores, low, pi - 1, criterio, ascd);
            quickSort(generadores, pi + 1, high, criterio, ascd);
        }
    }

    private int partition(List<Generador> generadores, int low, int high, String criterio, boolean ascd) {
        Generador pivot = generadores.get(high);
        int i = (low - 1);
    
        for (int j = low; j < high; j++) {
            boolean condition;
            if (criterio.equals("id")) {
                condition = Integer.compare(generadores.get(j).getId(), pivot.getId()) < 0;
            } else {
                condition = generadores.get(j).getModelo().compareTo(pivot.getModelo()) < 0;
            }
    
            if (ascd ? condition : !condition) {
                i++;
                Generador temp = generadores.get(i);
                generadores.set(i, generadores.get(j));
                generadores.set(j, temp);
            }
        }
    
        Generador temp = generadores.get(i + 1);
        generadores.set(i + 1, generadores.get(high));
        generadores.set(high, temp);
    
        return i + 1;
    }


    private void merge(List<Generador> generadores, List<Generador> left, List<Generador> right, String criterio, boolean ascd) {
        int i = 0, j = 0, k = 0;
    
        while (i < left.size() && j < right.size()) {
            boolean condition;
            if (criterio.equals("id")) {
                condition = Integer.compare(left.get(i).getId(), right.get(j).getId()) < 0;
            } else {
                condition = left.get(i).getModelo().compareTo(right.get(j).getModelo()) < 0;
            }
    
            if (ascd ? condition : !condition) {
                generadores.set(k++, left.get(i++));
            } else {
                generadores.set(k++, right.get(j++));
            }
        }
    
        while (i < left.size()) {
            generadores.set(k++, left.get(i++));
        }
    
        while (j < right.size()) {
            generadores.set(k++, right.get(j++));
        }
    }
 
    public void mergeSort(List<Generador> generadores, String criterio, boolean ascd) {
        if (generadores.size() > 1) {
            int middle = generadores.size() / 2;
            List<Generador> left = new ArrayList<>(generadores.subList(0, middle));
            List<Generador> right = new ArrayList<>(generadores.subList(middle, generadores.size()));

            mergeSort(left, criterio, ascd);
            mergeSort(right, criterio, ascd);

            merge(generadores, left, right, criterio, ascd);
        }
    }
}

