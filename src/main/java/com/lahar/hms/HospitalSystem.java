package com.lahar.hms;

import java.util.ArrayList;

// Abstract Class
abstract class Hospital {

    String id;

    Hospital(String id) {
        this.id = id;
    }

    abstract int calculateBill(int days);
}

// General Ward Class
class GeneralWard extends Hospital {

    GeneralWard(String id) {
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
public class HospitalSystem {

    ArrayList<AvailableBeds> beds = new ArrayList<>();

    public HospitalSystem(int size) {

        for (int i = 1; i <= size; i++) {

            beds.add(new AvailableBeds(i));
        }
    }

    // Admit Patient
    public String admitPatient(String id, String type) {

        Hospital patient;

        if (type.equalsIgnoreCase("ICU")) {

            patient = new ICU(id);

        } else {

            patient = new GeneralWard(id);
        }

        for (AvailableBeds bed : beds) {

            if (bed.isEmpty()) {

                bed.hospital = patient;

                return "Patient " + id + " admitted in " + type;
            }
        }

        return "No beds available";
    }

    // Discharge Patient
    public String dischargePatient(String id) {

        for (AvailableBeds bed : beds) {

            if (!bed.isEmpty() &&
                    bed.hospital.id.equals(id)) {

                bed.hospital = null;

                return "Patient " + id + " discharged";
            }
        }

        return "Patient not found";
    }

    // Calculate Bill
    public String calculateBill(String id, int days) {

        for (AvailableBeds bed : beds) {

            if (!bed.isEmpty() &&
                    bed.hospital.id.equals(id)) {

                int bill =
                        bed.hospital.calculateBill(days);

                return "Bill for Patient " +
                        id +
                        " = ₹" +
                        bill;
            }
        }

        return "Patient not found";
    }

    // Display Beds
    public String displayBeds() {

        StringBuilder result = new StringBuilder();

        result.append("\n----- Bed Status -----\n");

        for (AvailableBeds bed : beds) {

            if (bed.isEmpty()) {

                result.append(
                        "Bed ")
                        .append(bed.bedNumber)
                        .append(" is Empty\n");

            } else {

                result.append(
                        "Bed ")
                        .append(bed.bedNumber)
                        .append(" occupied by Patient ID: ")
                        .append(bed.hospital.id)
                        .append("\n");
            }
        }

        return result.toString();
    }
}