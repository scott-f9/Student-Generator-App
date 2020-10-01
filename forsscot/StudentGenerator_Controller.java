/*
    StudentGenerator_Controller.java
    Author: Scott Forsyth
    Date: April 6th, 2020

    Description
    This is the controller for the StudentGenerator program. It can create
    a randomly generated list of students and filter them based on the
    number of students to be displayed on the GUI and their grade point average
 */
package forsscot;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import models.Student;
import models.StudentStatus;

/**
 * FXML Controller class
 *
 * @author Scott Forsyth
 */
public class StudentGenerator_Controller implements Initializable {

    @FXML
    private ListView<Student> lstRoster;
    @FXML
    private Slider sldNum;
    @FXML
    private Label lblGpa;
    @FXML
    private Slider sldGpa;
    @FXML
    private Button btnCreate;
    @FXML
    private Label lblStudentNum;
    @FXML
    private Button btnShowStudents;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnGreaterGpa;
    private Menu mnuStatus;
    private File activeFile = null;
    ObservableList<Student> activeOl = null;
    ObservableList<Student> originalList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeOl = FXCollections.observableArrayList();
        lstRoster.setItems(activeOl);

        lblStudentNum.textProperty().bind(Bindings.format("%.0f",
                sldNum.valueProperty()));
        lblGpa.textProperty().bind(Bindings.format("%.1f", sldGpa.valueProperty()));
    }

    
    /**
     * Creates a CSV file with randomly generated Student attributes from a list
     * of student names
     * 
     * @param event the create CSV button is pressed
     */
    @FXML
    private void createCSV(ActionEvent event) {
        PrintWriter fileOut = null;
        try {
            StudentStatus randomStatus = null;
            activeFile = new File("students.csv");
            File names = new File("names_raw.txt");
            fileOut = new PrintWriter(new FileWriter(activeFile, true));
            Scanner reader = new Scanner(names);

            while (reader.hasNextLine()) {
                // creating a random id number
                String id = String.format("%9d", ((int) (Math.random() * 900000000) + 100000000));
                //getting names from the names_txt.txt file
                String fullName = reader.nextLine().trim();
                String[] splitName = fullName.split(" ");
                String firstName = splitName[0];
                String lastName = splitName[1];
                // creating a random gpa
                double gpa = Double.parseDouble(String.format("%1.1f", (Math.random() * 4.0)));
                // randomly assigning a status choice
                int standingChoice = (int) (Math.random() * 6);
                switch (standingChoice) {
                    case 0:
                        randomStatus = StudentStatus.GOOD_STANDING;
                        break;
                    case 1:
                        randomStatus = StudentStatus.ACADEMIC_PROBATION;
                        break;
                    case 2:
                        randomStatus = StudentStatus.ACADEMIC_SUSPENSION;
                        break;
                    case 3:
                        randomStatus = StudentStatus.BEHAVIOUR_SUSPENSION;
                        break;
                    case 4:
                        randomStatus = StudentStatus.EXPELLED;
                        break;
                    case 5:
                        randomStatus = StudentStatus.GRADUATED;
                        break;
                }
                // creating a Student with values
                Student student = new Student(id, firstName, lastName, gpa, randomStatus);
                fileOut.println(student.toString());
            }
            reader.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CSV creation error");
            alert.setHeaderText("CSV file could not be created");
            alert.setContentText("Please use a valid file to create CSV");
            alert.showAndWait();
        } finally {
            fileOut.close();
        }
    }

    
    /**
     * Lets the user select a roster of students to input to the GUI
     * 
     * @param event the open roster button is pressed
     */
    @FXML
    private void openRoster(ActionEvent event) {
        FileChooser openChooser = new FileChooser();
        openChooser.setInitialDirectory(Paths.get(".").toFile());
        ExtensionFilter filterTxt = new ExtensionFilter("Text and CSV Files", "*.txt", "*.csv");
        openChooser.getExtensionFilters().addAll(filterTxt);
        Window w = null;
        try {
            activeFile = openChooser.showOpenDialog(w);
            Scanner sc = new Scanner(activeFile);
            while (sc.hasNextLine()) {
                String tempdata = sc.nextLine().trim();
                Student newStudent = Student.parse(tempdata, ",");
                activeOl.add(newStudent);
            }
            sc.close();
            
                for (Student currentStudent : activeOl) {
                    originalList.add(new Student(currentStudent.getStudentID(),
                            currentStudent.getFirstName(),
                            currentStudent.getLastName(), currentStudent.getGpa(),
                            currentStudent.getStatus()));
                }            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Roster import error");
            alert.setHeaderText("No valid roster file was selected");
            alert.setContentText
           ("Please selected a valid file to create roster");
            alert.showAndWait();
        }
    }

    
    /**
     * Displays the roster of students on the GUI
     * 
     * @param event the display button is pressed
     */
    @FXML
    private void display(ActionEvent event) {       
        activeOl.clear();       
        int listSize = Integer.parseInt(lblStudentNum.getText());
        double gpaVal = Double.parseDouble(lblGpa.getText());       
        for (Student currentStudent : originalList){
            if (activeOl.size() > (listSize - 1)){
                break;
            }
            if (currentStudent.getGpa() > gpaVal){
                activeOl.add(currentStudent);
            }
        }    
    }

    
    /**
     * Filters the roster to display students only with a GPA greater than the
     * selected number on the slider
     * 
     * @param event the greater than GPA button is pressed
     */
    @FXML
    private void greaterThanGpa(ActionEvent event) {     
        activeOl.clear();
        double gpaVal = Double.parseDouble(lblGpa.getText());
        int listSize = Integer.parseInt(lblStudentNum.getText());        
        for (Student currentStudent : originalList){
            if (activeOl.size() > (listSize - 1)){
                break;
            }
            if (currentStudent.getGpa() > gpaVal){
                activeOl.add(currentStudent);
            }
        }
    }

    
    /**
     * Calls the saveAsRoster method
     * 
     * @param event the save roster button is selected
     */
    @FXML
    private void saveRoster(ActionEvent event) {
        saveAsRoster();
    }
    
    /**
     *  Saves the filtered list of students to a CSV file
     */
    private void saveAsRoster() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        fc.setTitle("Save As");
        ExtensionFilter csvFilter = new ExtensionFilter("CSV File", "*.csv");
        fc.getExtensionFilters().add(csvFilter);
        Window w = null;
        try {
            activeFile = fc.showSaveDialog(w);
            PrintWriter pw = new PrintWriter(activeFile);
            for (Student currentStudent : activeOl) {
                String studentString = currentStudent.toString();

                pw.print(studentString);
                pw.print("\n");
            }
            pw.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving file");
            alert.setHeaderText("No file was saved");
            alert.setContentText("Please check input and try again");
            alert.showAndWait();
        }

    }
   
    /**
     * Displays an alert box asking if the user wants to exit the program.
     * If yes is selected, the program exits.
     * 
     * @param event the exit button is pressed
     */
    @FXML
    private void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit Program");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES)
            System.exit(0);
    }

    /**
     * Clears the roster of students displayed on the GUI
     * 
     * @param event the clear button is selected
     */
    @FXML
    private void clear(ActionEvent event) {
        activeFile = null;
        lstRoster.getItems().clear();
    }

    
    /**
     * Opens an external readme.txt file displaying details about the program
     * 
     * @param event the about button is selected
     * @throws IOException if the file cannot be opened
     */
    @FXML
    private void aboutHandler(ActionEvent event) throws IOException {
        File readme = new File("readme.txt");
        Desktop.getDesktop().edit(readme);
    }
}
