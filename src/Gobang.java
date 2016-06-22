import java.util.Scanner;

public class Gobang{

	public static void main(String[] args){
		Gobang go = new Gobang();
		go.run();
	}

	public void run(){
		boolean player1First = true;

		String[][] go = new String[15][15];
		String you = "*";
		String me = "o";
		for (int i = 0; i < 15; i ++){
			for (int j = 0; j < 15; j ++){
				go[i][j] = "+";
			}
		}
		Player player = new Player(me, you);
//		Player player2 = new Player(you, me){
//			Computer computer = new Computer();
//			@Override
//			public String nextPos(String[][] go){
//				computer.setPlayer(you, me);
//				return computer.computePos(go);
//			}
//		};
		Player player2 = new Player(you, me){
			@Override
			public String nextPos(String[][] go){
				Scanner scan = new Scanner(System.in);
				String res = scan.nextLine();
			    return res;
			}
		};
//		Player player2 = new Player(you, me);
		Judgment judge = new Judgment(me, you);
		int res = 0;
		while (res == 0){
			try{
//				Thread.sleep(5000);
			}catch (Exception e){
				
			}
			String pos = null;
			String[] p = null;
			if (player1First){
				pos = player.nextPos(go);
				p = pos.split(",");
				go[Integer.parseInt(p[0])][Integer.parseInt(p[1])] = me;
				System.out.println("player1 is  " + pos);
				res = judge.result(go);
				judge.outPut(go);
				System.out.println("judge res1 is " + res);
				if (res != 0){
					break;
				}
			}
			if (!player1First){
				player1First = true;
			}
			pos = player2.nextPos(go);
			System.out.println("player2 is  " + pos);
			p = pos.split(",");
			go[Integer.parseInt(p[0])][Integer.parseInt(p[1])] = you;
			res = judge.result(go);
			System.out.println("judge res2 is " + res);
			judge.outPut(go);
		}
		if (res == 1){
			System.out.println(me + "  win");
		}else {
			System.out.println(you + "  win");
		}
	}

	public static class Judgment{
		private int SUCCESS = 4;
		private Finder finder1;
		private Finder finder2;
		private String player1;
		private String player2;

		public Judgment(String player1, String player2){
			this.player1 = player1;
			this.player2 = player2;
			finder1 = new Finder(player1, player2);
			finder2 = new Finder(player2, player1);
		}
		/*
		 * return 1 : player1 胜利 2 : player2 胜利　０ : 比赛继续
		 */
		public int result(String[][] go){
			int[] pos = {0, 0};
			for (int i = 0; i < go.length; i ++){
				for (int j = 0; j < go[0].length; j ++){
					pos[0] = i;
					pos[1] = j;
					if (go[i][j].equals(player1)){
						finder1.setPlayer(player1, player2);
						int res = finder1.lookDown(go, pos);
						int res1 = finder1.lookRight(go, pos);
						int res2 = finder1.lookLeftDown(go, pos);
						int res3 = finder1.lookRightDown(go, pos);
						if (Math.abs(res) >= SUCCESS || Math.abs(res1) >= SUCCESS
								|| Math.abs(res2) >= SUCCESS || Math.abs(res3) >= SUCCESS){
							return 1;
						}
					}else if (go[i][j].equals(player2)){
						finder2.setPlayer(player2, player1);
						int res = finder2.lookDown(go, pos);// + Math.abs(finder2.lookUp(go, pos));
						int res1 = finder2.lookRight(go, pos);// + Math.abs(finder2.lookLeft(go, pos));
						int res2 = finder2.lookLeftDown(go, pos);// + Math.abs(finder2.lookLeftUp(go, pos));
						int res3 = finder2.lookRightDown(go, pos);// + Math.abs(finder2.lookRightUp(go, pos));
						if (Math.abs(res) >= SUCCESS || Math.abs(res1) >= SUCCESS
								|| Math.abs(res2) >= SUCCESS || Math.abs(res3) >= SUCCESS){
							return 2;
						}
					}
				}
			}

			return 0;
		}

		public void outPut(String[][] go){
			for (int i = 0; i < go.length; i ++){
				System.out.print(i % 10 + " ");
			}
			System.out.println("");
			for (int i = 0; i < go.length; i ++){
				for (int j = 0; j < go[0].length; j ++){
					System.out.print(go[i][j] + " ");
				}
				System.out.println("" + (i % 10) + "");
			}
			System.out.println("");
		}
	}

	public static class Player{
		private String me;
		private String you;
		private Finder finder;

		public Player(String me, String you){
			this.me = me;
			this.you = you;
			finder = new Finder(me, you);
		}

		public String nextPos(String[][] go){
			int max = 0;
			int[] resPos = {0, 0};
			int[] pos = {0, 0};
			int[] val = {0, 0, 0, 0};
			for (int i = 0; i < go.length; i ++){
				for (int j = 0; j < go[0].length; j ++){
					if (go[i][j].equals(me) || go[i][j].equals(you)){
						continue;
					}
					// 计算自己的有利位置
					pos[0] = i;
					pos[1] = j;
					finder.setPlayer(me, you);
					val[0] = calculate(finder.lookDown(go, pos), finder.lookUp(go, pos));
					val[1] = calculate(finder.lookLeft(go, pos), finder.lookRight(go, pos));
					val[2] = calculate(finder.lookLeftUp(go, pos), finder.lookRightDown(go, pos));
					val[3] = calculate(finder.lookRightUp(go, pos), finder.lookLeftDown(go, pos));

					int tmp = calValue(val);

					//计算对方有利位置
					pos[0] = i;
					pos[1] = j;
					finder.setPlayer(you, me);
					val[0] = calculate(finder.lookDown(go, pos), finder.lookUp(go, pos));
					val[1] = calculate(finder.lookLeft(go, pos), finder.lookRight(go, pos));
					val[2] = calculate(finder.lookLeftUp(go, pos), finder.lookRightDown(go, pos));
					val[3] = calculate(finder.lookRightUp(go, pos), finder.lookLeftDown(go, pos));

					int tmp1 = calValue(val);
					tmp = evaluate(tmp, tmp1);
					if (max < tmp){
						max = tmp;
						resPos[0] = i;
						resPos[1] = j;
					}
				}
			}
			if (max == 0){
				int x = 5;
				int y = 5;
				while (go[x][y].equals(me) || go[x][y].equals(you)){
					x ++;
				}
				return "" + x + "," + y;
			}
			return "" + resPos[0] + "," + resPos[1];
		}

		/*
		 * 根据对方和自己的权值进行评估
		 */
		public int evaluate(int me, int you){
//			return me > you ? me : you;
			if (me >= 90){
				return me + 100;
			}
			return you > 60 ? (100 + you) : me;
		}

		public int calculate(int one, int other){
			int res = (one < 0 || other < 0) ? -1 : 1;
			return res * (Math.abs(one) + Math.abs(other) + 1);
		}

		public int calValue(int[] val){
			int max = 0;
			for (int i = 0; i < val.length; i ++){
				for (int j = i + 1; j < val.length; j ++){
					int tmp = calTable(val[i], val[j]);
					max = max > tmp ? max : tmp;
				}
			}
			return max;
		}

		public int calTable(int a, int b){
			if (Math.abs(a) == 5 || Math.abs(b) == 5){
				return 100;
			}
			if (a == 4 || b == 4 || a == -4 && b == -4 || a == -4 && b == 3
					|| b == -4 && a == 3){
				return 90;
			}
			if (a == 3 && b == 3){
				return 80;
			}
			if (a == -3 && b == 3 || a == 3 && b == -3){
				return 70;
			}
			if (a == -4 || b == -4){
				return 60;
			}
			if (a == 3 || b == 3){
				return 60;
			}
			if (a == 2 && b == 2){
				return 40;
			}
			if (a == -3 || b == -3){
				return 30;
			}
			if (a == 2 || b == 2){
				return 20;
			}
			if (a == -2 || b == -2){
				return 10;
			}
			return 0;
		}
	}

	public static class Finder{
		private String me;
		private String you;
		private DownNext down;
		private UpNext up;
		private LeftNext left;
		private RightNext right;
		private LeftUpNext leftUp;
		private LeftDownNext leftDown;
		private RightUpNext rightUp;
		private RightDownNext rightDown;

		public Finder(String me, String you){
			this.me = me;
			this.you = you;
			down = new DownNext();
			up = new UpNext();
			left = new LeftNext();
			right = new RightNext();
			leftUp = new LeftUpNext();
			leftDown = new LeftDownNext();
			rightUp = new RightUpNext();
			rightDown = new RightDownNext();
		}

		public void setPlayer(String me, String you){
			this.me = me;
			this.you = you;
		}

		/*
		 *@ para pos 要查看的位置 pos[0] x pos[1] y
		 *@ return 1:自身的颜色 -1:对方的颜色 0:没有棋子 2:超出棋盘边界
		 */
		public int lookAt(String[][] go, int[] pos){
			if (pos == null || pos.length != 2 || pos[0] >= go.length || pos[0] < 0 
					|| pos[1] >= go[0].length || pos[1] < 0){
				return 2;
			}
			if (go[pos[0]][pos[1]].equals(me)){
				return 1;
			}
			if (go[pos[0]][pos[1]].equals(you)){
				return -1;
			}
			return 0;
		}

		/*
		 *@ para 
		 *@ return 整数代表活 负数代表死
		 */
		public int lookNext(String[][] go, int[] pos, Next next){
			int[] tmpPos = new int[2];
			tmpPos[0] = pos[0];
			tmpPos[1] = pos[1];

			int count = 0;
			next.next(tmpPos);
			int res = 0;
			while ((res = lookAt(go, tmpPos)) == 1){
				count ++;
				next.next(tmpPos);
			}
			if (res == -1 || res == 2){
				count = -count;
			}
			return count;
		}

		public int lookDown(String[][] go, int[] pos){
			return lookNext(go, pos, down);
		}

		public int lookUp(String[][] go, int[] pos){
			return lookNext(go, pos, up);
		}

		public int lookLeft(String[][] go, int[] pos){
			return lookNext(go, pos, left);
		}

		public int lookRight(String[][] go, int[] pos){
			return lookNext(go, pos, right);
		}

		public int lookLeftUp(String[][] go, int[] pos){
			return lookNext(go, pos, leftUp);
		}

		public int lookLeftDown(String[][] go, int[] pos){
			return lookNext(go, pos, leftDown);
		}

		public int lookRightUp(String[][] go, int[] pos){
			return lookNext(go, pos, rightUp);
		}

		public int lookRightDown(String[][] go, int[] pos){
			return lookNext(go, pos, rightDown);
		}
	}

	//选择下一步　例如 x++,y 或者 x, y++
	public static interface Next{
		void next(int[] curPos);
	}

	public static class DownNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[0] ++;
		}
	}

	public static class UpNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[0] --;
		}
	}

	public static class LeftNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[1] --;
		}
	}

	public static class RightNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[1] ++;
		}
	}

	public static class LeftUpNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[0] --;
			curPos[1] --;
		}
	}

	public static class RightUpNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[1] ++;
			curPos[0] --;
		}
	}

	public static class LeftDownNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[1] --;
			curPos[0] ++;
		}
	}

	public static class RightDownNext implements Next{
		public void next(int[] curPos){
			if (curPos == null || curPos.length != 2){
				return ;
			}
			curPos[0] ++;
			curPos[1] ++;
		}
	}
}