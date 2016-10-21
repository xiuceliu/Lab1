package ll;

import java.util.regex.*;
//import poly.java;
/**
 * 
 * @author sion
 *
 */
//Each class should declare at least one constructor
//寤虹珛涓�涓瀯閫犲櫒鍒濆鍖栧彉閲�
class Polynomial {
    private String poly;
    // It is somewhat confusing to have a field name matching the declaring class name
    //publicMethodCommentRequirement  Required
    public Boolean expression(String parameter)
    //Avoid variables with short names like s
    //Avoid reassigning parameters such as ''
    //Reassigning values to parameters is a questionable practice. Use a temporary local variable instead.
    {
    	final String temp = parameter;//娣诲姞涓存椂鍙橀噺
    	if (check(arrytostring(parameter))) {
    		this.poly = arrytostring(parameter);
    		System.out.println(parameter);
    		parameter = poly;
    		return true;
    	}
    	//濡傛灉poly涓繕娌℃湁澶氶」寮忥紝鐩存帴杩斿洖锛屼笉鍒嗘瀽鍛戒护銆�
    	if (this.poly == null) {return true;}
    	//A method should have only one exit point, and that should be the last statement in the method
    	//妫�娴嬫槸鍚︿负绠�鍖栧懡浠�
    	if (parameter.matches("!simplify [a-zA-Z]+=\\d+")){
    		
    		this.simplify(parameter);
    	}
    	if (parameter.matches("!simplify")) {
    		this.simplify();
    		}
    	//妫�娴嬫槸鍚︿负姹傚鍛戒护
    	if (parameter.matches("!d/d[a-zA-Z]+")) {
    		final Pattern var = Pattern.compile("!d/d([a-zA-Z]+)");//final
    		final Matcher matcherVar = var.matcher(parameter);
    		matcherVar.find();
    		derivation(matcherVar.group(1));
    	}
    	
    	return true;
    }
    @SuppressWarnings("null")
	private String arrytostring(final String pa2)//@鏀寔鍖归厤^鏉ヨ〃绀篴鐨勫灏戞鏂�
    {
    	String temp=pa2;
    	final Pattern polyPattern2 = Pattern.compile("([a-zA-Z]+)\\^([0-9])");
    	final Matcher poly2 = polyPattern2.matcher(pa2);
    	int count ;
    	//Found 'DD'-anomaly for variable 'count'
    	//DD and DU anomalies (if I remember correctly鈥擨 use FindBugs and the messages are a little different)
    	//refer to assigning a value to a local variable that is never read, 
    	//usually because it is reassigned another value before ever being read.
    	//A typical case would be initializing some variable with null when it is declared. 
    	//Don't declare the variable until it's needed.
    	StringBuffer varcount =null ;
    	if (poly2.find())
    	{
    		count=Integer.parseInt(poly2.group(2));
    		//System.out.println("group0"+poly2.group(0)+"group1"+poly2.group(1)+"group2"+poly2.group(2));
    		for(int i=0;i<count-1;i++)
    		{
    			varcount.append(poly2.group(1) + "*");
    		}
    		varcount.append(poly2.group(1));//Prefer StringBuffer over += for concatenating strings
    		 temp=pa2.replace(poly2.group(0),varcount);
    		//System.out.println("鏇挎崲鍚庯細"+s);
    	}
    	return  temp;
    }
    
    /**
     * 
     * for warning
     *
     */
    private Boolean check(final String temp)
    {
    	final Pattern polyPattern = Pattern.compile("(([0-9]+|[a-zA-Z]+)(\\*|\\+))*([0-9]+|[a-zA-Z]+)"); //@
    	final Matcher poly = polyPattern.matcher(temp);
    	return poly.matches();
    	
    }
    
    /**
     * 
     * for warning
     *
     */
    public boolean simplify()//閲嶈浇锛屾病鏈夎緭鍏ュ彉閲忓��
    {
    	System.out.println(this.poly);
    	return true;
    }
    /**
     * 
     * Potential violation of Law of Demeter (method chain calls)?
	- Potential violation of Law of Demeter (object not created 
	 locally)?
     *
     */
    public boolean simplify(final String var)
    {
		//鑾峰彇variable鍜屾暟瀛�
    	final Pattern simp = Pattern.compile("!simplify ([a-zA-Z]+)=(\\d+)");
    	final Matcher matchesimp = simp.matcher(var);
    	matchesimp.find();
		final String variable = matchesimp.group(1);
		final String value = matchesimp.group(2);
		
		//灏唒olynomial涓殑璇ariable,鏇挎崲鎴恦alue
		final Pattern varPattern = Pattern.compile(variable);
		final Matcher findvar = varPattern.matcher(this.poly);
		if (findvar.find()) {
			//鑻ユ煡鎵惧埌璇ュ瓧绗︼紝杩涜鏇挎崲锛屽惁鍒欐墦鍗伴敊璇俊鎭�
			String newStr ;
			newStr = this.poly.replace(variable, value);
    		
			//鍒ゆ柇newStr涓槸鍚﹁繕鏈夊瓧姣�
			final Pattern letter = Pattern.compile("[a-zA-Z]+");
			final Matcher haveLetter = letter.matcher(newStr);
			
		final Pattern somel = Pattern.compile("((\\d\\*)+[a-zA-Z]+)|([a-zA-Z]+(\\*\\d)+)");
		final Matcher matchersomel = somel.matcher(newStr); //鍖归厤閮ㄥ垎绠楀紡鍜屽瓧姣嶇浉杩�
		
		final Pattern some2 = Pattern.compile("(\\d\\*)+(\\d)\\+");
		Matcher matchersomel2 = some2.matcher(newStr); //鍖归厤閮ㄥ垎绠楀紡鍜屽瓧姣嶇浉杩�
			if (haveLetter.find()) {
				//濡傛灉鏂扮殑瀛楃涓蹭腑杩樻湁瀛楁瘝鎵撳嵃锛屽惁鍒欒绠楃畻寮忕殑鍊�
				
				if (matchersomel2.find())
				{
					multisome(newStr);
				}
				else if (matchersomel.find())//濡傛灉閮ㄥ垎绠楀紡鍜屽瓧姣嶇浉杩�
				{
					calculatesome(newStr);
				}
				else {System.out.println(newStr);}
//				
			} else {
				System.out.println(calculate(newStr));
			}
			return true;
		} else {
			System.out.println("Error, no variable");
			return false;
		}
    }
    private void multisome(final String equation) { //濡傛灉澶氶」寮忛噷閮ㄥ垎绠楀紡鍜屽姞鍙风浉杩�
    	final Pattern item3 = Pattern.compile("(\\d\\*)+(\\d)\\+");
		final Matcher matcherItem3 = item3.matcher(equation);
		String newStr3 ;
		newStr3 = equation;
    	while (matcherItem3.find()) {
    		int item3Value = 1;
    		final Pattern num3 = Pattern.compile("\\d");
    		final Matcher matcherNum3 = num3.matcher(matcherItem3.group(0));
    		//System.out.println("group(0)="+matcherItem3.group(0)+"group(1)="+matcherItem3.group(1)+"groupcount="+matcherItem3.groupCount());
    		while (matcherNum3.find()) {
    			item3Value *= Integer.parseInt(matcherNum3.group(0)); //灏嗗瓧绗︿覆瑙ｆ瀽涓烘暣鏁�
    		}
    		//System.out.println("matcherItem3.group(2)"+matcherItem3.group(2));
    		//item3Value=item3Value*Integer.parseInt(matcherItem3.group(2));
			newStr3 = newStr3.replace(matcherItem3.group(0), item3Value + "+");
    	}
			System.out.println(newStr3);
    		
    }
    private void calculatesome(final String equation) {//濡傛灉澶氶」寮忛噷閮ㄥ垎绠楀紡鍜屽瓧姣嶇浉杩� 
    	final Pattern item2 = Pattern.compile("(\\d\\*)*[a-zA-Z]+|([a-zA-Z]+(\\*\\d)*)");
    	final Matcher matcherItem2 = item2.matcher(equation);
    	String newStr2 = equation;
    	String vartemp = "";
    	//2.DU-寮傚父锛氫竴涓垰鍒氬畾涔夌殑鍙橀噺鏄湭瀹氫箟鐨勩�傝繖浜涘紓甯稿彲鑳藉嚭鐜板湪鏅�氱殑婧愪唬鐮佹枃鏈腑3.DD-寮傚父锛氫竴涓垰鍒氬畾涔夌殑鍙橀噺閲嶆柊瀹氫箟銆傝繖鏄笉濂界殑浣嗗苟闈炰竴瀹氭槸涓猙ug銆�
    	while (matcherItem2.find()) {
    		int item2Value = 1;
    		final Pattern num = Pattern.compile("\\d");
    		final Matcher matcherNum = num.matcher(matcherItem2.group(0));
    		
    		
    		
    		final Pattern var = Pattern.compile("([a-zA-Z]+)");
    		final Matcher matchervar = var.matcher(matcherItem2.group(0));
    		if (matchervar.find()) {
    			vartemp = matchervar.group(0); 
    			} 
    		
    		while (matcherNum.find()) {
    			item2Value *= Integer.parseInt(matcherNum.group(0));//灏嗗瓧绗︿覆瑙ｆ瀽涓烘暣鏁�
    		}
    		
			newStr2 = newStr2.replace(matcherItem2.group(0), item2Value + "*" + vartemp);
    	}
			System.out.println(newStr2);
    		
    }
    private int calculate(final String equation)//濡傛灉澶氶」寮忛兘鍙互绠�鍖栦负鏁板瓧鏃�
    {
    	final Pattern item = Pattern.compile("(\\d\\*)*\\d");
    	final Matcher matcherItem = item.matcher(equation);
    	int sum = 0;
    	while (matcherItem.find()) {
    		int itemValue = 1;
    		final Pattern num = Pattern.compile("\\d");
    		final Matcher matcherNum = num.matcher(matcherItem.group(0));
    		while (matcherNum.find()) {
    			itemValue *= Integer.parseInt(matcherNum.group(0)); //灏嗗瓧绗︿覆瑙ｆ瀽涓烘暣鏁�
    		}
    		sum += itemValue;
    	}
    	return sum;
    }
    
    public void derivation(final String variable)
    {
    	final Pattern judge = Pattern.compile(variable);
    	final Matcher matcherjudge = judge.matcher(poly);
    	//Avoid if (x != y) ..; else ..;
    	if (matcherjudge.find())
    	{
//        	Pattern item = Pattern.compile("(.*)\\+");
//        	Matcher matcherItem = item.matcher(polynomial);
        	String newStr = "";//               avoid     = new String();
        	final String[]app = poly.split("\\+");    //a->app
        	int j=0;
        	while (j < app.length) {
        		final Pattern var = Pattern.compile(variable);
        		final String oldItem = app[j];
        		//System.out.println("matchreItem group(1)"+matcherItem.group(1));
        		final Matcher matcherVar = var.matcher(oldItem);
        		//System.out.println("group(0)"+matcherVar.group(0));
        		int counter = 0; //缁熻璇ュ彉閲忓嚭鐜扮殑娆℃暟
        		String newItem;
        		String replace;
        		while (matcherVar.find()) { 
        			++counter; 
        			} //鏋勫缓鏂扮殑item
        		if (counter == 0) {
        			newItem = "";
        		}
        		else if (counter == 1) {
        			newItem = oldItem.replaceFirst("\\*" + variable, ""); 
        		}
        		else {
        			replace = oldItem.replaceFirst("\\*" + variable, "");
        			newItem =  counter + "*" + replace;
        		}
        		
        		if (counter == 0) {
        			newStr = newStr + newItem; //   
        		}
        		else {
        			newStr += newItem + "+";
        			}
        		j++;
        	}
        	newStr = newStr.substring(0, newStr.length() - 1);
        	calculatesome(newStr);
    		
    	}
    	else
    	{
    		System.out.println("Error, no variable");
    	}
    }
}