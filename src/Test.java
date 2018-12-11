import java.util.ArrayList;

public class Test {

    public static void main(String args[]) {
        //定义open表
        ArrayList<Puzzle15> open = new ArrayList<>();
        ArrayList<Puzzle15> close = new ArrayList<>();
        Puzzle15 start = new Puzzle15();
        Puzzle15 target = new Puzzle15();

        //int startPuzzle[] = {2, 1, 5, 4, 9, 6, 3, 8, 13, 15, 14, 10, 11, 0, 12, 7};
        //int startPuzzle[] = { 2, 3, 4, 8, 1, 6, 7, 0, 5, 10, 11,12, 9, 13, 14, 15 };
        int startPuzzle[] = {5, 1, 2, 4, 9, 6, 3, 8, 13, 15, 10, 11, 14, 0, 7, 12};
        int targetPuzzle[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        start.setPuzzle(startPuzzle);
        target.setPuzzle(targetPuzzle);
        long startTime = System.currentTimeMillis();   //获取开始时间
        if (start.isSolvable(target)) {
            //初始化初始状态
            start.init();
            open.add(start);
            while (!open.isEmpty()) {
                int move;
                // Collections.sort(open);//按照evaluation的值排序
                int mix = 0;
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).cost < open.get(mix).cost) mix = i;
                }
                Puzzle15 best = open.get(mix);    //从open表中取出最小估值的状态并移除open表
                open.remove(mix);
                close.add(best);
                if (best.isTarget(target)) {
                    long end = System.currentTimeMillis(); //获取结束时间
                    //输出
                    best.printRoute();
                    System.out.println("程序运行时间： " + (end - startTime) + "ms");
                    System.exit(0);
                }


                //由best状态进行扩展并加入到open表中
                //0的位置上移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveUp()) {

                    if (best.move != 1) {
                        move = 0;
                        Puzzle15 up = best.moveUp(move);
                        up.operation(open, close, best, move);

                    }
                }
                //0的位置下移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveDown()) {

                    if (best.move != 0) {
                        move = 1;
                        Puzzle15 up = best.moveUp(move);
                        up.operation(open, close, best, move);

                    }
                }
                //0的位置左移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveLeft()) {

                    if (best.move != 3) {
                        move = 2;
                        Puzzle15 up = best.moveUp(move);
                        up.operation(open, close, best, move);
                    }
                }
                //0的位置右移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveRight()) {

                    if (best.move != 2) {
                        move = 3;
                        Puzzle15 up = best.moveUp(move);
                        up.operation(open, close, best, move);
                    }
                }
            }
        } else
            System.out.println("没有解，请重新输入。");
    }
}