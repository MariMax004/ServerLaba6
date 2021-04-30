import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        WorkMatriks workMatriks = null;
        FileReader fr;

        int i = 0;
        int n = 0;


        try {
            console.outText("Начало работы программы");

            while (true) {
                console.outQuestion("Ввести данные через файл?(y/n)");
                String nextS = console.inputText().toLowerCase().trim();
                if (nextS.equals("y") || nextS.equals("")) {
                    console.outQuestion("Введите имя файла (файл должен быть формата .txt): ");
                    String fileName = console.inputText().trim();
                    try {
                        fr = new FileReader("src/main/resources/"+fileName+".txt");
                        Scanner scan = new Scanner(fr);
                        int iteratorNext = 1;
                        while (scan.hasNextLine()) {
                            if(iteratorNext == 1){
                                try {
                                    n = Integer.parseInt(scan.nextLine().trim());
                                    if(n <= 0){
                                        console.outText("Матрица не может иметь размерность 0 и меньше");
                                        break;
                                    }
                                    workMatriks = new WorkMatriks(n);
                                    iteratorNext++;
                                }catch (NumberFormatException ex){
                                    console.outText("Ошибка в данных файла при получении размерности матрицы");
                                    break;
                                }
                            }else{
                                String input = scan.nextLine().trim().toLowerCase();
                                if (input.split(" ").length == n+1) {
                                    for (int j = 0; j < n + 1; j++) {
                                        try {
                                            workMatriks.matr[i][j] = workMatriks.matrN[i][j] =
                                                    Float.parseFloat(input.split(" ")[j]);
                                        } catch (NumberFormatException ex) {
                                            console.outText("Ошибка в данных файла (неверный формат данных внутри строки)");
                                            break;
                                        }
                                    }
                                    i++;
                                } else {
                                    console.outText("Ошибка в данных файла (строка матрицы не верной длинны)");
                                    break;
                                }
                            }
                        }

                        if(workMatriks != null) {
                            console.outText("Изначальная матрица: ");
                            workMatriks.outMatriks(n);
                            console.outText("--------------");
                            console.outText("Ответы: " + workMatriks.launchingTheSolution(n));
                            console.outText("det = " + workMatriks.doDet(n));
                            console.outText("Праобразованная матрица: ");
                            workMatriks.outMatriks(n);
                            console.outText("--------------");
                            console.outText("Невязки: " + workMatriks.unreasonable(n));

                        }

                        break;
                    } catch (FileNotFoundException e) {
                        console.outText("Ошибка в имени файла, его не существует");
                    }
                } else if (nextS.equals("n")) {
                    console.outText("Введите матрицу вручную");
                    console.outQuestion("Введите размерность матрицы числом: ");

                    n = Integer.parseInt(console.inputText().trim().toLowerCase());

                    workMatriks = new WorkMatriks(n);

                    console.outText("Введите матрицу");
                    while (i < n) {
                        String input = console.inputText().trim().toLowerCase();
                        if (input.split(" ").length == n + 1) {
                            for (int j = 0; j < n + 1; j++) {
                                try {
                                    workMatriks.matr[i][j] = workMatriks.matrN[i][j] = Float.parseFloat(input.split(" ")[j]);
                                } catch (NumberFormatException ex) {
                                    console.outText("Проблемы с введённой строкой, присутствуют лишние символы\n" +
                                            "Введите строку состоящию только из цифр");
                                    i--;
                                    break;
                                }
                            }
                            i++;
                        } else {
                            console.outText("Строка матрицы введена некорректно");
                        }
                    }

                    console.outText("Изначальная матрица: ");
                    workMatriks.outMatriks(n);
                    console.outText("--------------");
                    console.outText("Ответы: " + workMatriks.launchingTheSolution(n));
                    console.outText("det = " + workMatriks.doDet(n));
                    console.outText("Праобразованная матрица: ");
                    workMatriks.outMatriks(n);
                    console.outText("--------------");
                    console.outText("Невязки: " + workMatriks.unreasonable(n));

                    break;
                } else {
                    console.outText("Вы ввели некорректный символ продолжения");
                }
            }
       }catch (NoSuchElementException ex){
            console.outText("Программа завершилась экстренным способом, через ctrl+d ");
        }
    }
}
