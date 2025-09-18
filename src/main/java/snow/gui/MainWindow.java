package snow.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import snow.model.Snow;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Snow snow;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image snowImage = new Image(this.getClass().getResourceAsStream("/images/DaSnow.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Snow instance */
    public void setSnow(Snow d) {
        snow = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Snow's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = snow.getResponse(input);
        String commandType = snow.getCommandType();

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        // Check if response is an error
        if (response.startsWith("Error:")) {
            dialogContainer.getChildren().add(DialogBox.getErrorDialog(response, snowImage));
        } else {
            dialogContainer.getChildren().add(DialogBox.getSnowDialog(response, snowImage, commandType));
        }

        userInput.clear();

        // Check if the command indicates we should exit
        if (snow.shouldExit()) {
            Platform.exit();
        }
    }
}

