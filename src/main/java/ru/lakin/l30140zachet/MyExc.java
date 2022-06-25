package ru.lakin.l30140zachet;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

// =========== ССоОРВ - Система Сообщений об Ошибках Реального Времени и ввода-вывода ==================================
// =========== Подробнее: https://javarush.ru/quests/lectures/questsyntaxpro.level14.lecture05 =========================
// Методы класса Throwable:
// getCause() Возвращает причину или nullесли причина не существует или неизвестна.
// String getLocalizedMessage() Возвращает локализованное описание этого экземпляра Exception.
// String getMessage() Возвращает подробное сообщение этого экземпляра Exception.
// StackTraceElement[] 	getStackTrace() информация о трассировке стека printStackTrace().
// Throwable[] getSuppressed() Возвращает массив всех исключений, (обычно подавленных оператором try-with-resources)
// Throwable initCause (Throwable cause) Инициализирует причину этого исключения указанным значением.
// void printStackTrace() Выводит сей экземпляр Exception и его обратную трассировку в стандартный поток ошибок.
// void printStackTrace (PrintStream s) Выводит сей экземпляр Exception и его обратную трассировку в поток печати.
// void printStackTrace (PrintWriter s) Выводит сей экземпл. Exception и его обр.трассировку на указанный модуль печати.
// void etStackTrace (StackTraceElement[] stackTrace) Задает элементы трассировки стека, кои будут доступны в
//                                                                                  getStackTrace() и printStackTrace().

// ============================== шапки сообщений об ошибках ===========================================================
// "-0010: Не удалось открыть файл \"" + fileName + "\""
// "-0020: Не удалось создать файл  [\"" + testFileName + "\"]"
// "-0030: Фатальный сбой" + errorType
// 7xxx - сообщения программы (не моей библиотеки, а моей программы)

// ============================== Класс сообщений об ошибках ===========================================================
public class MyExc {
    // ----------- Внутренние переменные ССоРВ
    static long myExcCount = 0; // Счетчик ошибок текущего запуска программы
    static String myExcMessage = ""; // Текст сообщения об ошибке для последующего вывода куда надо
    // Формат даты-времени
    static SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMMMMMMM yyyy. HH:mm:ss z Z X",
                                                                                      Locale.getDefault());

// ----------- Формирование сообщения об ошибке для последующего вывода куда надо --------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    private static void myExcMessageString (Exception err, String myExcHeadMessage) {
        // Дата-время
        Date dateRAW = new Date();
        String date = dateFormat.format(dateRAW);
        // Создаём сообщение об ошибке
        myExcCount++;
        myExcMessage = "=== " + date + MyEnv.myCR;
        myExcMessage += "!!! Сбой 0" + myExcCount + myExcHeadMessage + MyEnv.myCR;

        if ( err == null ) {
            myExcMessage += "!!! Тип: НЕОПОЗНАН" + MyEnv.myCR;
            myExcMessage += "!!! Точка: НЕОПОЗНАНА" + MyEnv.myCR;
        } else {
            myExcMessage += "!!! Тип: " + err.toString() + MyEnv.myCR;
            myExcMessage += "!!! Точка: " + err.getStackTrace()[0] + MyEnv.myCR;
        }
    }

// ----------- Сообщение об ошибке - в логфайл -------------------------------------------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    // Возвращает false, если вывести сообщение в логфайл не удалось. Иначе true.
    private static boolean myExcLogfile (Exception err, String myExcHeadMessage) {
        myExcMessageString (err, myExcHeadMessage);           // Формируем сообщение об ошибке

        // Создали объект "логфайл". Если реального логфайла нет, то создаем
        File myLogFile = new File( MyEnv.myLogFilePathName ); // Создали объект "логфайл"
        try { myLogFile.createNewFile(); }                    // Если логфайла нет, создаём его
        catch ( IOException e ) {
            System.out.println( "=== Фатальная ошибка. Невозможно вести логфайл (write2myLog->createNewFile) \"" +
                    MyEnv.myLogFilePathName + "\". Программа прервана. Ша.");
            return false;
        }

        // Пишем в логфайл
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(myLogFile, true /* append = true */));
            pw.println(myExcMessage + MyEnv.myCR);
            pw.close(); // Закрываем файл
        }

/*
        try {
            PrintWriter pw = new PrintWriter ( myLogFile, true );    // Открываем файл на запись, ассоциируя с ним объект
            pw.println( myExcMessage + MyEnv.myCR ); // Пишем в файл строки
            pw.close(); // Закрываем файл
        }
*/
        catch ( IOException e ) {
            System.out.println( "=== Фатальная ошибка. Невозможно записать в логфайл (write2myLog->PrintWriter) \"" +
                    MyEnv.myLogFilePathName + "\". Программа прервана. Ша.");
            return false;
        }
        return true;
    }

// ------- Сообщение об ошибке - на консоль ----------------------------------------------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    // Возвращает false, если вывести сообщение на консоль не удалось. Иначе true.
    public static boolean echoExc2console (Exception err, String myExcHeadMessage) {
        myExcMessageString (err, myExcHeadMessage); // Формируем сообщение об ошибке
        System.out.print(myExcMessage);
        myExcMessage = "";
        return true;
    }
// ------- Сообщение об ошибке - на консоль и в лог --------------------------------------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    // Возвращает false, если вывести сообщение в логфайл не удалось. Иначе true.
    public static boolean echoExc2console2log (Exception err, String myExcHeadMessage) {
        if ( myExcLogfile (err, myExcHeadMessage) ) { // Формируем сообщение об ошибке и выводим в логфайл
            System.out.print(myExcMessage);
            myExcMessage = "";
            return true;
        }
        myExcMessage = "";
        return false;
    }
// ------- Сообщение об ошибке - на экран ------------------------------------------------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    // Возвращает false, если вывести сообщение на экран не удалось. Иначе true.
    public static boolean echoExc2screen2 (Exception err, String myExcHeadMessage) {
        myExcMessageString (err, myExcHeadMessage); // Формируем сообщение об ошибке
        // !!!!!!! ЗАГЛУШКА
        return true;
    }
// ------- Сообщение об ошибке - на экран и в лог ----------------------------------------------------------------------
    // myExcHeadMessage - шапка  сообщения об ошибке (заполняется в месте появления ошибки)
    // Возвращает false, если вывести сообщение в логфайл не удалось. Иначе true.
    public static boolean echoExc2screen2log (Exception err, String myExcHeadMessage) {
        if ( myExcLogfile (err, myExcHeadMessage) ) { // Формируем сообщение об ошибке и выводим в логфайл
            // !!!!!!! ЗАГЛУШКА
            myExcMessage = "";
            return true;
        }
        myExcMessage = "";
        return false;
    }
// ------- Отладочная печать -------------------------------------------------------------------------------------------
    public static void mD ( int lineNumStr, String mess ) {
        System.out.println("xxxxxxx ОТЛАДКА xxxxxxx Строка: " + lineNumStr + " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("xxxxxxx " + mess);
    }
}

