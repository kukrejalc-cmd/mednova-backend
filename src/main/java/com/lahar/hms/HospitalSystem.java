import java.util.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Abstract Class
abstract class Hospital {

    String id;

    Hospital(String id) {
        this.id = id;
    }

    abstract int calculateBill(int days);
}

// General Ward Class
class generalWard extends Hospital {

    generalWard(String id) {
        super(id);
    }

    @Override
    int calculateBill(int days) {
        return days * 1000;
    }
}

// ICU Class
class ICU extends Hospital {

    ICU(String id) {
        super(id);
    }

    @Override
    int calculateBill(int days) {
        return days * 5000;
    }
}

// Available Beds Class
class AvailableBeds {

    int bedNumber;

    Hospital hospital;

    AvailableBeds(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    boolean isEmpty() {
        return hospital == null;
    }
}

// Hospital Management Class
class HospitalManagement {

    ArrayList<AvailableBeds> beds = new ArrayList<>();

    HospitalManagement(int size) {

        for (int i = 1; i <= size; i++) {

            beds.add(new AvailableBeds(i));
        }
    }

    void admitPatient(Hospital hospital) {

        for (AvailableBeds bed : beds) {

            if (bed.isEmpty()) {

                bed.hospital = hospital;

                return;
            }
        }
    }

    void dischargePatient(String id) {

        for (AvailableBeds bed : beds) {

            if (!bed.isEmpty() &&
                    bed.hospital.id.equals(id)) {

                bed.hospital = null;

                return;
            }
        }
    }
}

// Main JavaFX Class
public class HospitalSystem extends Application {

    HospitalManagement hm = new HospitalManagement(5);

    @Override
    public void start(Stage stage) {

        // Title
        Label title = new Label("Hospital Management System");

        title.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        // Patient ID
        TextField patientId = new TextField();

        patientId.setPromptText("Enter Patient ID");

        // Ward Type
        ComboBox<String> wardType = new ComboBox<>();

        wardType.getItems().addAll(
                "General Ward",
                "ICU"
        );

        wardType.setPromptText("Select Ward Type");

        // Days Field
        TextField daysField = new TextField();

        daysField.setPromptText("Enter Number of Days");

        // Buttons
        Button admitBtn = new Button("Admit Patient");

        Button dischargeBtn =
                new Button("Discharge Patient");

        Button billBtn =
                new Button("Calculate Bill");

        Button displayBtn =
                new Button("Display Beds");

        // Output Area
        TextArea output = new TextArea();

        output.setPrefHeight(250);

        // Admit Patient
        admitBtn.setOnAction(e -> {

            String id = patientId.getText();

            String type = wardType.getValue();

            if (id.isEmpty() || type == null) {

                output.appendText(
                        "Enter Patient ID and Ward Type!\n"
                );

                return;
            }

            Hospital patient;

            if (type.equals("ICU")) {

                patient = new ICU(id);

            } else {

                patient = new generalWard(id);
            }

            hm.admitPatient(patient);

            output.appendText(
                    "Patient " + id +
                            " admitted in " +
                            type + "\n"
            );
        });

        // Discharge Patient
        dischargeBtn.setOnAction(e -> {

            String id = patientId.getText();

            if (id.isEmpty()) {

                output.appendText(
                        "Enter Patient ID!\n"
                );

                return;
            }

            hm.dischargePatient(id);

            output.appendText(
                    "Patient " + id +
                            " discharged\n"
            );
        });

        // Calculate Bill
        billBtn.setOnAction(e -> {

            String id = patientId.getText();

            int days;

            try {

                days = Integer.parseInt(
                        daysField.getText()
                );

            } catch (Exception ex) {

                output.appendText(
                        "Enter valid number of days!\n"
                );

                return;
            }

            for (AvailableBeds bed : hm.beds) {

                if (!bed.isEmpty() &&
                        bed.hospital.id.equals(id)) {

                    int bill =
                            bed.hospital.calculateBill(days);

                    output.appendText(
                            "Bill for Patient " +
                                    id +
                                    " = ₹" +
                                    bill + "\n"
                    );

                    return;
                }
            }

            output.appendText(
                    "Patient not found!\n"
            );
        });

        // Display Beds
        displayBtn.setOnAction(e -> {

            output.appendText(
                    "\n----- Bed Status -----\n"
            );

            for (AvailableBeds bed : hm.beds) {

                if (bed.isEmpty()) {

                    output.appendText(
                            "Bed " +
                                    bed.bedNumber +
                                    " is Empty\n"
                    );

                } else {

                    output.appendText(
                            "Bed " +
                                    bed.bedNumber +
                                    " occupied by Patient ID: " +
                                    bed.hospital.id + "\n"
                    );
                }
            }

            output.appendText("\n");
        });

        // Layout
        VBox root = new VBox(15);

        root.setPadding(new Insets(20));

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                title,
                patientId,
                wardType,
                daysField,
                admitBtn,
                dischargeBtn,
                billBtn,
                displayBtn,
                output
        );

        // Scene
        Scene scene = new Scene(root, 500, 650);

        // Stage
        stage.setTitle("Hospital Management UI");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}