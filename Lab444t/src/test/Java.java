package test;

import java.util.Scanner;
/**
 * .
 * @author dream.
 *
 */
public class Java {

  static boolean judge(String str) {
    // 判断表达式是否为0
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '0') {
        return true;
      }
    }

    // String tmp = "";
    // for (int i = 0; i < s.length(); i++) {
    // if (Character.isDigit(s.charAt(i))) {
    // tmp += s.charAt(i);
    // } else {
    // if (!tmp.equals("")) {
    // if (Integer.parseInt(tmp) == 0) {
    // return true;
    // }
    // }
    // tmp = "";
    // }
    // }
    // if (!tmp.equals("")) {
    // if (Integer.parseInt(tmp) == 0) {
    // return true;
    // }
    // }
    return false;
  }

  static String preStr(String str1) { 
    // 忽略乘号
    String tmp = "";
    boolean jud;
    jud=true;
    while (jud) {
      tmp = "";
      int flag = -1;
      for (int i = 0; i < str1.length(); i++) {
        if (i == 0 || i == str1.length() - 1) {
          continue;
        }
        if (str1.charAt(i) == '*'&&Character.isDigit(str1.charAt(i - 1))&&Character.isDigit(str1.charAt(i + 1))) {
          flag = i;
          break;
        }
      } // 找到这样的乘号，乘号两边是数字
      if (flag == -1) {
        break;
      }
      int fn = 0;
      int fg = str1.length() - 1;
      for (int i = flag - 1; i >= 0; i--) {
        if (!Character.isDigit(str1.charAt(i))) {
          fn = i + 1;
          break;
        }
      }
      for (int i = flag + 1; i < str1.length() - 1; i++) {
        if (!Character.isDigit(str1.charAt(i))) {
          fg = i - 1;
          break;
        }
      } // 离乘号左右最近的非数字
      String s1 = "";
      String s2 = "";
      for (int i = fn; i < flag; i++) {
        s1 =s1+str1.charAt(i);
      }
      for (int i = flag + 1; i <= fg; i++) {
        s2 += str1.charAt(i);
      }
      int now = Integer.parseInt(s1);
      now *= Integer.parseInt(s2);// 数字存入，转int
      for (int i = 0; i < fn; i++) {
        tmp += str1.charAt(i);
      }
      tmp += String.valueOf(now);
      for (int i = fg + 1; i < str1.length(); i++) {
        tmp += str1.charAt(i);
      }
      str1 = tmp;// 其余的元素照原样复制
    }
    tmp = "";
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != '*') {
        tmp += str1.charAt(i);
      }
    } // 删除所有乘号
    return tmp;
  }
  // 效果：将所有digit×digit类型的子串合并为一个数字，并且删除所有乘号

  static int pre(String ori, node[] str) { 
    // 预处理分割+号
    int num = 0;
    String tmp = "";
    for (int i = 0; i < ori.length(); i++) {
      if (ori.charAt(i) == '+') {
        str[++num] = new node(1, tmp);
        tmp = "";
    	  
      } else {
    	tmp += ori.charAt(i);
      }
    } // 基于+号分割表达式，存入结构体数组中的String
    if (!tmp.equals("")) {
      str[++num] = new node(1, tmp);
      tmp = "";
    } // 处理末尾的一个子项
    return num;
  }
  // 效果：将子项放入结构体数组中

  static void combination(int num, node[] s0) { 
    // 把数字合并
    for (int i = 1; i <= num; i++) {
      String digit = "";
      String character = "";
      for (int j = 0; j < s0[i].str.length(); j++) {
        // 对于每一个子项
        if (Character.isDigit(s0[i].str.charAt(j))) {
          digit += s0[i].str.charAt(j);// 数字载入
        } else {
          character += s0[i].str.charAt(j);// 字母载入
          if (!digit.equals("")) {
            s0[i].now *= Integer.parseInt(digit);// 每次遇到一次非数字，预处理数字和原值相乘
          }
          digit = "";
        }
      }
      if (!digit.equals("")) {
        s0[i].now *= Integer.parseInt(digit);// 最后一个项目可能是数字
      }
      s0[i].str = character;// 字母放最后
    }
  }
  // 效果：表达式形式转为：digit （*）character
  
  static void derivative(int num, char ch, node[] s0) { // 求导
    for (int i = 1; i <= num; i++) {
      int flag = 0;
      String tmp = "";
      for (int j = 0; j < s0[i].str.length(); j++) {
        if (s0[i].str.charAt(j) == ch) {
          flag++;// 找到欲求元素
        }
        if (s0[i].str.charAt(j) == ch && flag == 1) {
          continue;
        } else {
          tmp += s0[i].str.charAt(j);
        }
      }
      if (flag >= 1) {
        s0[i].now *= flag;// 求导后幂相乘
        s0[i].str = tmp;
      } else {
        s0[i].now = 0;
        s0[i].str = "";
      }
    }
  }

  static void print(int num, node[] s0) { // 输出
    for (int i = 1; i <= num; i++) {
      if (i != 1) {
        System.out.print("+");
      }
      if (s0[i].now == 0) {
        continue;
      }
      if (s0[i].now != 1) {
        System.out.print(s0[i].now);
      }
      boolean[] vis = new boolean[s0[i].str.length()];
      for (int j = 0; j < s0[i].str.length(); j++) {
        char ch;
        ch= s0[i].str.charAt(j);
        if (vis[j]) {
          continue;
        }
        vis[j] = true;
        int flag = 1;
        for (int k = j + 1; k < s0[i].str.length(); k++) {
          if (s0[i].str.charAt(k) == ch) {
            vis[k] = true;
            flag++;
          }
        }
        if (s0[i].now == 1 && j == 0) {
          System.out.print(ch);
        } else {
          System.out.print("*" + ch);
        }
        if (flag >= 2) {
          System.out.print("^" + flag);
        } //
      }

    }
    System.out.print("\n");
  }

  static void checks(String choice, int num, node[] s0) {
    System.out.print("\n");
    System.out.print(choice);
    System.out.print(num);
    System.out.print("\n");
  // for (int i = 0; i < n; i++)
  // {
  // System.out.print(s[i].str);
  // System.out.print("\n");
  // System.out.print(s[i].now);
  // System.out.print("\n");
  // }
  }

  static void simplify(String choice, int num, node[] s0) { // 表达式求值
    int k0 = 10;
    checks(choice, num, s0);
    while (k0 < choice.length() - 1) {
      char ch;
      ch= choice.charAt(k0);
      String tn = "";
      k0 += 2;
      for (int i = k0; i < choice.length(); i++) {
        k0 = i + 1;
        if (Character.isDigit(choice.charAt(i))) {
          tn += choice.charAt(i);
        } else {
          break;
        }
      } // 截取数字
      int now;
      now= Integer.parseInt(tn);
      for (int i = 1; i <= num; i++) {

        String tmp = "";
        for (int j = 0; j < s0[i].str.length(); j++) {
          if (s0[i].str.charAt(j) == ch) {
            s0[i].now *= now;
          } else {
            tmp += s0[i].str.charAt(j);
          }
        } // 字符相等替换，并且数字相乘
        s0[i].str = tmp;
      }
    }
  }

  static boolean check(String ori) { // 查看表达式是否合法
    int flag=0;
    for (int i = 0; i < ori.length(); i++) {
      if (ori.charAt(i) >= '0' && ori.charAt(i) <= '9') {
        continue;
      }
      if (ori.charAt(i) >= 'a' && ori.charAt(i) <= 'z') {
        continue;
      }
      if (ori.charAt(i) >= 'A' && ori.charAt(i) <= 'Z') {
        continue;
      }
      if (ori.charAt(i) == '+' || ori.charAt(i) == '*') {
        continue;
      }
      flag=1;// 合法字符检查
    }
    if (ori.charAt(0) == '+' || ori.charAt(ori.length() - 1) == '+') {
      flag=1;
    }
    if (ori.charAt(0) == '*' || ori.charAt(ori.length() - 1) == '*') {
      flag=1;
    }
    for (int i = 0; i < ori.length() - 1; i++) {
      if (ori.charAt(i) == '+' || ori.charAt(i) == '*') {
        if (ori.charAt(i + 1) == '+' || ori.charAt(i + 1) == '*') {
          flag=1;
        }
      }
    } // + *位置判断表达式正误
    if (judge(ori)) {
      flag=1;// 表达式中不能有0
    }
    return flag==1;
      
  }

  static boolean checkDer(String choice, int num, node[] s0) { // 查看求导表达式是否合法
    boolean flag = false;
    if (choice.length() != 6) {
      flag =false;
    }
    String tmp = "";
    for (int i = 0; i <= 4; i++) {
      tmp += choice.charAt(i);
    }
    if (!tmp.equals("!d/d ")) {
      flag =false;
    }
    for (int i = 1; i <= num; i++) {
      for (int j = 0; j < s0[i].str.length(); j++) {
        if (choice.charAt(5) == s0[i].str.charAt(j)) {
          flag = true;// 表达式是否有这个元素x
        }
      }
    }
    return flag;
  }

  static boolean checkSim(String choice, int num, node[] s0) { // 查看化简表达式是否合法
    if (choice.length() < 10) {
      return false;
    }
    String tmp = "";
    for (int i = 0; i <= 9; i++) {
      tmp += choice.charAt(i);
    }
    if (!"!simplify ".equals(tmp)) {
      return false;
    }
    int i0 = 10;
    while (i0 < choice.length()) {
      if (i0 + 2 >= choice.length()) {
        return false;
      }
      if (choice.charAt(i0) < 'a' || choice.charAt(i0) > 'z') {
        return false;
      }
      if (choice.charAt(i0 + 1) != '=') {
        return false;
      }
      if (choice.charAt(i0 + 2) < '0' || choice.charAt(i0 + 2) > '9') {
        return false;
      }
      i0 += 2;
      for (int j = i0; j < choice.length(); j++) {
        i0 = j + 1;
        if (choice.charAt(j) < '0' || choice.charAt(j) > '9') {
          if (choice.charAt(j) != ' ') {
            return false;
          }
          break;
        }
      }
    }
    if (judge(choice)) {
      return false;
    }
    return true;
  }

  static int expression(int num1, node[] s0) { // 合并同类项
    boolean jud;
    jud=true;
    while (jud) {
      int flag = -1;
      int ptr = -1;
      for (int i = 1; i <= num1; i++) {
        if (s0[i].str.equals("")) {
          flag = i;
          break;
        }
      }
      if (flag == -1) {
        break;
      }
      for (int i = flag + 1; i <= num1; i++) {
        if (s0[i].str.equals("")) {
          ptr = i;
          break;
        }
      }
      if (ptr == -1) {
        break;
      }
      num1--;
      s0[flag].now += s0[ptr].now;
      for (int i = ptr; i <= num1; i++) {
        s0[i].now = s0[i + 1].now;
        s0[i].str = s0[i + 1].str;
      }
    }
    return num1;// 合并常数项
  }

  /**
  * main.
  * @param args string
  */
  public static void main() {

    Scanner in = new Scanner(System.in);
    String ori = in.nextLine(); // 读入表达式
    while (check(ori)) { // 判断表达式是否合法 *java main 函数不能有return
      System.out.println("Error!");
      in = new Scanner(System.in);
      ori = in.nextLine(); // 读入表达式
    }
    System.out.println("Success!");
    ori = preStr(ori); // 效果：将所有digit×digit类型的子串合并为一个数字，并且删除所有乘号
    node[] s0 = new node[205];
    int num = pre(ori, s0);// 预处理分割+号
    combination(num, s0);// 效果：表达式形式转为：digit （*）character
    while (in.hasNext()) {
      String choice = in.nextLine();
      if (checkDer(choice, num, s0)) {
        // 是否是求导？
        derivative(num, choice.charAt(5), s0); // 求导
      } else if (checkSim(choice, num, s0)) {
        // 是否是求值？
        simplify(choice, num, s0); // 求值
      } else {
        System.out.println("Error!");
      }
      num = expression(num, s0); // 化简
      print(num, s0); // 输出
    }
    in.close(); // *关闭文件指针
  }
  String[] args;
}
