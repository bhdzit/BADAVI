package com.bhdz.badavi;

public class Mercancia {
    public int idMercancia;
    public String NombreMercancia;
    public String MarcaMercancia;
    public int idMarca;
    public Mercancia(){

    }
    public Mercancia(int idMercancia, String nombreMercancia, String marcaMercancia, int idMarca) {
        this.idMercancia = idMercancia;
        NombreMercancia = nombreMercancia;
        MarcaMercancia = marcaMercancia;
        this.idMarca = idMarca;
    }

    public int getIdMercancia() {
        return idMercancia;
    }

    public void setIdMercancia(int idMercancia) {
        this.idMercancia = idMercancia;
    }

    public String getNombreMercancia() {
        return NombreMercancia;
    }

    public void setNombreMercancia(String nombreMercancia) {
        NombreMercancia = nombreMercancia;
    }

    public String getMarcaMercancia() {
        return MarcaMercancia;
    }

    public void setMarcaMercancia(String marcaMercancia) {
        MarcaMercancia = marcaMercancia;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }
}
