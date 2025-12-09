package GUI;

import javax.swing.*;

import GUI.Dialog.*;

public class EntryDialogTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EntryDialog dialog = new EntryDialog(null);
            dialog.setVisible(true);
        });
    }
}