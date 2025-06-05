import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;



    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.pg = new ProgressBar();
        this.panelCentral = new BorderPane();
        this.modeAccueil();
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre(){
            BorderPane borderTitre = new BorderPane();
            borderTitre.setStyle("-fx-background-color:rgb(169, 179, 238);");

            Label nomJ = new Label("Jeu du pendu");
            nomJ.setFont(Font.font("System", FontWeight.BOLD, 40));

            HBox hboxBouton = new HBox();
            hboxBouton.setSpacing(20);
        
            ImageView imgHome = new ImageView("./../img/home.png");
            ImageView imginfo = new ImageView("./../img/info.png");
            ImageView imgpara = new ImageView("./../img/parametres.png");
            imgHome.setFitHeight(20);
            imgHome.setFitWidth(20);
            imgpara.setFitHeight(20);
            imgpara.setFitWidth(20);
            imginfo.setFitHeight(20);
            imginfo.setFitWidth(20);

            this.boutonMaison = new Button();
            this.boutonMaison.setGraphic(imgHome);
            this.boutonMaison.setPrefSize(55, 55);
            RetourAccueil retourAcceuil = new RetourAccueil(modelePendu, this);
            this.boutonMaison.setOnAction(retourAcceuil);

            
            this.boutonParametres = new Button();
            this.boutonParametres.setGraphic(imgpara);
            this.boutonParametres.setPrefSize(55, 55);
            ControleurParametre conPara = new ControleurParametre(modelePendu, this);
            this.boutonParametres.setOnAction(conPara);
   

            Button info = new Button();  
            info.setGraphic(imginfo);
            info.setPrefSize(55, 55);
            ControleurInfos conInf = new ControleurInfos(this);
            info.setOnAction(conInf);
         
      
            hboxBouton.getChildren().addAll(this.boutonMaison, this.boutonParametres, info);
            borderTitre.setLeft(nomJ);
            borderTitre.setRight(hboxBouton);
        return borderTitre;   
    }

    /**
      * @return le panel du chronomètre
      */
     private TitledPane leChrono(){
        
         TitledPane res = new TitledPane();
         return res;
     }

     /**
      * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
      *         de progression et le clavier
      */
     private BorderPane fenetreJeu(){
        BorderPane borderPaneJeu = new BorderPane();
        VBox vboxGauche = new VBox();
        vboxGauche.setAlignment(Pos.CENTER);
        VBox vboxDroite = new VBox();
        

        this.dessin = new ImageView("../img/pendu0.png");

        this.motCrypte = new Text(modelePendu.getMotCrypte());
        this.motCrypte.setFont(Font.font("System", FontWeight.BOLD, 20));
        

        this.leNiveau = new Text(String.valueOf("Niveau : " + modelePendu.getNiveau()));
        this.leNiveau.setFont(Font.font("System", FontWeight.BOLD, 18));

        Button boutonNewMot = new Button("Nouveau mot");
        ControleurNouveauMot conNewMot = new ControleurNouveauMot(modelePendu, this);
        boutonNewMot.setOnAction(conNewMot);


        Label vide1 = new Label(" "); 

        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurClavier(this.modelePendu, this), 8);
        
        
    
        vboxGauche.getChildren().addAll(this.motCrypte, this.dessin, this.pg, clavier);
        vboxDroite.getChildren().addAll(this.leNiveau, boutonNewMot);
        borderPaneJeu.setLeft(vboxGauche);
        borderPaneJeu.setRight(vboxDroite);


        return borderPaneJeu;
     }

     /**
      * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
     private VBox fenetreAccueil(){

        VBox vBoxAccueil = new VBox();
        vBoxAccueil.setPadding(new Insets(20));

            this.bJouer = new Button("Lancer pertie");
            ControleurLancerPartie conLancerPartie = new ControleurLancerPartie(modelePendu, this);
            this.bJouer.setOnAction(conLancerPartie);

            this.boutonMaison = new Button();
            RetourAccueil retourAccueil = new RetourAccueil(modelePendu, this);
            this.boutonMaison.setOnAction(retourAccueil);

            this.boutonParametres = new Button();
            ControleurParametre conPara = new ControleurParametre(modelePendu, this);
            this.boutonParametres.setOnAction(conPara);

            TitledPane choixNiveau = new TitledPane();
            choixNiveau.setPadding(new Insets(20));
            choixNiveau.setText("Niveau de difficulté");
            
            VBox vboxNiveau = new VBox();
            RadioButton facile = new RadioButton("facile");
            RadioButton moyen = new RadioButton("moyen");
            RadioButton difficile = new RadioButton("difficile");
            RadioButton expert = new RadioButton("expert");

            facile.setOnAction(new ControleurNiveau(modelePendu));
            moyen.setOnAction(new ControleurNiveau(modelePendu));
            difficile.setOnAction(new ControleurNiveau(modelePendu));
            expert.setOnAction(new ControleurNiveau(modelePendu));

            vboxNiveau.getChildren().addAll(facile, moyen, difficile, expert);
            choixNiveau.setContent(vboxNiveau);

        vBoxAccueil.getChildren().addAll(this.bJouer, choixNiveau);


    return vBoxAccueil;

        
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        
        this.panelCentral.setCenter(fenetreAccueil());
        modelePendu.setMotATrouver();
        majAffichage();
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(fenetreJeu());
    }
    
    public void modeParametres(){
       
    
    }

    /** lance une partie */
    public void lancePartie(){
        modelePendu.setMotATrouver();
        modeJeu();
        majAffichage();
        
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
       
        
        this.pg.setProgress(1.0 - ((double) modelePendu.getNbErreursRestants() / modelePendu.getNbErreursMax()));
        
        if(!(this.motCrypte == null)){
            this.motCrypte.setText(modelePendu.getMotCrypte());
        }
        
        if(this.lesImages != null && this.dessin != null){
            this.dessin.setImage(this.lesImages.get(10 - this.modelePendu.getNbErreursRestants()));
        }
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "voici les règles du jeu");
        alert.setTitle("Règle du jeu");
        alert.setContentText("Le nut est de trouver le mot.\nAu bout de 11 erreurs vous avez perdu.\nSelectionnez les lettres pour découvrir le mot \n       BONNE CHANCE !!! ");
        alert.showAndWait();
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "vous avez gagner");    
        alert.setTitle("Vous avez gagné !!!!"); 
        alert.setHeaderText(null);
        alert.setContentText("Bien joué vous avez trouvé le mot");
        alert.showAndWait();   
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION," vous avez perdu ");
        alert.setTitle("Vous avez Perdu");
        alert.setHeaderText(null);
        alert.setContentText("Dommage, vous avez perdu ! Le mot était : " + modelePendu.getMotATrouve());
        alert.showAndWait();
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
