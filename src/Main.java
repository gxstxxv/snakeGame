import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[/*(╯°□°)╯︵ ┻━┻*/] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("[Speed-Snake]");
        System.out.println("This is a small terminal game, which is supposed to represent the well-known game snake. There is a small difference in that the player himself sets the pace. The challenge is no longer to collect all the apples, but rather to do so in a short time.");
        System.out.println("\nPress enter to start.");

        if(scanner.nextLine().isEmpty()) {

            game();

        }

    }

    public static void game(){

        Instant starts = Instant.now();

        Scanner scanner = new Scanner(System.in);
        Random randomPosition = new Random();

        char[][] field = {   //0    1    2    3    4    5    6    7
                        /*0*/{'-', '-', '-', '-', '-', '-', '-', '-'},
                        /*1*/{'|', '+', '+', '+', '+', '+', '+', '|'},
                        /*2*/{'|', '+', '+', '+', '+', '+', '+', '|'},
                        /*3*/{'|', '+', '+', 'o', '+', '+', '+', '|'},
                        /*4*/{'|', '+', '+', '+', '+', '+', '+', '|'},
                        /*5*/{'|', '+', '+', '+', '+', '+', '+', '|'},
                        /*6*/{'|', '+', '+', '+', '+', '+', '+', '|'},
                        /*7*/{'-', '-', '-', '-', '-', '-', '-', '-'}
        };

        String eingabe;

        int[] x = new int[36];
        int[] y = new int[36];

        x[0] = 3;
        y[0] = 3;

        int neuX = 0;
        int neuY = 0;

        int pos = 0;

        //generate first apple
        int aX = 0;
        int aY = 0;
        while(field[aY][aX] != '+') {
            aX = randomPosition.nextInt(7 - 1) + 1;
            aY = randomPosition.nextInt(7 - 1) + 1;
        }
        field[aY][aX] = 'a';

        //first field
        fieldBuilder(field);

        //game loop until snake got max length
        while(pos != 35) {

            //getting input
            eingabe = String.valueOf(scanner.next().charAt(0));

            //giving input vector-meaning
            if(eingabe.equals("w")){
                neuY = y[pos] - 1;
                neuX = x[pos];
            }
            if(eingabe.equals("a")){
                neuY = y[pos];
                neuX = x[pos] - 1;
            }
            if(eingabe.equals("s")){
                neuY = y[pos] + 1;
                neuX = x[pos];
            }
            if(eingabe.equals("d")){
                neuY = y[pos];
                neuX = x[pos] + 1;
            }

            //checking if border or snake itself is touched
            if(field[neuY][neuX] != '|' && field[neuY][neuX] != '-' && field[neuY][neuX] != 'o'){

                //statement if snake gets longer (apple is eaten)
                if(field[neuY][neuX] == 'a'){

                    pos++;
                    for(int i = 0; i <= pos; i++) {
                        if(i == pos) {
                            x[i] = neuX;
                            y[i] = neuY;
                        }
                    }

                    //apple generator
                    while(field[aY][aX] != '+' && pos != 35) {
                        aX = randomPosition.nextInt(7 - 1) + 1;
                        aY = randomPosition.nextInt(7 - 1) + 1;
                    }

                }
                //statement if snake stays the same length
                else {

                    for(int i = 0; i <= pos; i++) {
                        if(i == pos) {
                            x[i] = neuX;
                            y[i] = neuY;
                        }
                        else {
                            x[i] = x[i+1];
                            y[i] = y[i+1];
                        }
                    }

                }

                //reset
                for (int i = 1; i != 7; i++){
                    for(int a = 1; a != 7; a++){
                        field[i][a] = '+';
                    }
                }

                //position assignment apple and snake
                field[aY][aX] = 'a';
                for(int i = 0; i <= pos; i++){
                    field[y[i]][x[i]] = 'o';
                }

                //build updated field
                fieldBuilder(field);

                //display current time
                Instant ends = Instant.now();
                String time = String.valueOf(Duration.between(starts, ends)).replace('.', 's').replace('M', 'm').replace("PT", "");
                System.out.print("[" + time.substring(0, time.length() - 7) + "]");
                System.out.println("[" + (pos+1) + "p]");

            }
            else
                break;


        }

        //win or loss condition
        if(pos == 35) {
            System.out.println("Win!");
        }
        else {
            System.out.println("Game Over!");
        }

    }

    public static void fieldBuilder(char[][] field) {

        //building field matrix
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (char[] chars : field) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }

    }

}