import java.util.Scanner;

public class Main
{
    private static void amountofVal(GaussMethods g)
    {
        System.out.print("Input number of unknown variables: >>> ");
        Scanner in = new Scanner(System.in);
        int number;
        do
        {

            while (!in.hasNextInt())
            {
                System.out.print("Incorrect! Try again >>> ");
                in.next();
            }
            number = in.nextInt();
            g.size = number;
        } while (number <= 1);
    }

    public static void main(String[] args)
    {


        while (true)
        {
            System.out.print("\nChoose input method\n\n\t1 - Random\n\t2 - Input from keyboard\n\t3 - Input from file\n\tOther symbol == exit\n\n>>>  ");

            try
            {
                Scanner in = new Scanner(System.in);
                int choice = in.nextInt();

                GaussMethods gauss = new GaussMethods();

                if (choice == 1)
                {
                    amountofVal(gauss);
                    gauss.RandomMatrix(gauss);
                    GaussMethods.Show_Equation(gauss);
                    GaussMethods.Solution(gauss);
                    continue;
                }
                if (choice == 2)
                {
                    amountofVal(gauss);
                    gauss.InputMatrix(gauss);
                    GaussMethods.Show_Equation(gauss);
                    GaussMethods.Solution(gauss);
                    continue;
                }
                if (choice == 3)
                {
                    gauss.ReadFromFile(gauss);
                    GaussMethods.Show_Equation(gauss);
                    GaussMethods.Solution(gauss);

                }

            } catch (Exception e)
            {
                System.exit(0);
            }
        }
    }
}
