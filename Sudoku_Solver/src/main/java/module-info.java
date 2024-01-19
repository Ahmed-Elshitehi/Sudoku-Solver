module org.example.sudoku_solver {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sudoku_solver to javafx.fxml;
    exports org.example.sudoku_solver;
}