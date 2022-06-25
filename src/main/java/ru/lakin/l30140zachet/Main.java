package ru.lakin.l30140zachet;

import java.io.*;

// =========== Корневой класс. Всегда должен присутствовать. Тогда как программа - в классе RunMe ======================
// =====================================================================================================================
public class Main {
        // Вызов программы meRun
    public static void main (String[] args) {
        String errorType;
        // Запускаем программу
        try { MyRun.myRun(args); }
        // Ловим ошибку
        catch ( Exception err ) {
            // Понимаем тип ошибки
            if (err instanceof RuntimeException) { errorType = "Ошибка кода"; }
            else if (err instanceof IOException) { errorType = "Ошибка ввода/вывода"; }
            else { errorType = "Прочие ошибки"; }
            // Печатаем ошибку на консоль
            MyExc.echoExc2console2log (err, "-0030: Фатальнй сбой \"" + errorType + "\"");
            System.exit(7);
        }
        System.exit(0);
    }
}