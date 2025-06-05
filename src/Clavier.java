import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle ;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches, int tailleLigne) {
        this.clavier = new ArrayList<>();
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setPrefColumns(tailleLigne);

        for(char carac: touches.toCharArray()){
            Button boyton = new Button(String.valueOf(carac));
            boyton.setOnAction(actionTouches);
            boyton.setShape(new Circle(1.5));
            this.clavier.add(boyton);
            this.getChildren().add(boyton);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
     
        for(Button b : this.clavier){
            b.setDisable(touchesDesactivees.contains(b.getText()));;
            
        }
    
        
    }
}
