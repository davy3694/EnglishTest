/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package englishtest;

/**
 *
 * @author Administrator
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
class FileName implements FilenameFilter  
{ 
    String str=null;
         FileName (String s)
         {
            str="."+s;
         }
    public  boolean accept(File dir,String name)
         { 
            return name.endsWith(str);
         }              
}
public class TestArea extends JPanel implements ActionListener,ItemListener,Runnable
{
  Choice list=null;                                  
  JTextArea 试题显示区=null,消息区=null;
  JCheckBox box[];                                   
  JButton  提交该题答案,读取下一题,查看分数;
  ReadTestquestion 读取试题=null;                    
  JLabel welcomeLabel=null;                           
  Thread  countTime=null;                           
  long time=0;                                       
  JTextField timeShow=null;                          
  boolean 是否关闭计时器=false,
          是否暂停计时=false;
  JButton 暂停或继续计时=null;
  public TestArea()
  {
    list= new Choice();
    String 当前目录=System.getProperty("user.dir");
    File dir=new File(当前目录);
    FileName fileTxt=new FileName("txt");
    String fileName[]=dir.list(fileTxt);            
    for(int i=0;i<fileName.length;i++) 
        {
          list.add(fileName[i]);
        }
   
    试题显示区=new  JTextArea(15,12);
    试题显示区.setLineWrap(true);                  
    试题显示区.setWrapStyleWord(true);             
    试题显示区.setFont(new Font("TimesRoman",Font.PLAIN,14));
    试题显示区.setForeground(Color.blue);     
    消息区=new  JTextArea(8,8);
    消息区.setForeground(Color.blue);  
    消息区.setLineWrap(true);                 
    消息区.setWrapStyleWord(true); 
   
    countTime=new  Thread(this);                    
    String s[]={"A","B","C","D"};
    box=new JCheckBox[4];
    for(int i=0;i<4;i++)
      {
        box[i]=new JCheckBox(s[i]);
      }
    暂停或继续计时=new JButton("暂停计时");
    暂停或继续计时.addActionListener(this);
    提交该题答案=new JButton("提交该题答案");
    读取下一题=new JButton("读取第一题");
    读取下一题.setForeground(Color.blue);
    提交该题答案.setForeground(Color.blue);
    查看分数=new JButton("查看分数");
    查看分数.setForeground(Color.blue);
    提交该题答案.setEnabled(false);
    提交该题答案.addActionListener(this);
    读取下一题.addActionListener(this);
    查看分数.addActionListener(this); 
    list.addItemListener(this);
    读取试题=new ReadTestquestion();
    JPanel pAddbox=new JPanel();
    for(int i=0;i<4;i++)
      {
        pAddbox.add(box[i]);
      }
    Box  boxH1=Box.createVerticalBox(),   
    boxH2=Box.createVerticalBox(),
    baseBox=Box.createHorizontalBox();    
    boxH1.add(new JLabel("选择试题文件"));
    boxH1.add(list);
    boxH1.add(new JScrollPane(消息区));
    boxH1.add(查看分数);
    timeShow=new JTextField(20);
    timeShow.setHorizontalAlignment(SwingConstants.RIGHT); 
    timeShow.setEditable(false);
    JPanel p1=new JPanel();
    p1.add(new JLabel("剩余时间："));
    p1.add(timeShow);                      
    p1.add(暂停或继续计时);
    boxH1.add(p1);                   
    boxH2.add(new JLabel("试题内容:"));
    boxH2.add(new JScrollPane(试题显示区));
    JPanel p2=new JPanel();
    p2.add(pAddbox);                       
    p2.add(提交该题答案);
    p2.add(读取下一题);
    boxH2.add(p2); 
    baseBox.add(boxH1);
    baseBox.add(boxH2);
    setLayout(new BorderLayout());
    add(baseBox,BorderLayout.CENTER);
    welcomeLabel=new JLabel("欢迎考试,提高英语水平",JLabel.CENTER);
    welcomeLabel.setFont(new Font("隶书",Font.PLAIN,24));
    welcomeLabel.setForeground(Color.blue);
    add(welcomeLabel,BorderLayout.NORTH);
    
  }
 public void itemStateChanged(ItemEvent e)
  {
     timeShow.setText(null);
     是否关闭计时器=false;
     是否暂停计时=false;
     暂停或继续计时.setText("暂停计时");
     String name=(String)list.getSelectedItem();
     读取试题.setFilename(name);
     读取试题.set完成考试(false);
     time=读取试题.getTime();                   
    if(countTime.isAlive())                     
        {
          是否关闭计时器=true;   
          countTime.interrupt();                
        }
     countTime=new Thread(this);                
     
     消息区.setText(null);
     试题显示区.setText(null);
     读取下一题.setText("读取第一题");
     提交该题答案.setEnabled(false);
     读取下一题.setEnabled(true);
     welcomeLabel.setText("欢迎考试,你选择的试题:"+读取试题.getFilename());
  }
 public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==读取下一题)
       {
         读取下一题.setText("读取下一题");
         提交该题答案.setEnabled(true);
         String contentTest=读取试题.getTestContent();
         试题显示区.setText(contentTest);
         消息区.setText(null);
         读取下一题.setEnabled(false);
          try {
                 countTime.start();               
              }
          catch(Exception event)
              {
                 
              }
       } 
    if(e.getSource()==提交该题答案)
       {
          读取下一题.setEnabled(true);
          提交该题答案.setEnabled(false);
          String answer="?";
          for(int i=0;i<4;i++)
             {
               if(box[i].isSelected())
                  {
                    answer=box[i].getText();
                    box[i].setSelected(false);
                    break;
                  } 
             }
          读取试题.setSelection(answer);
       }
     if(e.getSource()==查看分数)
       {
         int score=读取试题.getScore();
         String messages=读取试题.getMessages();
         消息区.setText("分数:"+score+"\n"+messages);
       }
     if(e.getSource()==暂停或继续计时)
       {
         if(是否暂停计时==false)
            {  
               暂停或继续计时.setText("继续计时");
               是否暂停计时=true;      
            }
         else if(是否暂停计时==true)
            { 
               暂停或继续计时.setText("暂停计时");
               是否暂停计时=false;   
               countTime.interrupt();  
            }
       }
  }
 public synchronized void run()                    
  {
     while(true)
      {
        if(time<=0)
           {
             是否关闭计时器=true;   
             countTime.interrupt();                
             提交该题答案.setEnabled(false);       
             读取下一题.setEnabled(false);         
             timeShow.setText("用时尽,考试结束");
           }
        else if(读取试题.get完成考试())
           {
             是否关闭计时器=true; 
             timeShow.setText("考试效果:分数*剩余时间(秒)="+1.0*读取试题.getScore()*(time/1000)); 
             countTime.interrupt();                
             提交该题答案.setEnabled(false);       
             读取下一题.setEnabled(false);         
             
           }
        else if(time>=1)
          {
            time=time-1000;
            long leftTime=time/1000;               
            long leftHour=leftTime/3600;           
            long leftMinute=(leftTime-leftHour*3600)/60;
            long leftSecond=leftTime%60;           
            timeShow.setText(""+leftHour+"小时"+leftMinute+"分"+leftSecond+"秒");
          }
         try
          {
            Thread.sleep(1000);                    
          }
         catch(InterruptedException ee)
          {
             if(是否关闭计时器==true)
               return ;                            
          }
         while(是否暂停计时==true)
          {
             try
                {
                    wait();                       
                }
            catch(InterruptedException ee)
                {
                  if(是否暂停计时==false)
                     {
                        notifyAll();             
                     } 
                }
          }
      }
  }
 
} 
