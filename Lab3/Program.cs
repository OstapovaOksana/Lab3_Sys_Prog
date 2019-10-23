using System ;
using System.Collections.Generic ;
using System.Linq ;
using System.Text ;
using System.Threading.Tasks ;

namespace CHM
{
    class Program
    {
        static double func(double x, double y)
        {
            return ((1 / x) * y) - ((1 / x) * Math.Pow(y, 2));
        }
        static void Main(string[] args)
        {
            double a = 1, b = 1.5, h = 0.1;
            /*sycgdhd*/
            double[] X = new double[6];
            double[] Y = new double[6];
            double[] Z = new double[6];
            X[0] = a;
            X[1] = 1.1;
            X[2] = 1.2;
            X[3] = 1.3;
            X[4] = 1.4;
            X[5] = 1.5;

            Y[0] = 0.5;
            for (int i = 1; i <= 5; i++)
            {
                //X[i] = X[i-1]* h;
                Z[i] = Y[i - 1] + h * func(X[i - 1], Y[i - 1]);
                Y[i] = Y[i - 1] + h * (func(X[i - 1], Y[i - 1]) + func(X[i], Z[i])) / 2.0;
            }

            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine("X["+i+"] = "+X[i]+"\n");
            }
            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine("Y[" + i + "] = " + Y[i] + "\n");
            }

            Console.ReadLine();
        }
    }
}
