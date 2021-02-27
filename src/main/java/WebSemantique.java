import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.ClosedException;
import com.hp.hpl.jena.vocabulary.*;
import javafx.util.Pair;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.graph.GraphFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebSemantique {
    static String PersonURI = "http://personne/rdf/";
    static String SymphonieURI = "http://jupiter/rdf/";

    static String MozartURI = PersonURI + "WolfgangMozart";
    static String MozartNom = "Wolfgang Amadeus Mozart";
    static String DOBMozart = "27 janvier 1756";
    static String BirthPlaceMozard = "Salzbourg";
    static String MozartDOD = "5 decembre 1791";
    static String MozartPOD = "Vienne";

    static String LeoMozartURI = PersonURI + "LeoMozart";
    static String LeoMozartNom = "Leopold Mozart";

    static String ConsWebURI = PersonURI + "ConWeber";
    static String ConsWebNom = "Constance Weber";

    static String ClaudioURI = PersonURI + "ClaudioAbbado";
    static String ClaudioNom = "Claudio Abbado";

    static String PartieURI = SymphonieURI + "Partie";
    static String Partie1 = "Molto Allegro";
    static String Partie2 = "Manuetto";
    static String Partie3 = "Allegro vivace";
    static String Partie4 = "Andante cantabile";

    static String JupiterURI = SymphonieURI + "jupiter";
    static String JupiterTitre = "Jupiter";
    static String JupiterPropritete = "41 Symphonie en ut majeur de Mozart";
    static String JupiterType = "Symphonie";
    static String JupiterAnneeEnregistrement = "1980";
    static String JupiterOrchestre = "orchestre symphonique de londre";


    private Model model;

    public WebSemantique(Model model, List<Pair<String, String>> prefixs) {
        this.model = model;
        for (Pair<String, String> s : prefixs) {
            model.setNsPrefix(s.getKey(), s.getValue());
        }
    }

    /**
     * @param s subject
     * @param p propriety
     * @param o object --> S'il pointe vers une ressource alors o devra suivre ce format : "URI|..."
     */
    public void addStatement(String s, String p, String o) {
        Resource res;
        Property property;
        String[] dataO = o.split("\\|");
        if ((res = model.getResource(s)) == null) {
            res = model.createResource(s);
            if ((property = model.getProperty(p)) == null) {
                property = model.createProperty(p);
            }
            if (dataO.length != 2) res.addProperty(property, o);
            else res.addProperty(property, model.getResource(dataO[1]));
        } else {
            if ((property = model.getProperty(p)) == null) {
                property = model.createProperty(p);

            }
            if (dataO.length != 2) res.addProperty(property, o);
            else res.addProperty(property, model.getResource(dataO[1]));
        }
    }

    public void CreateModel() {
        addStatement(LeoMozartURI, PersonURI + "Nom", LeoMozartNom);

        addStatement(ConsWebURI, PersonURI + "Nom", ConsWebNom);

        addStatement(MozartURI, PersonURI + "Décédé_à", MozartPOD);
        addStatement(MozartURI, PersonURI + "Décédé_le", MozartDOD);
        addStatement(MozartURI, PersonURI + "Mari_De", "URI|" + ConsWebURI);
        addStatement(MozartURI, PersonURI + "Fils_De", "URI|" + LeoMozartURI);
        addStatement(MozartURI, PersonURI + "Née_le", DOBMozart);
        addStatement(MozartURI, PersonURI + "Née_à", BirthPlaceMozard);
        addStatement(MozartURI, PersonURI + "Nom", MozartNom);

        addStatement(ClaudioURI, PersonURI + "Nom", ClaudioNom);

        addStatement(PartieURI, SymphonieURI + "Partie_4", Partie4);
        addStatement(PartieURI, SymphonieURI + "Partie_3", Partie3);
        addStatement(PartieURI, SymphonieURI + "Partie_2", Partie2);
        addStatement(PartieURI, SymphonieURI + "Partie_1", Partie1);

        addStatement(JupiterURI, SymphonieURI + "Partie_de", "URI|" + PartieURI);
        addStatement(JupiterURI, SymphonieURI + "Enregistrer_sous_direction_de", "URI|" + ClaudioURI);
        addStatement(JupiterURI, SymphonieURI + "Orchestre_Symphonique", JupiterOrchestre);
        addStatement(JupiterURI, SymphonieURI + "Année_Enregistrement", JupiterAnneeEnregistrement);
        addStatement(JupiterURI, SymphonieURI + "Type", JupiterType);
        addStatement(JupiterURI, SymphonieURI + "Propriété", JupiterPropritete);
        addStatement(JupiterURI, SymphonieURI + "Titre", JupiterTitre);
        addStatement(JupiterURI, SymphonieURI + "compositeur", "URI|" + MozartURI);
    }

    public void writeModel(String output) throws IOException {
        writeModel(output, "");
    }

    public void writeModel(String output, String format) throws IOException {
        switch (format) {
            case "":
                FileWriter RDFXMLOUT = new FileWriter("src/main/resources/" + output + ".xml");
                try {
                    model.write(RDFXMLOUT);
                } finally {
                    try {
                        RDFXMLOUT.close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
                break;
            case "N-TRIPLE":
                FileWriter NTRIPLETOUT = new FileWriter("src/main/resources/" + output + ".nt");
                try {
                    model.write(NTRIPLETOUT, "N-TRIPLE");
                } finally {
                    try {
                        NTRIPLETOUT.close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
                break;
            case "TURTLE":
                FileWriter TURTLEOUT = new FileWriter("src/main/resources/" + output + ".ttl");
                try {
                    model.write(TURTLEOUT, "TURTLE");
                } finally {
                    try {
                        TURTLEOUT.close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
                break;
            default:
                System.err.println("Voici les seuls formats disponible : N-TRIPLE, TURTLE, méthode sans argument pour RDF/XML");
        }
    }

    public static void main(String[] args) throws IOException {
        List<Pair<String, String>> prefix = new ArrayList<>();
        prefix.add(new Pair<>("person", "http://personne/rdf/"));
        prefix.add(new Pair<>("symphonie", "http://jupiter/rdf/"));
        WebSemantique ws = new WebSemantique(ModelFactory.createDefaultModel(), prefix);
        ws.CreateModel();
        ws.writeModel("test");
        ws.writeModel("test", "N-TRIPLE");
        ws.writeModel("test", "TURTLE");
    }
}
