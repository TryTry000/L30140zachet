package ru.lakin.l30140zachet;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MyRun extends Application {
    // Флаг состояния для прыжков фигур
    Boolean flag = true;

    // Динамические элементы
    Circle circle;
    Polygon triangle;
    HBox shpHBox;

    // событие "выбрали букву (раньше был цвет)"
    private void showColors(CheckBox clrRed, CheckBox clrGreen, CheckBox clrBlue, Label selectedColors) {
        String allSelected = "Выбрано: ";
        if (clrRed.isSelected()) allSelected += "Аз ";
        if (clrGreen.isSelected()) allSelected += "Буки ";
        if (clrBlue.isSelected()) allSelected += "Веди";
        if (allSelected.equals("Выбрано: ")) selectedColors.setText("ничо :(");
        else selectedColors.setText(allSelected);
    }

    // событие "прыжок"
    //EventHandler<ActionEvent> pryg = new EventHandler<ActionEvent>() {
    EventHandler<ActionEvent> pryg = new EventHandler<>() { // Вот так красиво ИДЕА заменила предыдущую строку
        public void handle(ActionEvent e) {
            if (flag) {
                shpHBox.getChildren().remove(circle);
                shpHBox.getChildren().add(circle);
            } else {
                shpHBox.getChildren().remove(triangle);
                shpHBox.getChildren().add(triangle);
            }
            flag = !flag;
        }
    };

    // событие "Кыш!"
    //EventHandler<ActionEvent> kysh = new EventHandler<ActionEvent>() {
    EventHandler<ActionEvent> kysh = e -> System.exit(0); // Вместо предыдущей строки ИДЕА вставила лямбду

    @Override
    public void start(Stage primaryStage) {
        try {

// -------- Примитивы

            // --- Пустая строка
            Text txtBlank = new Text(" ");

            // --- Чек-бокс
            Label selectedColors = new Label("Выбрано: Аз");
            CheckBox clrRed  = new CheckBox("Аз");
            clrRed.setSelected(true);
            CheckBox clrGreen  = new CheckBox("Буки");
            clrGreen.setAllowIndeterminate(true);
            CheckBox clrBlue  = new CheckBox("Веди");
            clrBlue.setAllowIndeterminate(true);
            clrBlue.setIndeterminate(true);
            // выбор чек-бокса
            clrRed.setOnAction(event -> showColors(clrRed, clrGreen, clrBlue, selectedColors));
            clrGreen.setOnAction(event -> showColors(clrRed, clrGreen, clrBlue, selectedColors));
            clrBlue.setOnAction(event -> showColors(clrRed, clrGreen, clrBlue, selectedColors));

            // --- Поле текстового ввода с кнопочкой
            String patsakSays = "   Пацак сказал: ";
            Label lblText = new Label(patsakSays);
            TextField textField = new TextField();
            textField.setPrefColumnCount(7);
            Button btnTextIn = new Button("Ку!");
            // отправка текста куда-нить
            btnTextIn.setOnAction(event -> lblText.setText(patsakSays + textField.getText()));

            // --- Круг
            circle = new Circle(100, Color.RED);
            circle.setStroke(Color.GREEN);
            circle.setStrokeWidth(3);
            //circle.setCenterX(30);
            //circle.setCenterX(60);

            // --- Треугольник
            triangle = new Polygon(0, 0, 200, 0, 100, 200);
            triangle.setFill(Color.GREEN);
            triangle.setStroke(Color.RED);
            triangle.setStrokeWidth(5);

            // --- Кнопки
            Button btnPryg = new Button("Прыг!");
            btnPryg.setStyle("-fx-background-color: NAVY;");
            btnPryg.setTextFill(Color.WHITE);
            Button btnKysh = new Button("Кыш!");
            btnKysh.setStyle("-fx-background-color: NAVY;");
            btnKysh.setTextFill(Color.WHITE);
            // нажатие кнопки
            btnPryg.setOnAction(pryg);
            btnKysh.setOnAction(kysh);

            // --- Текст
            Text txt = new Text();
            txt.setStyle("-fx-font-size: 11pt; -fx-font-family: \"Segoe UI Semibold\";" +
                    "-fx-text-fill: navy; -fx-opacity: 1;");
            txt.setText("Народ предпочитает синие кнопки!\n ");

// -------- Примитивы - в горизонтальные панели!

            // --- Панель 0 "горизонтальная линия" для примитивов (чек-боксы)
            HBox bxHBox = new HBox(30);
            // --- добавляем примитивы в панель для примитивов
            bxHBox.setAlignment(Pos.CENTER);
            bxHBox.getChildren().add(clrRed);
            bxHBox.getChildren().add(clrGreen);
            bxHBox.getChildren().add(clrBlue);

            // --- Панель 1 "горизонтальная линия" для примитивов (результат чек-боксов)
            HBox bxrzHBox = new HBox(30);
            // --- добавляем примитивы в панель для примитивов
            bxrzHBox.setAlignment(Pos.CENTER);
            bxrzHBox.getChildren().add(selectedColors);
            bxrzHBox.getChildren().add(lblText);

            // --- Панель 2 "горизонтальная линия" для примитивов (ввод в текстовую строку и кнопочка)
            HBox txtInHBox = new HBox(30);
            // добавляем примитивы в панель для примитивов
            txtInHBox.setAlignment(Pos.CENTER);
            txtInHBox.getChildren().add(textField);
            txtInHBox.getChildren().add(btnTextIn);

            // --- Панель 3 "горизонтальная линия" для примитивов (результат ввода в текстовую строку)
            HBox txtInrzHBox = new HBox(30);
            // добавляем примитивы в панель для примитивов
            txtInrzHBox.setAlignment(Pos.CENTER);
            txtInrzHBox.getChildren().add(lblText);

            // --- Панель 4 "горизонтальная линия" для фигур
            shpHBox = new HBox(30);
            // добавляем примитивы в панель для примитивов
            shpHBox.setAlignment(Pos.CENTER);
            shpHBox.getChildren().add(circle);
            shpHBox.getChildren().add(triangle);

            // --- Панель 5 "горизонтальная линия" для кнопок
            HBox btnHBox = new HBox(30);
            // добавляем примитивы в панель для примитивов
            btnHBox.setAlignment(Pos.CENTER);
            btnHBox.getChildren().add(btnPryg);
            btnHBox.getChildren().add(btnKysh);

            // --- Панель 6 "горизонтальная линия" для текста
            HBox txtHBox = new HBox(30);
            // добавляем примитивы в панель для примитивов
            txtHBox.setAlignment(Pos.CENTER);
            txtHBox.getChildren().add(txt);

// -------- Горизонтальные панели - в вертикальные панели!

            // Панель "Вертикальная линия" - контейнер для горизонтальных панелей
            VBox cntVBox = new VBox(30);
            // добавляем примитивы и панели в панель для примитивов

            cntVBox.getChildren().add(txtBlank);
            cntVBox.getChildren().add(bxHBox);
            cntVBox.getChildren().add(bxrzHBox);
            cntVBox.getChildren().add(txtInHBox);
            cntVBox.getChildren().add(txtInrzHBox);
            cntVBox.getChildren().add(shpHBox);
            cntVBox.getChildren().add(btnHBox);
            cntVBox.getChildren().add(txtHBox);

// -------- Ну и всё!

            // Добавление Gridpane к сцене
            //Scene scene = new Scene(cntVBox, 450, 50);
            Scene scene = new Scene(cntVBox);
            // Лепим сцену в начальную стадию
            primaryStage.setTitle("Лабы про Яву-ФыКС и зачет по Java");
            primaryStage.setScene(scene);
            // Кажем начальную стадию
            primaryStage.show();
        }
        catch (Exception err0) {
            // Ошибку - в консоль и лог. Второй параметр - шапка собщения об ошибке
            if (!MyExc.echoExc2console2log(err0, "-7000: Сбой: ")) {}
        }
    }

    // Запуск приложения
    public static void myRun (String[] args) {
        launch(args);
    }
}

/*
       try {
            System.out.println("Здесь - код программы");
        }
        // Обработка исключений
        catch (Exception err0) {
            // Ошибку - в консоль и лог. Второй параметр - шапка собщения об ошибке
            if (!MyExc.echoExc2console2log(err0, "-7040: Сбой такой-то: "))
            { }
        }
*/