package fillomino;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Fillomino {

    static Scanner in;
    static int z = 0;
    static Set<Board> DFSChecked = new HashSet<>();
    static Set<Board> BFSchecked = new HashSet<>();
    static Queue<Board> BFSqueue = new LinkedList<>();
    static Set<Board> checked = new HashSet<>();

    public static void main(String[] args) {
        in = new Scanner(System.in);
        System.out.print("Enter the width for the board =");
        int width = in.nextInt();
        System.out.print("Enter the height for the board=");
        int height = in.nextInt();
        Board b = new Board(width, height);
        b.enterBoard();
        BFS(b);
        System.out.println("Solving using DFS Algorithm - ");
        DFS(b);
        A_Star(b);
    }

    public static boolean DFS(Board b) {
        DFSChecked.add(b);
        //  b.printBoard();
        if (b.checkSolve()) {
            System.out.println("Number of Iterations for DFS Algorithm : " + z);
            z = 0;
            System.out.println("*****************The solution using DFS is :*************");
            b.printBoard();
            return true;
        } else {
            for (Board nextStep : b.getPossibleNextStep()) {
                if (!DFSChecked.contains(nextStep)) {
                    if (DFS(nextStep)) {
                        return true;
                    }
                }
            }
            z++;
            return false;
        }
    }

    public static boolean BFS(Board B) {
        System.out.println("Solving using BFS Algorithm - ");
        BFSqueue.offer(B);
        while (!BFSqueue.isEmpty()) {
            z++;
            Board b = BFSqueue.poll();
            if (BFSchecked.contains(b)) {
                continue;
            }
            BFSchecked.add(b);

            if (b.checkSolve()) {
                System.out.println("Number of Iterations for BFS Algorithm : " + z);
                z = 0;
                System.out.println("*****************The solution using BFS is :*************");
                b.printBoard();
                return true;
            } else {
                //b.printBoard();
                for (Board nextStep : b.getPossibleNextStep()) {
                    BFSqueue.offer(nextStep);
                }
            }
        }
        System.out.println("fail");
        return false;
    }

    public static void A_Star(Board b) {
        System.out.println("Solving using A* Algorithm - ");
        PriorityQueue<Board> queue = new PriorityQueue<>((Board i, Board j) -> {
            if (i.CalceFalseAndEmptyCells() + i.CalcToCompleteZone() > j.CalceFalseAndEmptyCells() + j.CalcToCompleteZone()) {
                return 1;
            } else if (i.CalceFalseAndEmptyCells() + i.CalcToCompleteZone() < j.CalceFalseAndEmptyCells() + j.CalcToCompleteZone()) {
                return -1;
            } else {
                return 0;
            }
        });

        Set<Board> explored = new HashSet<>();

        queue.add(b);
        do {
            z++;
            Board current = queue.poll();
            explored.add(current);

            if (current.checkSolve()) {
                System.out.println("Number of Iterations for A* Algorithm : " + z);
                z = 0;
                System.out.println("*****************The solution using A* is :*************");
                current.printBoard();
                return;
            }
            for (Board child : current.getPossibleNextStep()) {
                if (!queue.contains(child) && !explored.contains(child)) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
        System.out.println("there is not any solve");
    }

//    public static void AStarnew(Board b) {
//        PriorityQueue<Board> queue = new PriorityQueue<>((i, j) -> {
//            int f1 = i.CalceFalseAndEmptyCells() + i.CalcToCompleteZone();
//            int f2 = j.CalceFalseAndEmptyCells() + j.CalcToCompleteZone();
//            return Integer.compare(f1, f2);
//        });
//
//        queue.add(b);
//        checked.add(b);
//
//        while (!queue.isEmpty()) {
//            System.out.println(z++);
//            Board current = queue.poll();
//
//            if (current.checkSolve()) {
//                System.out.println("*****************the solve is :*************");
//                current.printBoard();
//                return;
//            }
//
//            for (Board child : current.getPossibleNextStep()) {
//                if (!checked.contains(child)) {
//                    queue.add(child);
//                    checked.add(child);
//                }
//            }
//        }
//        System.out.println("There is no solution.");
//    }
}
