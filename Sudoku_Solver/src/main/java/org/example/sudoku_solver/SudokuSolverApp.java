package org.example.sudoku_solver;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SudokuSolverApp extends Application {

    private final int[][] sudokuGrid = new int[9][9];
    private final TextField[][] textFields = new TextField[9][9];
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Solver");

        GridPane gridPane = createGrid();

        Button solveButton = new Button("Solve");
        Button resetButton = new Button("reset");

        solveButton.setPrefWidth(125);
        resetButton.setPrefWidth(125);

        solveButton.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                System.out.println("Solve");
                solveSudoku();
            }
        });

        resetButton.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                System.out.println("reset");
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sudokuGrid[i][j] = 0;
                    }
                }
                resettextFields();
            }
        });

        HBox hBox = new HBox(3);
        hBox.getChildren().add(solveButton);
        hBox.getChildren().add(new Pane());
        hBox.getChildren().add(resetButton);
        hBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(4);
        root.getChildren().add(new Label());
        root.getChildren().add(gridPane);
        root.getChildren().add(new Label());
        root.getChildren().add(hBox);

        Scene scene = new Scene(root, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void solveSudoku() {
        resetGrid();
        if (!solve()) {
            System.out.println("No valid solution");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No valid solution");
            alert.setContentText("No valid solution for this Puzzle");
        } else {
            resettextFields();
        }
    }

    private boolean solve() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuGrid[i][j] == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (isValid(i, j, k)) {
                            sudokuGrid[i][j] = k;
                            if (solve()) {
                                return true;
                            } else {
                                sudokuGrid[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    boolean isValid(int row, int col, int c) {
        for (int i = 0; i < 9; i++) {
            if (sudokuGrid[i][col] == c) return false;
            if (sudokuGrid[row][i] == c) return false;
            if (sudokuGrid[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false;
        }
        return true;
    }
    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuGrid[row][col] = 0;
                TextField textField = new TextField("");
                textFields[row][col] = textField;
                textField.setPrefSize(30, 30);
                textField.setAlignment(Pos.CENTER);
                gridPane.add(textField, col, row);
            }
        }
        return gridPane;
    }

    private void resettextFields() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String val = "";
                if (sudokuGrid[row][col] > 0) {
                    val += (char) ('0' + sudokuGrid[row][col]);
                }
                textFields[row][col].setText(val);
            }
        }
    }
    private void resetGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (textFields[row][col].getText().length() != 1) {
                    sudokuGrid[row][col] = 0;
                } else {
                    sudokuGrid[row][col] = Integer.parseInt(textFields[row][col].getText());
                }
                System.out.print(sudokuGrid[row][col]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
