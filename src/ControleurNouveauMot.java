import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton Accueil
 */
public class ControleurNouveauMot implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurNouveauMot(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }


    /**
     * L'action consiste à retourner sur la page d'accueil. Il faut vérifier qu'il n'y avait pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {

        Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait(); // on lance la fenêtre popup et on attends la réponse
        if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            System.out.println("Ok !");
            modelePendu.setMotATrouver();;
            vuePendu.modeJeu();
            vuePendu.majAffichage();
        }
        else{
            System.out.println("D'ac !");
        }
    }    
    

}