import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Puzzle15 implements Comparable {
    private int[] puzzle = new int[16];
    private int depth;                    //当前的深度即走到当前状态的步骤
    int cost;                //从起始状态到目标的最小估计值
    private Puzzle15 parent;            //当前状态的父状态
    private int zeroPosition;
    int move = 5;

    Puzzle15() {
        this.zeroPosition = getZeroPosition();
    }

    void setPuzzle(int[] puzzle) {
        this.puzzle = puzzle;
        this.init();
        this.zeroPosition = getZeroPosition();
    }

    void setCost(int cost) {
        this.cost = cost;
    }


    /**
     * 判断当前状态是否为目标状态
     *
     * @param target:target status
     * @return 0,-1,1
     */
    boolean isTarget(Puzzle15 target) {
        return Arrays.equals(puzzle, target.puzzle);
    }

    /**
     * 求f(n) = g(n)+h(n);
     * 初始化状态信息
     */

    void init() {

        int misPosition = 0;
        for (int i = 0; i < 16; i++) {
            if (puzzle[i] != 0) {
                int a = abs((i) / 4 - (puzzle[i] - 1) / 4);
                int b = abs((i) % 4 - (puzzle[i] - 1) % 4);
                misPosition += (a + b);
            } else {
                misPosition += (6 - i / 4 - i % 4);
            }
        }

        //到目标的最小估计
        if (this.parent == null) {
            this.depth = 0;
        } else {
            depth = parent.depth + 1;
        }
        this.cost = depth + misPosition;
    }

    /**
     * 求逆序值并判断是否有解
     *
     * @param target:
     * @return 有解：true 无解：false
     */
    boolean isSolvable(Puzzle15 target) {
        //去除0
        int a[] = new int[15];
        int b[] = new int[15];
        for (int i = 0, j = 0; i < 15; ) {
            if (puzzle[j] == 0) {
                j++;
                continue;
            }
            a[i] = puzzle[j];
            i++;
            j++;
        }
        for (int i = 0, j = 0; i < 15; ) {
            if (target.puzzle[j] == 0) {
                j++;
                continue;
            }
            b[i] = target.puzzle[j];
            i++;
            j++;
        }
        //求逆序数
        int reverse = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] > a[i])
                    reverse++;
                if (b[j] > b[i])
                    reverse++;
            }
        }
        return reverse % 2 == 0;
    }

    @Override
    public int compareTo(Object o) {
        Puzzle15 c = (Puzzle15) o;
        return this.cost - c.cost;//默认排序为f(n)由小到大排序
    }

    /**
     * @return 返回0在八数码中的位置
     */
    private int getZeroPosition() {
        int position = -1;
        for (int i = 0; i < 16; i++) {
            if (puzzle[i] == 0) {
                position = i;
            }
        }
        return position;
    }

    /**
     * @param open 状态集合
     * @return 判断当前状态是否存在于open表中
     */
    private int isContains(ArrayList<Puzzle15> open) {
        for (int i = 0; i < open.size(); i++) {
            if (Arrays.equals(open.get(i).puzzle, puzzle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return 小于3的不能上移返回false
     */
    boolean isMoveUp() {
        return this.zeroPosition > 3;
    }

    /**
     * @return 大于6返回false
     */
    boolean isMoveDown() {
        return this.zeroPosition < 12;
    }

    /**
     * @return 0，3，6返回false
     */
    boolean isMoveLeft() {
        return this.zeroPosition % 4 != 0;
    }

    /**
     * @return 2，5，8不能右移返回false
     */
    boolean isMoveRight() {
        return (this.zeroPosition) % 4 != 3;
    }

    /**
     * @param move 0：上，1：下，2：左，3：右
     * @return 返回移动后的状态
     */
    Puzzle15 moveUp(int move) {
        Puzzle15 temp = new Puzzle15();
        temp.puzzle = puzzle.clone();
        if (this.zeroPosition == -1) {
            this.zeroPosition = getZeroPosition();    //0的位置
        }
        temp.zeroPosition = getZeroPosition();
        int p = 0;                            //与0换位置的位置
        switch (move) {
            case 0:
                p = zeroPosition - 4;
                temp.puzzle[zeroPosition] = puzzle[p];
                break;
            case 1:
                p = this.zeroPosition + 4;
                temp.puzzle[zeroPosition] = puzzle[p];
                break;
            case 2:
                p = this.zeroPosition - 1;
                temp.puzzle[zeroPosition] = puzzle[p];
                break;
            case 3:
                p = this.zeroPosition + 1;
                temp.puzzle[zeroPosition] = puzzle[p];
                break;
        }
        temp.puzzle[p] = 0;
        temp.zeroPosition = p;
        return temp;
    }

    /**
     * 按照十五数码的格式输出
     */
    private void print() {
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 3) {
                System.out.println(this.puzzle[i]);
            } else {
                System.out.printf("%d\t", this.puzzle[i]);
            }
        }
    }

    /**
     * 逆序输出
     */
    void printRoute() {
        Puzzle15 temp;
        int count = 0;
        temp = this;
        while (temp != null) {
            temp.print();
            System.out.println("---------------");
            temp = temp.parent;
            count++;
        }
        System.out.println("步骤数：" + (count - 1));
    }

    /**
     * @param open   open表
     * @param close  close表
     * @param parent 父状态
     */
    void operation(ArrayList<Puzzle15> open, ArrayList<Puzzle15> close, Puzzle15 parent, int move) {
        if (this.isContains(close) == -1) {
            int position = this.isContains(open);
            if (position == -1) {
                this.parent = parent;
                this.init();
                this.zeroPosition = getZeroPosition();
                this.move = move;
                open.add(this);
            } else {
                update(open, parent, move, position);
            }
        } else {
            int position = this.isContains(close);
            update(close, parent, move, position);
        }

    }

    private void update(ArrayList<Puzzle15> list, Puzzle15 parent, int move, int position) {
        if (this.cost < list.get(position).cost) {
            list.get(position).setCost(this.cost);
            list.get(position).parent = parent;
            list.get(position).move = move;
        }
    }
}