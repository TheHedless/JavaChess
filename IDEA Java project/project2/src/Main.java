import java.lang.reflect.Type;
import java.util.Scanner;

public class Main {
    static Piece.Color Round= Piece.Color.White;
    static Piece[][] Board;

    public static void main(String[] args) {
        Board = new Piece[8][8];
        for (int i = 0; i < Board.length; i++) {
            Board[i][6] = new Piece(Piece.Type.Pawn, Piece.Color.Black);
            Board[i][1] = new Piece(Piece.Type.Pawn, Piece.Color.White);
        }
        Board[0][0] = new Piece(Piece.Type.Rook, Piece.Color.White);
        Board[0][7] = new Piece(Piece.Type.Rook, Piece.Color.Black);
        Board[1][0] = new Piece(Piece.Type.Knight, Piece.Color.White);
        Board[1][7] = new Piece(Piece.Type.Knight, Piece.Color.Black);
        Board[2][0] = new Piece(Piece.Type.Bishop, Piece.Color.White);
        Board[2][7] = new Piece(Piece.Type.Bishop, Piece.Color.Black);
        Board[3][0] = new Piece(Piece.Type.King, Piece.Color.White);
        Board[3][7] = new Piece(Piece.Type.King, Piece.Color.Black);
        Board[4][0] = new Piece(Piece.Type.Queen, Piece.Color.White);
        Board[4][7] = new Piece(Piece.Type.Queen, Piece.Color.Black);
        Board[5][0] = new Piece(Piece.Type.Bishop, Piece.Color.White);
        Board[5][7] = new Piece(Piece.Type.Bishop, Piece.Color.Black);
        Board[6][0] = new Piece(Piece.Type.Knight, Piece.Color.White);
        Board[6][7] = new Piece(Piece.Type.Knight, Piece.Color.Black);
        Board[7][0] = new Piece(Piece.Type.Rook, Piece.Color.White);
        Board[7][7] = new Piece(Piece.Type.Rook, Piece.Color.Black);
        Show();
    }

    private static void Game() {
        Scanner input = new Scanner(System.in);
        System.out.print("Next move:");
        char[] number = input.nextLine().toCharArray();                     //expected input PL: A1-A2 ahol az első A1 az a honnan A2 meg a hova
        char[] lepes1 = new char[2];
        char[] lepes2 = new char[2];
        for(int p=0;p!=2;p++){
            lepes1[p]=number[p];
            lepes2[p]=number[p+3];
        }
        int[] Honnan = new int[2];
        int[] Hova = new int[2];
        Honnan[0] = lepes1[0]-'A';
        Honnan[1] = lepes1[1]-'1';
        Hova[0] = lepes2[0]-'A';
        Hova[1] = lepes2[1]-'1';                     //ASCII value alapján dolgozik
        if (MovementTest(Honnan, Hova)) {
            Board[Hova[0]][Hova[1]] = Board[Honnan[0]][Honnan[1]];
            Board[Honnan[0]][Honnan[1]] = null;
            Round=Piece.Color.values()[1-Round.ordinal()];
        }
        Show();
    }

    private static boolean MovementTest(int[] Honnan, int[] Hova) {
        Scanner input = new Scanner(System.in);
        Piece Mozditando = Board[Honnan[0]][Honnan[1]];
        Piece Celzott = Board[Hova[0]][Hova[1]];
        if (Mozditando == null || Mozditando.Szin!=Round || (Celzott != null && Mozditando.Szin == Celzott.Szin)) {
            return false;
        }
        switch (Mozditando.Babu) {

            case Pawn -> {
                if (Mozditando.Szin == Piece.Color.White) {
                    if ((Celzott != null && (Hova[0] == Honnan[0] - 1 && Hova[1] == Honnan[1] + 1) || (Hova[0] == Honnan[0] + 1 && Hova[1] == Honnan[1] + 1))   //ütés
                            || (Celzott == null && (Hova[0] == Honnan[0] && Hova[1] == Honnan[1] + 1))   //sima lépés
                            || (Honnan[1] == 1 && (Celzott == null
                            && Board[Honnan[0]][Honnan[1] + 1] == null && (Hova[0] == Honnan[0] && Hova[1] == Honnan[1] + 2)))) {    //kezdő lépés
                        if(Hova[1]==7){
                            System.out.print("Milyen bábut szeretne?");
                            String temp= input.nextLine();
                            Piece.Type csere = Piece.Type.valueOf(temp);                //ha beér a paraszt cserél
                            if (temp!="King"||temp!="Pawn"){Mozditando.Babu=csere;return true;}
                            else{return false;}
                        }
                        return true;
                    }
                } else {
                    if(Hova[1]==0){
                        System.out.print("Milyen bábut szeretne?");
                        String temp= input.nextLine();
                        Piece.Type csere = Piece.Type.valueOf(temp);                //ha beér a paraszt cserél
                        if (temp!="King"||temp!="Pawn"){Mozditando.Babu=csere;return true;}
                        else{return false;}
                    }
                    return (Celzott != null && (Hova[0] == Honnan[0] - 1 &&
                            Hova[1] == Honnan[1] - 1) ||
                            (Hova[0] == Honnan[0] + 1 && Hova[1] == Honnan[1] - 1))   //ütés
                            || (Celzott == null && (Hova[0] == Honnan[0] && Hova[1] == Honnan[1] - 1))   //sima lépés
                            || (Honnan[1] == 6 && (Celzott == null
                            && Board[Honnan[0]][Honnan[1] - 1] == null && (Hova[0] == Honnan[0] && Hova[1] == Honnan[1] - 2))); //kezdő ütés
                }
            }
            case Rook -> {
                if (Hova[0] == Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] + i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0]][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] == Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] - i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0]][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] > Honnan[0] && Hova[1] == Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1]] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] == Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1]] != null) {
                            return false;
                        }
                    }
                }
            }
            case Bishop -> {
                if (Hova[0] > Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] + i == Hova[1] && Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] > Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] - i == Hova[1] && Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] - i == Hova[0] && Honnan[1] - i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0] - i][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] - i == Hova[0] && Honnan[1] + i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0] - i][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                }
            }
            case Knight -> {
                return (
                        (Honnan[0] + 1 == Hova[0] || Honnan[0] - 1 == Hova[0]) && (Honnan[1] + 2 == Hova[1] || Honnan[1] - 2 == Hova[1])
                                || (Honnan[0] + 2 == Hova[0] || Honnan[0] - 2 == Hova[0]) && (Honnan[1] + 1 == Hova[1] || Honnan[1] - 1 == Hova[1])
                );
            }
            case King -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (Honnan[0] + i == Hova[0] && Honnan[1] + j == Hova[1]) {
                            return true;
                        }
                    }
                }
            }
            case Queen -> {
                if (Hova[0] > Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] + i == Hova[1] && Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] > Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] - i == Hova[1] && Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] - i == Hova[0] && Honnan[1] - i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0] - i][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] - i == Hova[0] && Honnan[1] + i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0] - i][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] == Honnan[0] && Hova[1] > Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] + i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0]][Honnan[1] + i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] == Honnan[0] && Hova[1] < Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[1] - i == Hova[1]) {
                            return true;
                        } else if (Board[Honnan[0]][Honnan[1] - i] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] > Honnan[0] && Hova[1] == Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1]] != null) {
                            return false;
                        }
                    }
                } else if (Hova[0] < Honnan[0] && Hova[1] == Honnan[1]) {
                    for (int i = 1; i < 8; i++) {
                        if (Honnan[0] + i == Hova[0]) {
                            return true;
                        } else if (Board[Honnan[0] + i][Honnan[1]] != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void Show() {
        for (int x = 0; x < Board.length; x++) {
            for (Piece[] pieces : Board) {
                if (pieces[7-x] == null) {
                    System.out.print('\u2001');
                } else if (pieces[7-x].Szin == Piece.Color.White) {
                    switch (pieces[7-x].Babu) {
                        case Pawn -> System.out.print('♟');
                        case Rook -> System.out.print('♜');
                        case Bishop -> System.out.print('♝');
                        case Knight -> System.out.print('♞');
                        case King -> System.out.print('♚');
                        case Queen -> System.out.print('♛');
                        default -> System.out.print('\u2001');
                    }
                } else if (pieces[7-x].Szin == Piece.Color.Black) {
                    switch (pieces[7-x].Babu) {
                        case Pawn -> System.out.print('♙');
                        case Rook -> System.out.print('♖');
                        case Bishop -> System.out.print('♗');
                        case Knight -> System.out.print('♘');
                        case King -> System.out.print('♔');
                        case Queen -> System.out.print('♕');
                        default -> System.out.print('\u2001');
                    }

                }
            }
            System.out.println();
        }
        System.out.println(Round);
        Game();
    }
}

class Piece {
    enum Type {Pawn, Rook, Bishop, Knight, King, Queen}

    enum Color {Black, White}

    Type Babu;
    Color Szin;
    public Piece(Type babu, Color szin) {
        Babu = babu;
        Szin = szin;
    } //a bábúk szine és típusa itt van
}