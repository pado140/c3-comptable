package com.redevance.dao.bean;

import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Livres extends ImplEntity {
    private StringProperty isbn,titre,description,resume,imagelivre,edition,anneesortie;
    private ObjectProperty<LocalDateTime> created;
    private ObjectProperty<Auteurs> auteurs;
    private ObservableList<VenteLivres> vente;
    private ObjectProperty<Contrats> contrat;
    private ObjectProperty<File> image;
    private ObjectProperty<Double> prix;
    private ObjectProperty<Integer> qty;
    private ObjectProperty<Categories>categories;
    private final String prefixPath=new File(System.getProperty("user.home")).getPath()+"/c3/Livres/";
    
    private String path;

    public Livres(Map<String, Object> data) {
        super(data);
    }

    public Livres() {
    }

    @Override
    public boolean isValid() {
        return !(getTitre().trim().isEmpty());
    }

    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String Isbn) {
        this.isbn.set(Isbn);
    }

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String Titre) {
        this.titre.set(Titre);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String Description) {
        this.description.set(Description);
    }

    public String getResume() {
        return resume.get();
    }

    public void setResume(String resume) {
        this.resume.set(resume);
    }

    public String getEdition() {
        return edition.get();
    }

    public void setEdition(String edition) {
        this.edition.set(edition);
    }

    public LocalDateTime getCreated() {
        return created.get();
    }

    public void setCreated(LocalDateTime last_Saved) {
        this.created.set(last_Saved);
    }

    public Auteurs getAuteur() {
        return auteurs.get();
    }
    
    public ObjectProperty<Auteurs> auteurProperty() {
        return auteurs;
    }

    public void setAuteur(Auteurs auteurs) {
        this.auteurs.set(auteurs);
    }

    public Contrats getContrat() {
        return contrat.get();
    }

    public void setContrat(Contrats contrat) {
        this.contrat.set(contrat);
    }


    public File getImage() {
        return image.get();
    }

    public void setImage(File image) {
        this.image.set(image);
    }
    public String getExte(String filename){
        return filename.substring(filename.lastIndexOf("."));
    }

    public Categories getCategorie() {
        return categories.get();
    }

    public void setCategorie(Categories categorie) {
        this.categories.set(categorie);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty resumeProperty() {
        return resume;
    }

    public ObjectProperty<Categories> categorieProperty() {
        return categories;
    }

    public StringProperty editionProperty() {
        return edition;
    }

    public ObjectProperty<LocalDateTime> createdProperty() {
        return created;
    }

    public ObjectProperty<Contrats> contratProperty() {
        return contrat;
    }

    public ObjectProperty<File> getImageProperty() {
        return image;
    }

    public StringProperty imagelivreProperty() {
        return imagelivre;
    }

    public void setImagelivre(String imagelivre) {
        this.imagelivre.set(imagelivre);
    }

    public String getImagelivre() {
        return prefixPath+imagelivre.get();
    }
    
    @Override
    public String toString() {
        return getTitre();
    }

    public ObjectProperty<Double> prixProperty() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix.set(prix);
    }

    public ObjectProperty<Integer> qtyProperty() {
        return qty;
    }
    
    public int getQty() {
        return qty.get();
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public Double getPrix() {
        return prix.get();
    }
    
    public ObservableList<VenteLivres> getVentes() {
        return vente;
    }

    public void setVentes(ObservableList<VenteLivres> ventes) {
        this.vente = ventes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void init() {
        isbn=new SimpleStringProperty();
        titre=new SimpleStringProperty();
        description=new SimpleStringProperty();
        resume=new SimpleStringProperty();
        anneesortie=new SimpleStringProperty();
        categories=new SimpleObjectProperty<>();
        edition=new SimpleStringProperty();
        created=new SimpleObjectProperty<>();
        contrat=new SimpleObjectProperty<>();
        image=new SimpleObjectProperty<>();
        auteurs=new SimpleObjectProperty<>();
        vente=FXCollections.observableArrayList();
        imagelivre=new SimpleStringProperty();
        prix=new SimpleObjectProperty();
        qty=new SimpleObjectProperty();
    }

    public String getAnneesortie() {
        return anneesortie.get();
    }
    
    public StringProperty anneesortieProperty() {
        return anneesortie;
    }

    public void setAnneesortie(String anneesortie) {
        this.anneesortie.set(anneesortie);
    }
    
    
}
