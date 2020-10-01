/*
    Name:  Scott Forsyth
    Assignment:  Assignment 5
    Program: Internet Communications Technology
    Date:  April 6th, 2020
    
    Description:
    This class launches the GUI roster application, which allows users to
    input, filter, and save a student roster file.
*/

package forsscot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Scott Forsyth
 */
public class StudentGenerator_Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StudentGenerator_View.fxml"));
        stage.setTitle("Student and Roster Generator");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
