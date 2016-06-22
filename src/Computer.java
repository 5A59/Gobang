class Computer{
	int grade[][][];
	int totalGrade[][];
	String opponent=".",me="o";
//	int shuzu[]={7,35,800,15000,800000,15,400,1800,100000,0};
	int shuzu[]={7,15,400,1800,100000,35,800,15000,800000,0};
	public String computePos(String board[][])
	{
		String ans="";
		int maxGrade=0;
		grade=new int[4][board.length][board.length];
		totalGrade=new int[board.length][board.length];
		setPos1(board,grade[0]);
		setPos2(board,grade[1]);
		setPos3(board,grade[2]);
		setPos4(board,grade[3]);
		ans=addSum(board);
		return ans;
	}

	public void setPlayer(String me, String opponent){
		this.me = me;
		this.opponent = opponent;
	}

	//分别计算横竖撇啦的得分
	public void setPos1(String board[][],int grade1[][])
	{
		for(int i=0;i<board.length;i++){
			for(int j=2;j<board.length-2;j++){
				int summ=0;
				String tag="+";
				for(int k=-2;k<=2;k++){
					if(board[i][j+k]!="+"){
						if(tag=="+"){
							if(tag!=board[i][j+k]){
								tag=board[i][j+k];
								summ++;
							}
						}
						else if(tag!=board[i][j+k]){
							tag="+";
							summ=9;
							break;
						}
						else
							summ++;
					}	
				}
				if(tag==opponent)
					grade1[i][j]=shuzu[summ+4];
				else
					grade1[i][j]=shuzu[summ];
			}
			
		}
	}
	public void setPos2(String board[][],int grade1[][])
	{
		for(int i=2;i<board.length-2;i++){
			for(int j=0;j<board.length;j++){
				int summ=0;
				String tag="+";
				for(int k=-2;k<=2;k++){
					if(board[i+k][j]!="+"){
						if(tag=="+"){
							if(tag!=board[i+k][j]){
								tag=board[i+k][j];
								summ++;
							}
						}
						else if(tag!=board[i+k][j]){
							tag="+";
							summ=9;
							break;
						}
						else
							summ++;
					}	
				}
				if(tag==opponent)
					grade1[i][j]=shuzu[summ+4];
				else
					grade1[i][j]=shuzu[summ];
			}
			
		}
	}public void setPos3(String board[][],int grade1[][])
	{
		for(int i=2;i<board.length-2;i++){
			for(int j=2;j<board.length-2;j++){
				int summ=0;
				String tag="+";
				for(int k=-2;k<=2;k++){
					if(board[i+k][j+k]!="+"){
						if(tag=="+"){
							if(tag!=board[i+k][j+k]){
								tag=board[i+k][j+k];
								summ++;
							}
						}
						else if(tag!=board[i+k][j+k]){
							tag="+";
							summ=9;
							break;
						}
						else
							summ++;
					}	
				}
				if(tag==opponent)
					grade1[i][j]=shuzu[summ+4];
				else
					grade1[i][j]=shuzu[summ];
			}
			
		}
	}
	public void setPos4(String board[][],int grade1[][])
	{
		for(int i=2;i<board.length-2;i++){
			for(int j=2;j<board.length-2;j++){
				int summ=0;
				String tag="+";
				for(int k=-2;k<=2;k++){
					if(board[i+k][j-k]!="+"){
						if(tag=="+"){
							if(tag!=board[i+k][j-k]){
								tag=board[i+k][j-k];
								summ++;
							}
						}
						else if(tag!=board[i+k][j-k]){
							tag="+";
							summ=9;
							break;
						}
						else
							summ++;
					}	
				}
				if(tag==opponent)
					grade1[i][j]=shuzu[summ+4];
				else
					grade1[i][j]=shuzu[summ];
			}
			
		}
	}
	public String addSum(String [][]board)
	{
		String ans="";
		int maxGrade=0;
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++){
				int total=0;
	/*			for(int k=Math.max(0,j-2);k<Math.min(board.length,j+2);k++)
					total+=grade[0][i][k];
				for(int k=Math.max(0,i-2);k<Math.min(board.length,i+2);k++)
					total+=grade[1][k][j];
				for(int k=Math.min(2,Math.min(i,j));k>Math.max(-2,Math.max(i,j)-board.length);k--)
					total+=grade[2][i-k][j-k];
				for(int k=Math.min(2,Math.min(i,board.length-j));k>Math.max(-2,Math.max(j,i-board.length));k--)
					total+=grade[3][i-k][j+k];
					*/
				total+=grade[0][i][j]+grade[1][i][j]+grade[2][i][j]+grade[3][i][j];
				totalGrade[i][j]=total;
				if(total>maxGrade&&board[i][j]=="+"){
					maxGrade=total;
					ans=i+","+j;
				}
			}
		}
		return ans;
	}
	
}