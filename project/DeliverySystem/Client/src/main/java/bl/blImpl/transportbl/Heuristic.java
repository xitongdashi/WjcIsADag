package bl.blImpl.transportbl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bl.blImpl.transportbl.loadhelp.LoadService;

/** 
 * Client//bl.blImpl.transportbl//Heuristic.java
 * @author CXWorks
 * @date 2015年12月17日 下午5:45:07
 * @version 1.0 
 */
public class Heuristic implements LoadService {

	/* (non-Javadoc)
	 * @see bl.blImpl.transportbl.loadhelp.LoadService#algorithm(int[][])
	 */
	/**
	 * 启发式算法，见参考pdf
	 * 其实个人感觉还是贪心没差啊，，，，，
	 * @param src 第一行为商品，接着为长、宽、高、体积\重量\ID
	 * @return
	 */
	@Override
	public Map<Boolean, List<Integer>> algorithm(int[][] src) {
		Map<Boolean, List<Integer>> ans=new HashMap<Boolean, List<Integer>>(2);
		//组合，此步后不考虑高度问题
		int[] used=new int[src.length+1];//不相交集，感觉飞起
		for (int i = 0; i < used.length; i++) {
			used[i]=-1;
		}
		int index=1;
		for (int i = 0; i < src.length-1; i++) {
			if (used[i+1]==-1) {
				used[i+1]=0;
			}
			else {
				continue;
			}
			int sq=src[i][0]*src[i][1];
			int h=src[i][2];
			for (int j = i+1; j < src.length&&h<CAR_H; j++) {
				if (used[j+1]==-1) {
					if (check(sq, src[j], h)) {//可以组合
						if (src[j][1]*src[j][2]<=sq) {
							sq=src[j][1]*src[j][2];
							h+=src[j][0];
							used[j+1]=i+1;
						}
						else {
							sq=src[j][0]*src[j][1];
							h+=src[j][2];
							used[j+1]=i+1;
						}
					}
				}
			}
		}
		//
		LinkedList<Integer> out=new LinkedList<Integer>();
		LinkedList<Integer> head = new LinkedList<Integer>();
		for (int i = 1; i < used.length; i++) {
			if (used[i]==0) {
				head.add(i-1);
			}
			else if (used[i]==-1) {
				out.add(i-1);
			}
		}
		//之后为二维平面问题
		LinkedList<int[]> position=new LinkedList<int[]>();
		int[] init={0,0,CAR_LEN,CAR_WIDTH};
		position.add(init);
		for (int i = 0; i < head.size(); i++) {
			//TODO last step
		}
		//
		return ans;
	}
	
	private boolean check(int sq,int[] info,int h){
		if (info[0]*info[1]<=sq&&h+info[2]<CAR_H) {
			return true;	
		}
		return false;
	}
}
