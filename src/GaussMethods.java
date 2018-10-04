import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;



class GaussMethods
{

    int size; // amount of eq
    private double[][] matrix; // matrix
    private double[][] workingMatrix; // matrix copy

    static void Show_Equation(GaussMethods gauss) {
        for (var i = 0; i < gauss.size; i++) {
            for (var j = 0; j < gauss.size; j++) {
                System.out.print(String.format("(%.2f)*X%d ", gauss.matrix[i][j], j + 1));
                if (j != gauss.size - 1) System.out.print("+ ");
            }
            System.out.print(String.format("= %.2f\n", gauss.matrix[i][gauss.size]));
        }
        System.out.println();
    }

    static void Solution(GaussMethods g) {
        g.workingMatrix = new double[g.size][g.size + 1];
        for (var i = 0; i < g.size; i++) {
            if (g.size + 1 >= 0) System.arraycopy(g.matrix[i], 0, g.workingMatrix[i], 0, g.size + 1);
        }
        double[] solution = new double[g.size]; // solutions matrix
        var determinant = Determinant(g);

        System.out.println("Matrix\n");
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size; j++) {
                System.out.print(String.format("%.2f  ", g.matrix[i][j]));
            }
            System.out.println();
            System.out.println();
        }

        System.out.println("Working matrix\n");
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size; j++) {
                System.out.print(String.format("%.2f  ", g.workingMatrix[i][j]));
                System.out.print("  ");

            }
            System.out.println();
            System.out.println();
        }

        System.out.println(String.format("Determinant is equal to: %.2f", determinant));
        if (determinant != 0) {
            for (int i = g.size - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = g.size - 1; j > i; j--) {
                    sum += solution[j] * g.workingMatrix[i][j];
                }
                solution[i] = (g.workingMatrix[i][g.size] - sum) / g.workingMatrix[i][i];
                System.out.println();
            }
            for (var i = 0; i < g.size; i++) {
                System.out.print(String.format("X%d = ", i + 1));
//                System.out.print(String.format("%.2d",solution[i]));
                System.out.print(String.format("%.2f", solution[i]));
                System.out.println();
            }
        } else System.out.println("System does not have single solution");
    }

    private static void Swap_Lines(GaussMethods gauss, int str1, int str2) {
        var temp = new double[gauss.size + 1]; //temporary str for swapping
        for (var j = 0; j < gauss.size + 1; j++) {
            temp[j] = gauss.workingMatrix[str1][j];
            gauss.workingMatrix[str1][j] = gauss.workingMatrix[str2][j];
            gauss.workingMatrix[str2][j] = temp[j];
        }
    }

    private static double Determinant(GaussMethods gauss) // find det using Gauss Method
    {
        double det = 1;
        for (var j = 0; j < gauss.size - 1; j++) {
            double per2 = gauss.workingMatrix[j][j];
            int maxInd = j;

            double per1;
            for (var i = j + 1; i < gauss.size; i++) {
                per1 = gauss.workingMatrix[i][j];
                if (Math.abs(per1) > Math.abs(per2)) maxInd = i;
            }
            if (j != maxInd) {
                Swap_Lines(gauss, j, maxInd); // когда в массиве находится ненулевой элемент, стоящий ниже нужной строки, меняем их с помощью метода Change
                det *= -1; // при замене строк знак определителя меняется на противоположный
            }
            per2 = gauss.workingMatrix[j][j];
            if (per2 != 0) {
                for (var x1 = j + 1; x1 < gauss.size; x1++) {
                    if (gauss.workingMatrix[x1][j] != 0) {
                        per1 = gauss.workingMatrix[x1][j];
                        for (var x2 = j; x2 < gauss.size + 1; x2++)
                            gauss.workingMatrix[x1][x2] = gauss.workingMatrix[j][x2] * per1 / per2 - gauss.workingMatrix[x1][x2];
                    }
                }
            } else {
                det = 0;
                break;
            }
        }
        for (var i = 0; i < gauss.size; i++) {
            det *= gauss.workingMatrix[i][i];
        }

        return det;
    }
    void InputMatrix(GaussMethods g) // user values
    {
        Scanner in = new Scanner(System.in);
        g.matrix = new double[g.size][g.size + 1];
        System.out.println("Input values");
        if (g.size < 1) {
            System.out.println("Wrong amount of equations");
        } else {
            for (var i = 0; i < g.size; i++) {
                for (var j = 0; j < g.size + 1; j++) {
//                    System.out.printf("(%d;%d) = ", i + 1, j + 1);
//                    matrix[i][j] = in.nextDouble();
                    System.out.printf("(%d;%d) = ", i + 1, j + 1);
                    while (!in.hasNextDouble()) {
                        System.out.printf("(%d;%d) = ", i + 1, j + 1);
                        in.next();
                    }
                    g.matrix[i][j] = in.nextDouble();

                }
            }
//            Show_Matrix(g.size);
        }
    }
    
    void ReadFromFile(GaussMethods g)

    {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String [] splitted = null;
        int row = 0;
        float[][] matrixFromFile = new float[20][20];
        assert sc != null;
        while(sc.hasNext())
        {
            splitted = sc.nextLine().split(";");
            for(int column = 0; column < splitted.length; column++) {
                matrixFromFile[row][column] = Float.parseFloat(splitted[column]);
//                System.out.println(matrixFromFile[row][column]);
//                System.out.println(splitted.length);
            }
            row++;
        }

        assert splitted != null;
        g.size = splitted.length - 1;
        g.matrix = new double[g.size][g.size + 1];
        for (var i = 0; i < g.size; i++)
        {
            for (var j = 0; j < g.size + 1; j++)
            {

                g.matrix[i][j] = matrixFromFile[i][j];
            }
        }
    }



    
    void RandomMatrix(GaussMethods g) //random values
    {

        g.matrix = new double[g.size][g.size + 1];
        var a = new Random();
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size + 1; j++) {
                double d = 10.0 + a.nextDouble() * 20.0;
                g.matrix[i][j] = d;
//                System.out.print(String.format("%.2f  ", d));
            }
//            System.out.println();
//            System.out.println();
        }
    }



    private void Show_Matrix(int size) {
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size + 1; j++) {
                System.out.print(matrix[i][j]);
                System.out.print("  ");
            }
            System.out.println();
        }
    }
}


