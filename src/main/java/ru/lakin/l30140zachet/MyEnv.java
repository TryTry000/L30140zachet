package ru.lakin.l30140zachet;
import java.io.File;

// =========== Переменные окружения класса MyEnv =======================================================================
// =====================================================================================================================
class MyEnv {
// ---------------- CR платформы
    static final String myCR = System.lineSeparator();
// ---------------- слэш пути патформы (прямой|обратный)
    static final String mySL = File.separator;
// ---------------- домашняя папка программы (!!!!! ЗАГЛУШКА)
//  static final String myHomePath = System.getProperty("user.dir");
    static final String myHomePath = "D:" + mySL + "__Android" + mySL + "TestHome";
// ---------------- путь и имя логфайла
    static String myLogFilePathName = myHomePath + File.separator + "Log.txt";
}
