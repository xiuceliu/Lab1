import java.util.*;

class node {
	int now;
	String str;
	node(int now, String str) {
		this.now = now;
		this.str = str;
	}
}

public class lab1 {
	
	static boolean Judge(String s) {  //判断表达式是否为0
		String tmp = "";
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				tmp += s.charAt(i);
			} else {
				if (!tmp.equals("")) {
					if (Integer.parseInt(tmp) == 0) {
						return true;
					}
				} 
				tmp = "";
			}
		}
		if (!tmp.equals("")) {
			if (Integer.parseInt(tmp) == 0) {
				return true;
			}
		}
		return false;
	}
	
	static String preStr(String s) {  //忽略乘号
		String tmp = "";
		boolean jud = true;
		while (jud) {
			tmp = "";
			int flag = -1;
			for (int i = 0; i < s.length(); i++) {
				if (i == 0 || i == s.length() - 1) continue;
				if (s.charAt(i) == '*') {
					if (Character.isDigit(s.charAt(i - 1)) && Character.isDigit(s.charAt(i + 1))) {
						flag = i;
						break;
					}
				}
			}//找到这样的乘号，乘号两边是数字
			if (flag == -1) break;
			int fn = 0, fg = s.length() - 1;
			for (int i = flag - 1; i >= 0; i--) {
				if (!Character.isDigit(s.charAt(i))) {
					fn = i + 1; break;
				}
			}
			for (int i = flag + 1; i < s.length() - 1; i++) {
				if (!Character.isDigit(s.charAt(i))) {
					fg =  i - 1; break;
				}
			}//离乘号左右最近的非数字
			String s1 = "", s2 = "";
			for (int i = fn; i < flag; i++) s1 += s.charAt(i);
			for (int i = flag + 1; i <= fg; i++) s2 += s.charAt(i);
			int now = Integer.parseInt(s1);
			now *= Integer.parseInt(s2);//数字存入，转int
			for (int i = 0; i < fn; i++) tmp += s.charAt(i);
			tmp += String.valueOf(now);
			for (int i = fg + 1; i < s.length(); i++) tmp += s.charAt(i);
			s = tmp;//其余的元素照原样复制
		}
		tmp = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '*') tmp += s.charAt(i);
		}//删除所有乘号
		return tmp;
	}//效果：将所有digit×digit类型的子串合并为一个数字，并且删除所有乘号
	
	static int Pre(String ori, node s[]) {  //预处理分割+号
		int n = 0;
		String tmp = "";
		for (int i = 0; i < ori.length(); i++) {
			if (ori.charAt(i) != '+') {
				tmp += ori.charAt(i);
			} else {
				s[++n] = new node(1, tmp);
				tmp = "";
			}
		}//基于+号分割表达式，存入结构体数组中的String
		if (!tmp.equals("")) {
			s[++n] = new node(1, tmp);
			tmp = "";
		}//处理末尾的一个子项
		return n;
	}//效果：将子项放入结构体数组中
	
	static void Combination(int n, node s[]) {   //把数字合并
		for (int i = 1; i <= n; i++) {
			String digit = "";
			String character = "";
			for (int j = 0; j < s[i].str.length(); j++) {//对于每一个子项
				if (Character.isDigit(s[i].str.charAt(j))) {
					digit += s[i].str.charAt(j);//数字载入
				} else {
					character += s[i].str.charAt(j);//字母载入
					if (!digit.equals(""))
						s[i].now *= Integer.parseInt(digit);//每次遇到一次非数字，预处理数字和原值相乘
					digit = "";
				}
			}
			if (!digit.equals("")) {
				s[i].now *= Integer.parseInt(digit);//最后一个项目可能是数字
			}
			s[i].str = character;//字母放最后
		}
	}//效果：表达式形式转为：digit （*）character
	
	public String derivative(String now) {  //求导\
		String ori = "1*x*y+2*x";
		int all = 9;
		int len = now.length();
		if (len <= 5 || len >= 7) {
			return "Error";
		}
		char c = now.charAt(5);
		int flag = 0;
		for (int i = 0; i < all; i++) {
			if (ori.charAt(i) == c) flag++;
		}
		if (flag == 0) return "Error";
		String tmp = "";
		for (int i = 0; i < all; i++) {
			if (ori.charAt(i) == c) {
				tmp += '1';
			} else {
				tmp += ori.charAt(i);
			}
		}
		if (flag >= 1)
		System.out.println(tmp);
		return tmp;
	}
	
	static void Print(int n, node s[]) { //输出
		for (int i = 1; i <= n; i++) {
			if (i != 1) System.out.print("+");
			if (s[i].now == 0) continue;
			System.out.print(s[i].now);
			boolean [] vis = new boolean[300];
			for (int j = 0; j < s[i].str.length(); j++) {
				char c = s[i].str.charAt(j);
				if (vis[c]) continue;
				vis[c] = true;
				int flag = 1;
				for (int k = j + 1; k < s[i].str.length(); k++) {
					if (s[i].str.charAt(k) == c) flag++;
				}
				System.out.print("*" + c);
				if (flag >= 2) {
					System.out.print("^" + flag);
				}//
			}
			
		}
		System.out.print("\n");
	}
	
	static void simplify(String Choice, int n, node s[]) {   //表达式求值
		int k = 10;
		while (k < Choice.length() - 1) {
			char c = Choice.charAt(k);
			String tn = "";
			k += 2;
			for (int i = k; i < Choice.length(); i++) {
				k = i + 1;
				if (Character.isDigit(Choice.charAt(i))) {
					tn += Choice.charAt(i);
				} else {
					break;
				}
			} //截取数字
			int now = Integer.parseInt(tn);
			for (int i = 1; i <= n; i++) {
				String tmp = "";
				for (int j = 0; j < s[i].str.length(); j++) {
					if (s[i].str.charAt(j) == c) {
						s[i].now *= now;
					} else {
						tmp += s[i].str.charAt(j);
					}
				}//字符相等替换，并且数字相乘
				s[i].str = tmp;
			}
		}
	}
	
	static boolean Check(String ori) {  //查看表达式是否合法
		for (int i = 0; i < ori.length(); i++) {
			if (ori.charAt(i) >= '0' && ori.charAt(i) <= '9') continue;
			if (ori.charAt(i) >= 'a' && ori.charAt(i) <= 'z') continue;
			if (ori.charAt(i) == '+' || ori.charAt(i) == '*') continue;
			return true;// 合法字符检查
			
		}
		if (ori.charAt(0) == '+' || ori.charAt(ori.length() - 1) == '+') return true;
		if (ori.charAt(0) == '*' || ori.charAt(ori.length() - 1) == '*') return true;
		for (int i = 0; i < ori.length() - 1; i++) {
			if (ori.charAt(i) == '+' || ori.charAt(i) == '*') {
				if (ori.charAt(i + 1) == '+' || ori.charAt(i + 1) == '*') return true;
			}
		}//+ *位置判断表达式正误
		if (Judge(ori)) return true;//表达式中不能有0
		return false;
	}
	
	static boolean CheckDer(String Choice, int n, node s[]) {   //查看求导表达式是否合法
		if (Choice.length() !=  6) return false;
		String tmp = "";
		for (int i = 0; i <= 4; i++) {
			tmp += Choice.charAt(i);
		}
		if (!tmp.equals("!d/d ")) return false;
		boolean flag = false;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < s[i].str.length(); j++) {
				if (Choice.charAt(5) == s[i].str.charAt(j)) flag = true;//表达式是否有这个元素x
			}
		}
		if (!flag) return false;
		return true;
	}
	
	static boolean CheckSim(String Choice, int n, node s[]) { //查看化简表达式是否合法
		if (Choice.length() < 10) return false;
		String tmp = "";
		for (int i = 0; i <= 9; i++) {
			tmp += Choice.charAt(i);
		}
		if (!tmp.equals("!simplify ")) return false;
		int i = 10;
		while (i < Choice.length()) {
			if (i + 2 >= Choice.length()) return false;
			if (Choice.charAt(i) < 'a' || Choice.charAt(i) > 'z') return false;
			if (Choice.charAt(i + 1) != '=') return false;
			if (Choice.charAt(i + 2) < '0' || Choice.charAt(i + 2) > '9') return false;
			i += 2;
			for (int j = i; j < Choice.length(); j++) {
				i = j + 1; 
				if (Choice.charAt(j) < '0' || Choice.charAt(j) > '9') {
					if (Choice.charAt(j) != ' ') return false;
					break;
				}
			}
		}
		if (Judge(Choice)) return false;
		return true;
	}
	
	
	static int expression(int n, node s[]) {   //合并同类项
		boolean jud = true;
		while (jud) {
			int flag = -1;
			int ptr = -1;
			for (int i = 1; i <= n; i++) {
				if (s[i].str.equals("")) {
					flag = i;
					break;
				}
			}
			if (flag == -1) break;
			for (int i = flag + 1; i <= n; i++) {
				if (s[i].str.equals("")) {
					ptr = i;
					break;
				}
			}
			if(ptr == -1) break;
			n--;
			s[flag].now += s[ptr].now;
			for (int i = ptr; i <= n; i++) {
				s[i].now = s[i + 1].now; 
				s[i].str = s[i + 1].str;
			}
		}
		return n;//合并常数项
	}
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		String ori = in.nextLine();   //读入表达式
		if (Check(ori)) {   //判断表达式是否合法
			System.out.println("Error!");
			return;
		}
		System.out.println("Success!");  
		ori = preStr(ori);   //效果：将所有digit×digit类型的子串合并为一个数字，并且删除所有乘号
		node [] s = new node[205];
		int n = Pre(ori, s);//预处理分割+号
		Combination(n, s);//效果：表达式形式转为：digit （*）character
		while (in.hasNext()) {
			String Choice = in.nextLine();
			if (CheckDer(Choice, n, s)) {//是否是求导？
				//i = derivative(n, Choice.charAt(5), s); //求导
			} else if (CheckSim(Choice, n, s)) {//是否是求值？
				simplify(Choice, n, s);  //求值
			} else {
				System.out.println("Error!");
			}
			n = expression(n, s); //化简
			Print(n, s);  //输出
		}
	}
}