package com.example.sistemaingressos.telas;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class AddSalaController {

    @FXML
    private TextField idInput;

    @FXML
    private Spinner<Integer> quantidadeInput;


    @FXML
    private void cancelar() {
        // Lógica para cancelar e fechar o diálogo
    }
}
