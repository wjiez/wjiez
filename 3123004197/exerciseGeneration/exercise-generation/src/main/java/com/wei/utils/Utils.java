package com.wei.utils;

import com.wei.pojo.Data;
import com.wei.pojo.Exercise;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

public class Utils {
	//简化真分数的函数
	public static void simplify(Data data){
   		//不是分数,返回
		if(data.getNumerator()==null){
			   return;
		}
		int temp = data.getNumerator() / data.getDenominator();
		if(temp!=0){
			if(data.getItg() ==null) data.setItg(temp);
			else data.setItg(data.getItg()+temp);
		}
		data.setNumerator(data.getNumerator() % data.getDenominator());
		//整数则将分子分母置空
		if(data.getNumerator() ==0) {
		 	data.setNumerator(null);
			data.setDenominator(null);
		}
		else{//是真分数,使用辗转相处法得最大公约数,进行化简
		 	int a = data.getNumerator();
			int b = data.getDenominator();
			while (b != 0) {
	   	    	int remainder = a % b;
				   a = b;
        		b = remainder;
			}
			data.setNumerator(data.getNumerator()/a);
			data.setDenominator(data.getDenominator()/a);
		}
	}
	//计算: 调用不同的计算函数
	public static Data cal(Data a,Data b, char sign){
		Data result = switch (sign) {
            case '+' -> add(a, b);
            case '-' -> minus(a, b);
            case '×' -> multiply(a, b);
            case '÷' -> divide(a, b);
            default -> null;
        };
        return result;
	}
	//加法
	public static Data add(Data a, Data b){
		Data result = new Data();
		if(a.getItg() !=null|| b.getItg() !=null){
			if(a.getItg() ==null) result.setItg(b.getItg());
			else {
				int itg = b.getItg() == null ? a.getItg() : (a.getItg() + b.getItg());
				result.setItg(itg);
			}
			if(result.getItg()==0&&(a.getNumerator()!=null||b.getNumerator()!=null))result.setItg(null);
		}
		if(a.getNumerator() ==null&& b.getNumerator() ==null)return result;
		else if(a.getNumerator() ==null){
			result.setNumerator(b.getNumerator());
			result.setDenominator(b.getDenominator());
			return result;
		}else if (b.getNumerator() ==null){
			result.setNumerator(a.getNumerator());
			result.setDenominator(a.getDenominator());
			return result;
		}else{
			result.setNumerator(a.getNumerator() * b.getDenominator() + b.getNumerator() * a.getDenominator());

			result.setDenominator(a.getDenominator() * b.getDenominator());
			simplify(result);
			return result;
		}
	}
	//减法,当结果为负数时,返回null
	public static Data minus(Data a, Data b){
		Data result = new Data();
		if(a.getItg() !=null|| b.getItg() !=null){
			if(a.getItg() ==null) {
				if(b.getItg()==0){
					result.setNumerator(a.getNumerator());
					result.setDenominator(a.getDenominator());
					return result;
				}
				return null;
			}
			else result.setItg(b.getItg() == null ? a.getItg() : (a.getItg() - b.getItg()));
			if(result.getItg() <0)return null;
			if(result.getItg() ==0) {
				if(a.getNumerator()==null&&b.getNumerator()==null){
					result.setItg(0);
					return result;
				}
				else result.setItg(null);
			}
		}
		if(a.getNumerator() ==null&& b.getNumerator() ==null)return result;
		else if(a.getNumerator() ==null){
			if(result.getItg() ==null)return null;
			result.setNumerator(result.getItg() * b.getDenominator() - b.getNumerator());
			result.setDenominator(b.getDenominator());
			result.setItg(null);
			simplify(result);
			return result;
		}else if (b.getNumerator() ==null){
			result.setNumerator(a.getNumerator());
			result.setDenominator(a.getDenominator());
			return result;
		}else{
			if(result.getItg() !=null)
				result.setNumerator((result.getItg() *  a.getDenominator()+a.getNumerator())*b.getDenominator() - b.getNumerator() * a.getDenominator());
			else
				result.setNumerator(a.getNumerator() * b.getDenominator() - b.getNumerator() * a.getDenominator());
			if(result.getNumerator() <0)return null;
			if(result.getNumerator() ==0){
				result.setNumerator(null);
				result.setDenominator(null);
				result.setItg(0);
				return result;
			}
			result.setDenominator(a.getDenominator() * b.getDenominator());
			result.setItg(null);
			simplify(result);
			return result;
		}
	}
	//乘法
	public static Data multiply(Data a, Data b){
		Data result = new Data();
		if((a.getItg()!=null&&a.getItg() ==0)|| (b.getItg()!=null&&b.getItg() ==0)){
			result.setItg(0);
			return result;
		}
		if(a.getNumerator() ==null&& b.getNumerator() ==null){
			result.setItg(a.getItg() * b.getItg());
			return result;
		}else if(a.getNumerator() ==null){
			if(b.getItg() ==null) result.setNumerator(a.getItg() * b.getNumerator());
			else result.setNumerator(a.getItg() * (b.getItg() * b.getDenominator() + b.getNumerator()));
			result.setDenominator(b.getDenominator());
		}else if(b.getNumerator() ==null){
			if(a.getItg() ==null) result.setNumerator(b.getItg() * a.getNumerator());
			else result.setNumerator(b.getItg() * (a.getItg() * a.getDenominator() + a.getNumerator()));
			result.setDenominator(a.getDenominator());
		}else{
			if(a.getItg() ==null&& b.getItg() ==null) result.setNumerator(a.getNumerator() * b.getNumerator());
			else if(a.getItg() ==null) result.setNumerator(a.getNumerator() * (b.getItg() * b.getDenominator() + b.getNumerator()));
			else if(b.getItg() ==null) result.setNumerator(b.getNumerator() * (a.getItg() * a.getDenominator() + a.getNumerator()));
			else result.setNumerator((a.getDenominator() * a.getItg() + a.getNumerator()) * (b.getDenominator() * b.getItg() + b.getNumerator()));
			result.setDenominator(b.getDenominator() * a.getDenominator());
		}
		simplify(result);
		return result;
	}
	//除法,当除数为0返回null
	public static Data divide(Data a, Data b){
		Data result = new Data();
		if(b.getItg()!=null&&b.getItg() ==0)return null;
		if(a.getItg()!=null&&a.getItg() ==0){
			result.setItg(0);
			return result;
		}
		if(a.getNumerator() ==null&& b.getNumerator() ==null){
			result.setNumerator(a.getItg());
			result.setDenominator(b.getItg());
			simplify(result);
			return result;
		}else if(a.getNumerator() ==null){
			if(b.getItg() ==null) result.setDenominator(b.getNumerator());
			else result.setDenominator(b.getItg() * b.getDenominator() + b.getNumerator());
			result.setNumerator(b.getDenominator() * a.getItg());
		}else if(b.getNumerator() ==null){
			if(a.getItg() ==null) result.setNumerator(a.getNumerator());
			else result.setNumerator(a.getItg() * a.getDenominator() + a.getNumerator());
			result.setDenominator(a.getDenominator() * b.getItg());
		}else{
			if(a.getItg() ==null&& b.getItg() ==null) {
				result.setNumerator(a.getNumerator() * b.getDenominator());
				result.setDenominator(a.getDenominator() * b.getNumerator());
			}
			else if(a.getItg() ==null) {
				result.setNumerator(a.getNumerator() * b.getDenominator());
				result.setDenominator(a.getDenominator() * (b.getItg() * b.getDenominator() + b.getNumerator()));
			}
			else if(b.getItg() ==null) {
				result.setNumerator((a.getItg() * a.getDenominator() + a.getNumerator()) * b.getDenominator());
				result.setDenominator(a.getDenominator() * b.getNumerator());
			}
			else{
				result.setNumerator((a.getItg() * a.getDenominator() + a.getNumerator()) * b.getDenominator());
				result.setDenominator(a.getDenominator() * (b.getItg() * b.getDenominator() + b.getNumerator()));
			}
		}
		simplify(result);
		return result;
	}
	//随机生成整数
	public static Data integerGeneration(int maxInt){
		Random random = new Random();
		int randomNumber = random.nextInt(maxInt);
		return new Data(randomNumber,null,null);
	}
	//随机生成真分数:先生成不能整除的分母和分子,然后化简
	public static Data fractionGeneration(int maxInt){
		Data data=new Data();
		Random random = new Random();
		data.setNumerator(random.nextInt(maxInt-1)+1);
		int temp=random.nextInt(maxInt-1)+1;
		while(data.getNumerator() %temp==0)temp=random.nextInt(maxInt-1)+1;
		data.setDenominator(temp);
		simplify(data);
		return data;
	}

	//随机生成运算符号
	public static Character signGeneration(){
		Random random = new Random();
		int randomNumber = random.nextInt(4);
		switch(randomNumber){
			case 0:
				return '+';
			case 1:
				return '-';
			case 2:
				return '×';
			case 3:
				return '÷';
			default :
				return null;
		}

	}
	//用于读取校对练习文件的答案和答案文件的答案
	public static void proofread(String exerciseFilePath, String answerFilePath) {

		try {

			ArrayList<String> exercises = (ArrayList<String>) Files.readAllLines(Paths.get(exerciseFilePath));

			ArrayList<String> answers = (ArrayList<String>) Files.readAllLines(Paths.get(answerFilePath));

			int index =0;

			ArrayList<Integer> errorNum = new ArrayList<>();
			ArrayList<Integer> correctNum = new ArrayList<>();
			while(index<exercises.size()){
				int equalsIndex = exercises.get(index).indexOf('='),dotIndex = answers.get(index).indexOf('.');
				String o = exercises.get(index).substring(equalsIndex+1).trim();
				if(answers.get(index).substring(dotIndex+1).trim().equals(o)) correctNum.add(index+1);
				else errorNum.add(index+1);
				index++;
			}
			System.out.print("correct:(");
			if(!(correctNum.isEmpty())){
				for(int i:correctNum){
					System.out.print(i);
					if(correctNum.get(correctNum.size()-1)!=i)System.out.print(",");
				}
			}
			System.out.println(")");
			System.out.print("wrong:(");
			if(!(errorNum.isEmpty())){
				for(int i:errorNum){
					System.out.print(i);
					if(errorNum.get(errorNum.size()-1)!=i)System.out.print(",");
				}
			}
			System.out.println(")");

		} catch (IOException e) {

			System.out.println("读取文件时出错：" + e.getMessage());

		}

	}
	//将题目集合中的题目和答案分别写入题目文件的答案文件
	public static void writeToFile(ArrayList<Exercise> exercises) {
		try(PrintStream pse = new PrintStream("Exercises.txt",StandardCharsets.UTF_8);
			PrintStream psa = new PrintStream("Answers.txt", StandardCharsets.UTF_8)){
			for(int i=0;i<exercises.size();i++){
				Data data = exercises.get(i).getAnswer();
				psa.print(i+1+". ");
				pse.print(i+1+". ");
				printData(psa,data);
				psa.println(" ");
				ArrayList<Data> datas = exercises.get(i).getDatas();
				ArrayList<Character> signs = exercises.get(i).getSigns();
				int j,k;
				for(j=0,k=0;j<datas.size()&&k<signs.size();j++){
					if(signs.get(k)=='('){
						pse.print(signs.get(k++)+" ");
					}
					printData(pse,datas.get(j));
					if(signs.get(k)==')'){
						pse.print(signs.get(k++)+" ");
					}
					if(k<signs.size())pse.print(signs.get(k++)+" ");
				}
				if(j<datas.size())printData(pse,datas.get(j));
				pse.println("=");
//				pse.print("=");
//				printData(pse,data);  //写入答案到题目文件
//				pse.println(" ");
			}
		} catch(Exception e){
			System.out.println("写文件时出错：" + e.getMessage());
		}
	}

	//打印整数或分数的方法
	public static void printData(PrintStream ps, Data data){
		if(data.getNumerator()==null)ps.print(data.getItg());
		else {
			if(data.getItg()!=null)ps.print(data.getItg()+"'");
			ps.print(data.getNumerator()+"/"+data.getDenominator());
		}
		ps.print(" ");
	}
	//用于计算一整道题目: 首先转换为后缀表达式,然后用栈的思想来计算
	public static boolean exerciseCal(Exercise exercise){
		ArrayList<Object> postfix = toPostfix(exercise);
		Stack<Data> dataStack = new Stack<Data>();
		int i=0;
		while(i < postfix.size()){
			while(postfix.get(i).getClass()!=Character.class){
				dataStack.push((Data)postfix.get(i++));
			}
			Character sign = (Character)postfix.get(i++);
			Data opNum1= (Data)dataStack.pop();
			Data opNum2= (Data)dataStack.pop();
			Data result = cal(opNum2,opNum1,sign);
			if(result==null)return false;
			dataStack.push(result);
		}
		exercise.setAnswer(dataStack.pop());
		return true;
	}
	//将一整道练习题转换为后缀表达式: 使用栈的思想
	public static ArrayList<Object>	toPostfix(Exercise exercise){
		ArrayList<Object> postfix = new ArrayList<Object>();
		Stack<Character> charStack = new Stack<Character>();
		ArrayList<Data> datas =exercise.getDatas();
		ArrayList<Character> signs =exercise.getSigns();
		int j,k;
		for(j=0,k=0;j<datas.size()&&k<signs.size();j++){
			if(signs.get(k)=='('){
				charStack.push('(');
				k++;
			}
			postfix.add(datas.get(j));
			if(signs.get(k)==')'){
				while(charStack.peek()!='('){
					postfix.add(charStack.pop());
				}
				charStack.pop();
				k++;
			}
			if(k<signs.size()){
				if(charStack.empty()||charStack.peek()=='(')charStack.push(signs.get(k++));
				else if((charStack.peek()=='+'||charStack.peek()=='-')&&(signs.get(k)=='÷'||signs.get(k)=='×')){
					charStack.push(signs.get(k++));
				}else {
					postfix.add(charStack.pop());
					charStack.push(signs.get(k++));
				}
			}

		}
		if(j<datas.size())postfix.add(datas.get(j));
		while(!charStack.empty()){
			postfix.add(charStack.pop());
		}
		return postfix;
	}

	//检查两道题目是否相同: 也是使用栈的思想, 同步计算,看是否相同
	public static boolean checkDuplication(Exercise a,Exercise b){

		Data d1=a.getAnswer(),d2=b.getAnswer();
		if(!Objects.equals(d1.getItg(), d2.getItg()) || !Objects.equals(d1.getNumerator(), d2.getNumerator()) || !Objects.equals(d1.getDenominator(), d2.getDenominator()))return false;
		ArrayList<Object> aPostfix = toPostfix(a);
		ArrayList<Object> bPostfix = toPostfix(b);
		Stack<Data> aDataStack = new Stack<Data>();
		Stack<Data> bDataStack = new Stack<Data>();
		int i=0,j=0;
		while(i < aPostfix.size()){
			while(aPostfix.get(i).getClass()!=Character.class){
				aDataStack.push((Data)aPostfix.get(i++));
			}
			while(bPostfix.get(j).getClass()!=Character.class){
				bDataStack.push((Data)bPostfix.get(j++));
			}
			Character aSign = (Character)aPostfix.get(i++);
			Character bSign = (Character)bPostfix.get(j++);
			if(aSign!=bSign)return false;
			else{
				Data aTemp1= aDataStack.pop();
				Data aTemp2= aDataStack.pop();
				Data bTemp1= bDataStack.pop();
				Data bTemp2= bDataStack.pop();
				Data aResult = cal(aTemp2,aTemp1,aSign);
				Data bResult = cal(bTemp2,bTemp1,bSign);

				if(!aResult.equals(bResult))return false;

				if(aResult.getItg()!=null&&aResult.getItg()==0&&!aTemp1.equals(bTemp1)&&aSign=='÷')return false;
				if(aResult.getItg()!=null&&aResult.getItg()==0&&aSign=='×'){
					if(aTemp1.getItg()!=null&&aTemp1.getItg()==0&&!aTemp2.equals(bTemp1)&&!aTemp2.equals(bTemp2))return false;
					if(aTemp2.getItg()!=null&&aTemp2.getItg()==0&&!aTemp1.equals(bTemp1)&&!aTemp1.equals(bTemp2))return false;

				}
				if((aSign=='+'||aSign=='×')&&(aTemp1.equals(bTemp1)||aTemp1.equals(bTemp2)))return false;
				if(!aTemp1.equals(bTemp1)) return false;
				aDataStack.push(aResult);
				bDataStack.push(bResult);
			}
		}
		return true;
	}


	/**
	 * 用于连续生成多道题目的函数
	 * @param exercises 题目列表
	 * @param numLimit  生成数值限制
	 * @param dataNum   生成题目的运算数量(减一即为运算符号的数量)
	 * @param exerciseNum  生成题目的数量上限
	 * @param checkStart   从题目集合中要查重的起始位置
	 */
	public static void exerciseGeneration(ArrayList<Exercise> exercises,int numLimit,int dataNum,int exerciseNum,int checkStart){
		int count=exercises.size();
		while(count<exerciseNum){
			Exercise exercise=new Exercise();
			exercise.setDatas(new ArrayList<Data>());
			exercise.setSigns(new ArrayList<Character>());
			exercise.setAnswer(new Data());
			Random random = new Random();
			for(int i=0;i<dataNum;i++){
				int randomNumber = random.nextInt(2);
				if(randomNumber==0)exercise.getDatas().add(integerGeneration(numLimit));
				else
					exercise.getDatas().add(fractionGeneration(numLimit));
			}
			for (int i=0;i<dataNum-1;i++)exercise.getSigns().add(signGeneration());
			if(dataNum>2&&random.nextInt(2)==1){
				int randomInt = random.nextInt(dataNum-1);
				exercise.getSigns().add(randomInt,'(');
				if(randomInt==0){
					exercise.getSigns().add(random.nextInt(dataNum-2)+2,')');

				}else{
					exercise.getSigns().add(random.nextInt(dataNum-1-randomInt)+randomInt+2,')');
				}
			}
			if(!exerciseCal(exercise)) continue;
			boolean flag=true;
			int start=checkStart;
			while(start<exercises.size()){
				if(checkDuplication(exercise,exercises.get(start++))) {
					flag=false;
					break;
				}
			}
			if(!flag) continue;
			exercises.add(exercise);
			count++;
		}
	}

}
