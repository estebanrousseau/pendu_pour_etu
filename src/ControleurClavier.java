import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurClavier implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurClavier(MotMystere modelePendu, Pendu vuePendu){
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
   
        Button bouton = (Button) actionEvent.getSource();
        String lettre = bouton.getText();
        modelePendu.essaiLettre(lettre.charAt(0));
        
        vuePendu.majAffichage();
        bouton.setDisable(true);

      
        if (modelePendu.gagne()) {
            this.vuePendu.popUpMessageGagne();
        } else if (modelePendu.perdu()) {
            this.vuePendu.popUpMessagePerdu();
   
        }

    }
}
