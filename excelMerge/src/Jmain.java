import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Jmain implements ActionListener {
    private JFrame frame = new JFrame("合并Excel（替换关键字）");// 框架布局
    private JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
    private Container con = new Container();//
    private JLabel label1 = new JLabel("Excel文件1");
    private JLabel label2 = new JLabel("Excel文件2");
    private JLabel label3 = new JLabel("输出路径");
    private JLabel label4 = new JLabel("索引列");
    private JLabel label5 = new JLabel("注：列编号从0开始，多个索引列使用\";\"分隔");
    private JTextField text1 = new JTextField();
    private JTextField text2 = new JTextField();
    private JTextField text3 = new JTextField();
    private JTextField text4 = new JTextField();
    private JButton button1 = new JButton("...");// 选择
    private JButton button2 = new JButton("...");// 选择
    private JButton button3 = new JButton("...");// 选择
    private JFileChooser jfc = new JFileChooser();// 文件选择器
    private JButton button4 = new JButton("确定");//

    private String first = "";
    private String second = "";
    private String output = "";

    public static void main(String[] args) {
        new Jmain();
    }

    public Jmain() {
        jfc.setCurrentDirectory(new File("c://"));// 文件选择器的初始目录定为c盘

        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(300, 250);// 设定窗口大小
        frame.setContentPane(tabPane);// 设置布局

        label1.setBounds(10, 10, 70, 20);
        text1.setBounds(75, 10, 120, 20);
        button1.setBounds(210, 10, 50, 20);

        label2.setBounds(10, 35, 70, 20);
        text2.setBounds(75, 35, 120, 20);
        button2.setBounds(210, 35, 50, 20);

        label3.setBounds(10, 60, 70, 20);
        text3.setBounds(75, 60, 120, 20);
        button3.setBounds(210, 60, 50, 20);

        label4.setBounds(10, 85, 70, 20);
        text4.setBounds(75, 85, 120, 20);

        label5.setBounds(10, 110, 200, 20);
        button4.setBounds(100, 135, 50, 20);

        label5.setFont(new Font("宋体", Font.PLAIN, 10));

        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        button4.addActionListener(this); // 添加事件处理

        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(label2);
        con.add(text2);
        con.add(button2);
        con.add(label3);
        con.add(text3);
        con.add(button3);
        con.add(label4);
        con.add(text4);
        con.add(label5);
        con.add(button4);

        frame.setVisible(true);// 窗口可见
        frame.setResizable(false);//不可改变大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
        tabPane.add("1面板", con);// 添加布局1
    }
    /**
     * 时间监听的方法
     */
    public void actionPerformed(ActionEvent e) {

        /**
         * 监听按钮，绑定到文本
         */
        first = getFileName(e, button1, text1, first,0);
        second = getFileName(e, button2, text2, second, 0);
        output = getFileName(e, button3, text3, output, 1);

        if (e.getSource().equals(button4)) {
            String[] t = text4.getText().split(";");
            int[] colNo = new int[t.length];
            for(int i=0; i<colNo.length; i++){
                colNo[i] = Integer.valueOf(t[i]);
            }
            if("".equals(first)){
                JOptionPane.showMessageDialog(null, "请选择Excel文件1", "提示", 2);
            }else if("".equals(second)){
                JOptionPane.showMessageDialog(null, "请选择Excel文件2", "提示", 2);
            }else if("".equals(output)){
                JOptionPane.showMessageDialog(null, "输出路径", "提示", 2);
            }else if(colNo.length == 0){
                JOptionPane.showMessageDialog(null, "请输入索引列值", "提示", 2);
            }else{
                try {
                    ExcelMerge.mergeAndOutput(first, second, output, colNo);
                    JOptionPane.showMessageDialog(null, "合并完成", "成功", 2);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", 2);
                }
            }
        }
    }

    private String getFileName(ActionEvent e, JButton btn, JTextField text, String file, int type){
        if (e.getSource().equals(btn)) {// 判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(type);// 设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return file;
            } else {
                File f = jfc.getSelectedFile();// f为选择到的目录
                text.setText(f.getAbsolutePath());
                return f.getAbsolutePath();
            }
        }
        return file;
    }
}
