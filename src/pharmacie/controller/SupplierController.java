package pharmacie.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.FournisseurDAO;
import pharmacie.model.Fournisseur;
import pharmacie.view.SupplierView;

import java.util.List;

public class SupplierController {
    private SupplierView view;
    private FournisseurDAO fournisseurDAO;

    public SupplierController(SupplierView view) {
        this.view = view;
        this.fournisseurDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getFournisseurDAO();
    }

    public void loadData(TableView<Fournisseur> table) {
        List<Fournisseur> list = fournisseurDAO.findAll();
        table.setItems(FXCollections.observableArrayList(list));
    }

    public void addSupplier(String nom, String email, String phone, String address) {
        if (nom == null || nom.trim().isEmpty()) {
            showAlert("Erreur", "Le nom est obligatoire.");
            return;
        }

        Fournisseur f = new Fournisseur();
        f.setNom(nom);
        f.setEmail(email);
        f.setTelephone(phone);
        f.setAdresse(address);
        // Default note
        f.setNotePerformance(0);

        try {
            fournisseurDAO.save(f);
            view.refreshTable();
            showAlert("Succès", "Fournisseur ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le fournisseur: " + e.getMessage());
        }
    }

    public void deleteSupplier(Fournisseur f) {
        if (f == null) {
            showAlert("Avertissement", "Aucun fournisseur sélectionné.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le fournisseur ?");
        confirm.setContentText("Cette action est irréversible.");

        if (confirm.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                fournisseurDAO.delete(f.getId());
                view.refreshTable();
                showAlert("Succès", "Fournisseur supprimé.");
            } catch (Exception e) {
                // Check if it's an integrity violation (MySQL error code 1451 or 23000)
                // Since generic Exception catches it, we check the message or cause
                String msg = e.getMessage();
                if (msg != null && (msg.contains("constraint") || msg.contains("foreign key"))) {
                    showAlert("Erreur de suppression",
                            "Impossible de supprimer ce fournisseur car il possède des commandes liées.\n" +
                                    "Veuillez d'abord supprimer ses commandes dans l'historique.");
                } else {
                    e.printStackTrace();
                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
                }
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
