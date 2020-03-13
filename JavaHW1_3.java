package javahw1_3;

import java.util.*;
import java.math.*;

class PostConvertor{
    PostConvertor(){
    }

    String[] Str2Tok(String s){
        StringTokenizer st = new StringTokenizer(s,"+-*/%()",true);
        String[] arr = new String[1000];
        int len = 0,i = 1;
        
        while(st.hasMoreTokens()){
            arr[i++] = st.nextToken();
            ++len;
        }
            
        arr[0] = Integer.toString(len);//arr[0] = arr.length
        
        return arr;    
    }
    
    String[] Tok2Pos(String str) {
        String[] arr = Str2Tok(str);//original token_array
        String[] posArr = new String[1000];//postfix token_array
        int ans_cnt = 1, posLen = 0;
        int length = Integer.parseInt(arr[0]);//get length of original token_array
        Stack<String> con = new Stack<String>();
        
        for(int i = 1;i <= length ;++i){//foreach element in original token_array
            if(Character.isDigit(arr[i].charAt(0))){//is a number
                posArr[ans_cnt++] = arr[i];
                ++posLen;
            }
            else if(arr[i].equals("("))
                con.push(arr[i]);
            else if(arr[i].equals(")")){
                while(!con.peek().equals("(")){
                    posArr[ans_cnt++] = con.pop();
                    ++posLen;
                }
                con.pop();
            }
            else{//normal operator
                if(con.empty())
                    con.push(arr[i]);
                else if(Priority(arr[i]) > Priority(con.peek()))//can press top
                    con.push(arr[i]);
                else{//cannot press top
                    while(!con.empty()&&(Priority(arr[i]) <= Priority(con.peek()))){
                        posArr[ans_cnt++] = con.pop();
                        ++posLen;
                    }
                    con.push(arr[i]);
                }        
            }
        }
        //clean the stack
        while(!con.empty()){
            posArr[ans_cnt++] = con.pop();
            ++posLen;
        }
        posArr[0] = Integer.toString(posLen);//record posArr.length
        
        return posArr;
    }
    
    BigDecimal PosCal(String s){
        String[] arr = Tok2Pos(s);
        int length = Integer.parseInt(arr[0]);
        Stack<String> con = new Stack<String>();
        BigDecimal op1,op2,tmp;
        
        for(int i = 1;i <= length;++i){//foreach element in posfix array
            if(Character.isDigit(arr[i].charAt(0)))//is number
                con.push(arr[i]);
                
            switch(arr[i]){
                case "+":
                    op2 = new BigDecimal(con.pop());
                    op1 = new BigDecimal(con.pop());
                    tmp = op1.add(op2);
                    con.push(tmp.toString());
                    break;
                case "-":
                    op2 = new BigDecimal(con.pop());
                    op1 = new BigDecimal(con.pop());
                    tmp = op1.subtract(op2);
                    con.push(tmp.toString());             
                    break;
                case "*":
                    op2 = new BigDecimal(con.pop());
                    op1 = new BigDecimal(con.pop());
                    tmp = op1.multiply(op2);
                    con.push(tmp.toString());
                    break;
                case "/":
                    op2 = new BigDecimal(con.pop());
                    op1 = new BigDecimal(con.pop());
                    tmp = op1.divide(op2);
                    con.push(tmp.toString());
                    break;
                case "%":
                    op2 = new BigDecimal(con.pop());
                    op1 = new BigDecimal(con.pop());
                    tmp = op1.remainder(op2);
                    con.push(tmp.toString());
                    break;  
            }
        }//end scanning postfix arr
        tmp = new BigDecimal(con.pop());//get left number
        
        return tmp;
    }
    //get operator Priority probable int stack
    public static int Priority(String op){
        if(op.equals("(")) return 1;
        else if(op.equals("+")||op.equals("-")) return 2;
        else if(op.equals("*")||op.equals("/")||op.equals("%")) return 3;
        
        return -1;
    }
}

public class JavaHW1_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()){
            BigDecimal ans;
            String str = (String)input.nextLine();
            PostConvertor A = new PostConvertor();
            
            ans = A.PosCal(str);
            System.out.println(ans.toString());
        }
    }
}



