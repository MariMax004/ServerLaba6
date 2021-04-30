

import static java.lang.Math.abs;
import static java.lang.Math.pow;


public class WorkMatriks {
    float[][] matr;// основная матрица
    float[][] matrN;//матрица для вычисления невязок
    float[] outMass;
    int[][] cntlMass;
    Console console = new Console();

    public WorkMatriks(int n){
        this.matr = new float[n][n+1];
        this.matrN = new float[n][n+1];
        this.outMass = new float[n];
        this.cntlMass = new int[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                cntlMass[i][j] = j+1;
    }

    //Вывод матрицы
    public void outMatriks(int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                console.outQuestion(matr[i][j] + " ");
            }
            console.outQuestion("|" + matr[i][n]);
            console.outText(" ");
        }
    }

    //Функция решает СЛАУ
    public String launchingTheSolution(int n){
        for(int i = 0; i < n; i++){
            int point = 0;
            float max = 0;
            for(int j = i; j < n; j++){
                if(max < abs(matr[i][j])) {
                    max = abs(matr[i][j]);
                    point = j;
                }
            }
            //ставим max элемент на начало строки от 0
            for(int ii = i; ii < n; ii++){
                float temp = matr[ii][i];
                matr[ii][i] = matr[ii][point];
                matr[ii][point] = temp;

                int t = cntlMass[ii][i];
                cntlMass[ii][i] = cntlMass[ii][point];
                cntlMass[ii][point] = t;
            }

            for(int ii = i+1; ii < n; ii++){
                float u = matr[ii][i]/ matr[i][i];
                for(int j = i; j < n+1;j++){
                    matr[ii][j] = u* matr[i][j] - matr[ii][j];
                }
            }

            /*console.outText("----------");
            outMatriks(n);*/

        }


        int writeOutCnt = n-1;
        for(int i = n-1; i >= 0; i--){
            if(i == n-1){
                outMass[cntlMass[n-1][writeOutCnt]-1] = matr[i][n]/matr[i][n-1];
            }else{
                float answ = matr[i][n];
                int point = 0;
                for(int j = n-1; j >= 0 && matr[i][j] != 0; j--){
                    if(outMass[cntlMass[n-1][j]-1] != 0) {
                        answ -= matr[i][j] * outMass[cntlMass[n - 1][j] - 1];
                    }
                    point = j;
                }
                outMass[cntlMass[n-1][writeOutCnt]-1] = answ/matr[i][point];
            }
            writeOutCnt--;
        }


        String outStr = "";
        //int writeOutCnt = 1;
        writeOutCnt = 1;
        for(int i = 0; i < n;i++){
            outStr += "X" + writeOutCnt + " = " + outMass[i] + " ";
            writeOutCnt++;
        }


        return outStr;
    }

    //Функция считает определитель
    public float doDet(int n){
        float answer = 1;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(i == j)
                    answer *= matr[i][j];
        return answer;
    }

    //Функция считает невязки для уравнений
    public String unreasonable(int n){
        StringBuilder out = new StringBuilder();
        int writeOutCnt = 1;
        for(int i = 0; i < n; i++){
            float r = matrN[i][n];
            for(int j = 0; j < n; j++){
                r -= matrN[i][j]*outMass[j];
            }

            out.append("R").append(writeOutCnt).append(" = ").append(r).append(" ");
            writeOutCnt++;
        }
        return out.toString();
    }
}
