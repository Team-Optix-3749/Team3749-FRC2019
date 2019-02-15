package frc.robot;

import java.lang.*;
public class Matrix
{
    private double[][] vals;

    public Matrix (double[][] r)
    {
        vals = r;
    }

    public void swapRows (int a, int b)
    {
        double[] temp;
        temp = vals[a];
        vals[a] = vals[b];
        vals[b] = temp;
    }

    public void scaleRow (int r, double scalar)
    {
        for (int i = 0; i < vals[r].length; i ++)
            vals[r][i] *= scalar;
    }
    public void divRow (int r, double scalar)
    {
        for (int i = 0; i < vals[r].length; i ++)
            vals[r][i] /= scalar;
    }

    public void sumRows (int first, int second, int sum)
    {
        for (int i = 0; i < vals.length; i ++)
            vals[sum][i] = vals[first][i] + vals[second][i];
    }

    public void rowReduce ()
    {
        int rows = vals.length;
        for (int i = 0; i < rows; i ++)
        {
            for (int j = 0; j < rows; j ++)
            {
                if (vals[i][j] != 0)
                    divRow(i, vals[i][j]);
            }
        }
    }

    public void print()
    {
        String val;
        int end;
        for (int i = 0; i < vals.length; i ++)
        {
            for (int j = 0; j < vals[i].length; j ++)
            {
                val = String.valueOf(vals[i][j]);
                end = val.indexOf(".") + 5;
                if (end > val.length()) end = val.length();
                val = val.substring(0, end);
                System.out.print(val + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main (String[] args)
    {
        double [][] values = {
            {2, 0, 0, 1},
            {0, 1, 0, 1},
            {0, 0, 3, 1},
        };
        Matrix m = new Matrix(values);
        m.print();
        m.rowReduce();
        m.print();
    }
}