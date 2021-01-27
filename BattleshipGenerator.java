package uj.java.pwj2020.collections;

import java.util.ArrayList;
import java.util.Random;

public interface BattleshipGenerator {

    String generateMap();

    static BattleshipGenerator defaultInstance() {
        return new MyGameGenerator();
    }

}
class Pair
{
    int x;
    int y;
    Pair (int a, int b)
    {
        x = a;
        y = b;
    }
    Pair (Pair p)
    {
        x = p.x;
        y = p.y;
    }
    boolean Equals (Pair a)
    {
        if (x == a.x && y == a.y)
            return  true;
        return  false;
    }
}
class MyGameGenerator implements BattleshipGenerator
{
    private char[][] fields;
    private boolean FieldAveliable(Pair p)
    {
        if (p.x >= fields.length || p.y >= fields[0].length || p.x < 0 || p.y < 0)
            return  false;
        if (fields [p.x][p.y] == 'B' || fields [p.x][p.y] == '#')
            return false;
        return  true;

    }
    private void AddIfAveliable(Pair a, ArrayList<Pair> arrayList)
    {
        if (FieldAveliable(a))
            arrayList.add(a);
    }
    private void DeleteShip(Pair[] ship)
    {
        for(int i = 0; i < ship.length; i++)
            if (ship[i] != null)
                fields [ship[i].x][ship[i].y] = '.';
    }
    private void BlockField (Pair p, ArrayList<Pair> avaliableList)
    {
        if (p.x >= fields.length || p.y >= fields[0].length || p.x < 0 || p.y < 0)
            return;
        if (fields[p.x][p.y] == '.')
        {
            fields[p.x][p.y] = 'B';
            avaliableList.removeIf(n->(n.Equals(p)));
        }
    }
    private boolean generateShip(ArrayList<Pair> avaliableFields, int size)
    {
        Pair[] ship = new Pair[size];

        Random R = new Random();
        int auxInt;
        auxInt = R.nextInt(avaliableFields.size());
        ship[0] =  new Pair(avaliableFields.get(auxInt));
        fields[ship[0].x][ship[0].y] = '#';
        ArrayList<Pair> possibleFields = new ArrayList<>();
        for (int i = 1;i < size;i++)
        {
            AddIfAveliable( new Pair(ship[i - 1].x,ship [i -1].y - 1),possibleFields);
            AddIfAveliable( new Pair(ship[i - 1].x,ship [i -1].y + 1),possibleFields);
            AddIfAveliable( new Pair(ship[i - 1].x - 1,ship [i -1].y),possibleFields);
            AddIfAveliable( new Pair(ship[i - 1].x + 1,ship [i -1].y),possibleFields);
            if (possibleFields.size() == 0)
            {
                DeleteShip(ship);
                return  false;
            }
            auxInt = R.nextInt(possibleFields.size());

            ship[i] = new Pair(possibleFields.get(auxInt));
            fields[ship[i].x][ship[i].y] = '#';
            possibleFields.remove(auxInt);
        }

        for (int i = 0; i < size; i++)
        {
            BlockField(new Pair(ship[i].x,ship[i].y - 1),avaliableFields);
            BlockField(new Pair(ship[i].x,ship[i].y + 1),avaliableFields);
            BlockField(new Pair(ship[i].x - 1,ship[i].y - 1),avaliableFields);
            BlockField(new Pair(ship[i].x + 1,ship[i].y - 1),avaliableFields);
            BlockField(new Pair(ship[i].x - 1,ship[i].y +  1),avaliableFields);
            BlockField(new Pair(ship[i].x + 1,ship[i].y + 1),avaliableFields);
            BlockField(new Pair(ship[i].x - 1,ship[i].y),avaliableFields);
            BlockField(new Pair(ship[i].x + 1,ship[i].y ),avaliableFields);
            final int auxI = i;
            avaliableFields.removeIf(n ->(n.Equals(ship[auxI])));
        }
        return true;
    }
    public String generateMap()
    {
        fields = new char[10][10];
        ArrayList<Pair> availableFields = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
            {
                fields[i][j] = '.';
                availableFields.add( new Pair(i,j));
            }
        for (int i =0; i < 4; i++)
        {
            for (int j = 3 - i; j < 4;j++)
            {
                boolean b;
                do
                {
                    b = generateShip(availableFields,4 - i);
                }while (!b);
            }
        }
        // for(int i = 0; i < 10; i++)
        //  System.out.println(fields[i]);
        String result =  "";
        for (int i =0; i < 10; i++)
            result += String.valueOf(fields[i]);
        result = result.replace('B','.');
        return result;
    }
}
