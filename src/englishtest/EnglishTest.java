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
import java.awt.event.*;
import javax.swing.*;
public class EnglishTest extends JFrame
{
  TestArea testPanel=null;
  Container con=null;
  public EnglishTest()
  {
    super("标准化模拟考试");
    testPanel=new TestArea();
    con=getContentPane();
    con.add(testPanel,BorderLayout.CENTER); 
    addWindowListener(new WindowAdapter()
                 { public void windowClosing(WindowEvent e)
                     { System.exit(0);
      	             }
                 });
    setVisible(true);
    setBounds(60,40,660,460);
    con.validate();
    validate();
  }
 public static void main(String args[])
   {
      new EnglishTest();
   }
}
