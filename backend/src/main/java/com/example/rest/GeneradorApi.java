package com.example.rest;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.controller.dao.services.GeneradorService;
import com.example.models.Generador;
import javax.ws.rs.PathParam;

@Path("generador")
public class GeneradorApi {

    private GeneradorService generadorService = new GeneradorService();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGeneradores() {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorService ps = new GeneradorService();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenerador(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorService ps = new GeneradorService();
        try {
            ps.setGenerador(ps.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", "OK");
        map.put("data", ps.getGenerador());
        if (ps.getGenerador() == null) {
            map.put("data", "ESte identificador no existe");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGenerador(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            GeneradorService ps = new GeneradorService();
            ps.getGenerador().setModelo(map.get("modelo").toString());
            ps.getGenerador().setCoste(Double.parseDouble(map.get("coste").toString()));
            ps.getGenerador().setCapacidadGeneracion(Double.parseDouble(map.get("CapacidadGeneracion").toString()));
            ps.getGenerador().setConsumoPorHora(Double.parseDouble(map.get("ConsumoPorHora").toString()));
            ps.getGenerador().setUso(map.get("uso").toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Generador registered");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGenerador(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            GeneradorService ps = new GeneradorService();
            ps.setGenerador(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getGenerador().setModelo(map.get("modelo").toString());
            ps.getGenerador().setCoste(Double.parseDouble(map.get("coste").toString()));
            ps.getGenerador().setCapacidadGeneracion(Double.parseDouble(map.get("CapacidadGeneracion").toString()));
            ps.getGenerador().setConsumoPorHora(Double.parseDouble(map.get("ConsumoPorHora").toString()));
            ps.getGenerador().setUso(map.get("uso").toString());
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Generador updated");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGenerador(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            GeneradorService ps = new GeneradorService();
            ps.delete(id);
            map.put("msg", "OK");
            map.put("data", "Generador deleted");
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }


    
    @GET
    @Path("/ordenar/{metodo}/{criterio}/{orden}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Generador> sortGeneradores(@PathParam("metodo") String metodo,@PathParam("criterio") String criterio,@PathParam("orden") String orden) {
        boolean ascd = orden.equalsIgnoreCase("asc");
        @SuppressWarnings("unchecked")
        List<Generador> generadores = generadorService.getAllGeneradores(); 
        
        switch (metodo) {
            case "quick":
                if (criterio.equals("id")) {
                    generadorService.quickSort(generadores, "id", ascd);
                } else {
                    generadorService.quickSort(generadores, "modelo", ascd);
                }
                break;
            case "merge":
                if (criterio.equals("id")) {
                    generadorService.mergeSort(generadores, "id", ascd);
                } else {
                    generadorService.mergeSort(generadores, "modelo", ascd);
                }
                break;
            case "shell":
                if (criterio.equals("id")) {
                    generadorService.shellSort(generadores, "id", ascd);
                } else {
                    generadorService.shellSort(generadores, "modelo", ascd);
                }
                break;
            default:
                if (criterio.equals("id")) {
                    generadorService.quickSort(generadores, "id", ascd);
                } else {
                    generadorService.quickSort(generadores, "modelo", ascd);
                }
                break;
        }
        return generadores;
    }


}


