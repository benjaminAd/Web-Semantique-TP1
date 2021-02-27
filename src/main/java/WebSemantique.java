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

    public void addStatement(String s, String p, String o) {
        Resource subject = this.model.createResource(s);
        Property predicate = this.model.createProperty(p);
        RDFNode object = this.model.createResource(o);
        Statement stmt = this.model.createStatement(subject, predicate, object);
        model.add(stmt);
    }

    public void CreateModel() {
        addStatement(LeoMozartURI, PersonURI + "Nom", LeoMozartNom);

        addStatement(ConsWebURI, PersonURI + "Nom", ConsWebNom);

        addStatement(MozartURI, PersonURI + "Décédé_à", MozartPOD);
        addStatement(MozartURI, PersonURI + "Décédé_le", MozartDOD);
        addStatement(MozartURI, PersonURI + "Mari_De", ConsWebURI);
        addStatement(MozartURI, PersonURI + "Fils_De", LeoMozartURI);
        addStatement(MozartURI, PersonURI + "Née_le", DOBMozart);
        addStatement(MozartURI, PersonURI + "Née_à", BirthPlaceMozard);
        addStatement(MozartURI, PersonURI + "Nom", MozartNom);

        addStatement(ClaudioURI, PersonURI + "Nom", ClaudioNom);

        addStatement(PartieURI, SymphonieURI + "Partie_4", Partie4);
        addStatement(PartieURI, SymphonieURI + "Partie_3", Partie3);
        addStatement(PartieURI, SymphonieURI + "Partie_2", Partie2);
        addStatement(PartieURI, SymphonieURI + "Partie_1", Partie1);

        addStatement(JupiterURI, SymphonieURI + "Partie_de", PartieURI);
        addStatement(JupiterURI, SymphonieURI + "Enregistrer_sous_direction_de", ClaudioURI);
        addStatement(JupiterURI, SymphonieURI + "Orchestre_Symphonique", JupiterOrchestre);
        addStatement(JupiterURI, SymphonieURI + "Année_Enregistrement", JupiterAnneeEnregistrement);
        addStatement(JupiterURI, SymphonieURI + "Type", JupiterType);
        addStatement(JupiterURI, SymphonieURI + "Propriété", JupiterPropritete);
        addStatement(JupiterURI, SymphonieURI + "Titre", JupiterTitre);
        addStatement(JupiterURI, SymphonieURI + "compositeur", MozartURI);
    }

    public void writeModel() throws IOException {
        writeModel("");
    }

    public void writeModel(String format) throws IOException {
        switch (format) {
            case "":
                FileWriter RDFXMLOUT = new FileWriter("src/main/resources/Text.xml");
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
                FileWriter NTRIPLETOUT = new FileWriter("src/main/resources/Text.nt");
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
                FileWriter TURTLEOUT = new FileWriter("src/main/resources/Text.ttl");
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
        Model model = ModelFactory.createDefaultModel();

        model.setNsPrefix("person", PersonURI);
        model.setNsPrefix("symphonie", SymphonieURI);

        Property Nom = model.createProperty(PersonURI, "Nom");
        Property FilsDe = model.createProperty(PersonURI, "Fils_De");
        Property MariDe = model.createProperty(PersonURI, "Mari_De");
        Property NeeLe = model.createProperty(PersonURI, "Née_le");
        Property NeeA = model.createProperty(PersonURI, "Née_à");
        Property DCDLe = model.createProperty(PersonURI, "Décédé_le");
        Property DCDA = model.createProperty(PersonURI, "Décédé_à");

        Property PropertyPart1 = model.createProperty(SymphonieURI, "Partie_1");
        Property PropertyPart2 = model.createProperty(SymphonieURI, "Partie_2");
        Property PropertyPart3 = model.createProperty(SymphonieURI, "Partie_3");
        Property PropertyPart4 = model.createProperty(SymphonieURI, "Partie_4");

        Property Compositeur = model.createProperty(SymphonieURI, "compositeur");
        Property Titre = model.createProperty(SymphonieURI, "Titre");
        Property Propriete = model.createProperty(SymphonieURI, "Propriété");
        Property Type = model.createProperty(SymphonieURI, "Type");
        Property AnneeEnregistrement = model.createProperty(SymphonieURI, "Année_Enregistrement");
        Property OrchestreSymphonique = model.createProperty(SymphonieURI, "Orchestre_Symphonique");
        Property EnregistrerSous = model.createProperty(SymphonieURI, "Enregistrer_sous_direction_de");
        Property PartieDe = model.createProperty(SymphonieURI, "Partie_de");

        Resource LeoMozart = model.createResource(LeoMozartURI).addProperty(Nom, LeoMozartNom);
        Resource ConsWeber = model.createResource(ConsWebURI).addProperty(Nom, ConsWebNom);
        Resource WolfgangMozart = model.createResource(MozartURI).addProperty(DCDA, MozartPOD).addProperty(DCDLe, MozartDOD).addProperty(MariDe, ConsWeber).addProperty(FilsDe, LeoMozart).addProperty(NeeLe, DOBMozart).addProperty(NeeA, BirthPlaceMozard).addProperty(Nom, MozartNom);
        Resource ClaudioAbbado = model.createResource(ClaudioURI).addProperty(Nom, ClaudioNom);
        Resource Partie = model.createResource(PartieURI).addProperty(PropertyPart4, Partie4).addProperty(PropertyPart3, Partie3).addProperty(PropertyPart2, Partie2).addProperty(PropertyPart1, Partie1);
        Resource Jupiter = model.createResource(JupiterURI).addProperty(PartieDe, Partie).addProperty(EnregistrerSous, ClaudioAbbado).addProperty(OrchestreSymphonique, JupiterOrchestre).addProperty(AnneeEnregistrement, JupiterAnneeEnregistrement).addProperty(Type, JupiterType).addProperty(Propriete, JupiterPropritete).addProperty(Titre, JupiterTitre).addProperty(Compositeur, WolfgangMozart);
        //Fichier XML
        FileWriter RDFXMLOUT = new FileWriter("src/main/resources/Text.xml");
        try {
            model.write(RDFXMLOUT);
        } finally {
            try {
                RDFXMLOUT.close();
            } catch (IOException e) {
                //ignore
            }
        }

        //Fichier N-TRIPLET
        FileWriter NTRIPLETOUT = new FileWriter("src/main/resources/Text.nt");
        try {
            model.write(NTRIPLETOUT, "N-TRIPLE");
        } finally {
            try {
                NTRIPLETOUT.close();
            } catch (IOException e) {
                //ignore
            }
        }

        //Fichier TURTLE
        FileWriter TURTLEOUT = new FileWriter("src/main/resources/Text.ttl");
        try {
            model.write(TURTLEOUT, "TURTLE");
        } finally {
            try {
                TURTLEOUT.close();
            } catch (IOException e) {
                //ignore
            }
        }
//        DatasetGraph graphe =DatasetGraphFactory.create();
//        graphe.

        //Fichier JSON
        /*
        Ne reconnait pas le RDF/JSON
        FileWriter JSONOUT = new FileWriter("Text.rj");
        try {
            model.write(JSONOUT, "RDF/JSON");
        } finally {
            try {
                JSONOUT.close();
            } catch (IOException e) {
                //ignore
            }
        }*/
    }
}
