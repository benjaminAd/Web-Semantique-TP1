import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

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


    public static void main(String[] args) {
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
        model.write(System.out);
        /*
        tutorial n°03
        // some definitions
        String personURI = "http://somewhere/JohnSmith";
        String givenName = "John";
        String familyName = "Smith";
        String fullName = givenName + " " + familyName;
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        //   and add the properties cascading style
        Resource johnSmith
                = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

        // list the statements in the graph
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();         // get next statement
            Resource subject = stmt.getSubject();   // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject();    // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }*/
    }
}
