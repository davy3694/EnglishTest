/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package englishtest;

/**
 *
 * @author Administrator
 */
import java.io.*;
import java.util.*;
public class ReadTestquestion
{  String filename="",         
   correctAnswer="",           
   testContent="" ,            
   selection="" ;              
   int score=0;               
   long time=0;               
   boolean 完成考试=false;    
   File f=null;
   FileReader in=null;
   BufferedReader 读取=null;
  public void setFilename(String name)
      {   filename=name; 
           
           
            score=0;
            selection="";
          try { 
                if(in!=null&&读取!=null)
                  {
                     in.close();             
                     读取.close();
                  }
                f=new File(filename);
                in=new FileReader(f);
                读取=new BufferedReader(in);            
                correctAnswer=(读取.readLine()).trim(); 
                String temp=(读取.readLine()).trim()  ; 
                StringTokenizer token=new StringTokenizer(temp,":");
                int hour=Integer.parseInt(token.nextToken()) ;      
                int minute=Integer.parseInt(token.nextToken());    
                int second=Integer.parseInt(token.nextToken());    
                time=1000*(second+minute*60+hour*60*60);           
               
              }
           catch(Exception e)
              {
                testContent="没有选择试题";
              }  
      }
  public String getFilename()
      { 
         return filename;
      }
  public long getTime()
      {
         return time;
      }
  public void set完成考试(boolean b)
      {
        完成考试=b;
      }
  public boolean get完成考试()
      {
        return 完成考试;
      } 
  public String getTestContent()  
        { try {  
                 String s=null;
                 StringBuffer temp=new StringBuffer();
               if(读取!=null)                       
                  {
                   while((s=读取.readLine())!=null) 
                     { 
                       if(s.startsWith("**")) 
                           break;
                       temp.append("\n"+s);
                       if(s.startsWith("endend")) 
                        {
                          in.close();             
                          读取.close();  
                          完成考试=true;         
                        }
                   }
                  testContent=new String(temp); 
                  }
               else
                  {
                     testContent=new String("没有选择试题");
                  } 
              } 
          catch(Exception e)
              { 
                 testContent="试题内容为空,考试结束！！";
              }
          return testContent;
        }
  public void setSelection(String s)
        {  
           selection=selection+s; 
        }
  public int getScore()
        {  score=0;
           int length1=selection.length();   
           int length2=correctAnswer.length();
           int min=Math.min(length1,length2);
           for(int i=0;i<min;i++)
              { try{  
                    if(selection.charAt(i)==correctAnswer.charAt(i))
                          score++;
                   }
                catch(StringIndexOutOfBoundsException e) 
                   { 
                      i=0;
                   }
              }
           return score;
        }
   public String getMessages()
        {
          int length1=selection.length();  
          int length2=correctAnswer.length();
          int length=Math.min(length1,length2);
          String message="正确答案:"+correctAnswer.substring(0,length)+"\n"+
                         "你的回答:"+selection+"\n";
          return message;
        }
   
}

