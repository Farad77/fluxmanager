package re.infoport.model;

import org.apache.commons.net.ftp.FTPFile;

public class Fichier {
    private String fullNom;
    private FTPFile fichier;
    private String dir;
    private String nom;

    public Fichier(FTPFile fichier, String dir, String nom) {
        this.fichier = fichier;
        this.dir = dir;
        this.nom = nom;
        fullNom=this.dir+"/"+this.nom;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return fullNom;
    }

    public Fichier() {
    }

    public Fichier(String fullNom, FTPFile fichier) {
        this.fullNom = fullNom;
        this.fichier = fichier;
    }

    public String getFullNom() {
        return fullNom;
    }

    public void setFullNom(String fullNom) {
        this.fullNom = fullNom;
    }

    public FTPFile getFichier() {
        return fichier;
    }

    public void setFichier(FTPFile fichier) {
        this.fichier = fichier;
    }
}
