package test;

import java.util.Scanner;
/**
 * .
 * @author dream.
 *
 */
public class Java {

  static boolean judge(String str) {
    // �жϱ��ʽ�Ƿ�Ϊ0
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
    // ���Գ˺�
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
      } // �ҵ������ĳ˺ţ��˺�����������
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
      } // ��˺���������ķ�����
      String s1 = "";
      String s2 = "";
      for (int i = fn; i < flag; i++) {
        s1 =s1+str1.charAt(i);
      }
      for (int i = flag + 1; i <= fg; i++) {
        s2 += str1.charAt(i);
      }
      int now = Integer.parseInt(s1);
      now *= Integer.parseInt(s2);// ���ִ��룬תint
      for (int i = 0; i < fn; i++) {
        tmp += str1.charAt(i);
      }
      tmp += String.valueOf(now);
      for (int i = fg + 1; i < str1.length(); i++) {
        tmp += str1.charAt(i);
      }
      str1 = tmp;// �����Ԫ����ԭ������
    }
    tmp = "";
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != '*') {
        tmp += str1.charAt(i);
      }
    } // ɾ�����г˺�
    return tmp;
  }
  // Ч����������digit��digit���͵��Ӵ��ϲ�Ϊһ�����֣�����ɾ�����г˺�

  static int pre(String ori, node[] str) { 
    // Ԥ����ָ�+��
    int num = 0;
    String tmp = "";
    for (int i = 0; i < ori.length(); i++) {
      if (ori.charAt(i) == '+') {
        str[++num] = new node(1, tmp);
        tmp = "";
    	  
      } else {
    	tmp += ori.charAt(i);
      }
    } // ����+�ŷָ���ʽ������ṹ�������е�String
    if (!tmp.equals("")) {
      str[++num] = new node(1, tmp);
      tmp = "";
    } // ����ĩβ��һ������
    return num;
  }
  // Ч�������������ṹ��������

  static void combination(int num, node[] s0) { 
    // �����ֺϲ�
    for (int i = 1; i <= num; i++) {
      String digit = "";
      String character = "";
      for (int j = 0; j < s0[i].str.length(); j++) {
        // ����ÿһ������
        if (Character.isDigit(s0[i].str.charAt(j))) {
          digit += s0[i].str.charAt(j);// ��������
        } else {
          character += s0[i].str.charAt(j);// ��ĸ����
          if (!digit.equals("")) {
            s0[i].now *= Integer.parseInt(digit);// ÿ������һ�η����֣�Ԥ�������ֺ�ԭֵ���
          }
          digit = "";
        }
      }
      if (!digit.equals("")) {
        s0[i].now *= Integer.parseInt(digit);// ���һ����Ŀ����������
      }
      s0[i].str = character;// ��ĸ�����
    }
  }
  // Ч�������ʽ��ʽתΪ��digit ��*��character
  
  static void derivative(int num, char ch, node[] s0) { // ��
    for (int i = 1; i <= num; i++) {
      int flag = 0;
      String tmp = "";
      for (int j = 0; j < s0[i].str.length(); j++) {
        if (s0[i].str.charAt(j) == ch) {
          flag++;// �ҵ�����Ԫ��
        }
        if (s0[i].str.charAt(j) == ch && flag == 1) {
          continue;
        } else {
          tmp += s0[i].str.charAt(j);
        }
      }
      if (flag >= 1) {
        s0[i].now *= flag;// �󵼺������
        s0[i].str = tmp;
      } else {
        s0[i].now = 0;
        s0[i].str = "";
      }
    }
  }

  static void print(int num, node[] s0) { // ���
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

  static void simplify(String choice, int num, node[] s0) { // ���ʽ��ֵ
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
      } // ��ȡ����
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
        } // �ַ�����滻�������������
        s0[i].str = tmp;
      }
    }
  }

  static boolean check(String ori) { // �鿴���ʽ�Ƿ�Ϸ�
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
      flag=1;// �Ϸ��ַ����
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
    } // + *λ���жϱ��ʽ����
    if (judge(ori)) {
      flag=1;// ���ʽ�в�����0
    }
    return flag==1;
      
  }

  static boolean checkDer(String choice, int num, node[] s0) { // �鿴�󵼱��ʽ�Ƿ�Ϸ�
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
          flag = true;// ���ʽ�Ƿ������Ԫ��x
        }
      }
    }
    return flag;
  }

  static boolean checkSim(String choice, int num, node[] s0) { // �鿴������ʽ�Ƿ�Ϸ�
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

  static int expression(int num1, node[] s0) { // �ϲ�ͬ����
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
    return num1;// �ϲ�������
  }

  /**
  * main.
  * @param args string
  */
  public static void main() {

    Scanner in = new Scanner(System.in);
    String ori = in.nextLine(); // ������ʽ
    while (check(ori)) { // �жϱ��ʽ�Ƿ�Ϸ� *java main ����������return
      System.out.println("Error!");
      in = new Scanner(System.in);
      ori = in.nextLine(); // ������ʽ
    }
    System.out.println("Success!");
    ori = preStr(ori); // Ч����������digit��digit���͵��Ӵ��ϲ�Ϊһ�����֣�����ɾ�����г˺�
    node[] s0 = new node[205];
    int num = pre(ori, s0);// Ԥ����ָ�+��
    combination(num, s0);// Ч�������ʽ��ʽתΪ��digit ��*��character
    while (in.hasNext()) {
      String choice = in.nextLine();
      if (checkDer(choice, num, s0)) {
        // �Ƿ����󵼣�
        derivative(num, choice.charAt(5), s0); // ��
      } else if (checkSim(choice, num, s0)) {
        // �Ƿ�����ֵ��
        simplify(choice, num, s0); // ��ֵ
      } else {
        System.out.println("Error!");
      }
      num = expression(num, s0); // ����
      print(num, s0); // ���
    }
    in.close(); // *�ر��ļ�ָ��
  }
  String[] args;
}
