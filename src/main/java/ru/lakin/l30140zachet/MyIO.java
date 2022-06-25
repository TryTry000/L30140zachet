package ru.lakin.l30140zachet;
import java.io.File;
import java.io.IOException;

// ============================== шапки сообщений об ошибках ===========================================================
// "-0010: Не удалось открыть файл \"" + fileName + "\""
// "-0020: Не удалось создать файл  [\"" + testFileName + "\"]"
// "-0030: Фатальный сбой" + errorType
// 7xxx - сообщения программы (не моей библиотеки, а моей программы)

// ============================== Класс ввода-вывода ===================================================================
public class MyIO {
    // --- Если файла нет, то создать ----------------------
    public static boolean myCheckCreateFile(String myFilePathName) {
        // Разбираемся с файлами
        File myFile = new File(myFilePathName); // Создали объект - файл
        // Если такого файла на диске нет, то пробуем создать
        try { myFile.createNewFile(); }
        catch (IOException err) {
            // Ошибку - в консоль и лог. Второй параметр - шапка собщения об ошибке.
            MyExc.echoExc2console2log(err,
                    "-0020: Не удалось создать файл  [\"" + myFilePathName + "\"]");
            return false;
        }
        return true;
    }
}
