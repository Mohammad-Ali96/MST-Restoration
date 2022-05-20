package mst_restoration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.control.SplitPane;
import javax.swing.*;

public class MST_GUI extends JFrame {

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label_cost1;
    private JLabel label_cost2;
//    private JLabel label_time1;
//    private JLabel label_time2;

    JSeparator separator1;
    JSeparator separator2;

    private JTextField txt1;
    private JTextField txt2;
    private JTextField utxt;
    private JTextField vtxt;

    private JButton btn1;
    private JButton btn2;
    private JButton btn_mst;
    private JButton btn4;
    private JButton btn5;
    private JButton btn_delete1;
    private JButton btn_delete2;
    private JButton clear;
    private JButton exit;
    private JTextArea area;

    MST mst;

    MST_GUI() {
        this.setVisible(true);
        this.setSize(800, 580);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("MST RESTORATION");
        this.setResizable(false);
        this.setLayout(null);

        this.label1 = new JLabel("input number of nodes:");
        this.label2 = new JLabel("input number of edges:");
        this.label_cost1 = new JLabel("The Cost Of MST: ");
        this.label_cost2 = new JLabel("The Cost Of new MST: ");
//        this.label_time1 = new JLabel("The Time: ");

        label3 = new JLabel("Compute Minimum Spaning Tree MST (Prims Algorithm): ");
        label3.setFont(new Font("Helvetics", Font.BOLD, 14));
        label4 = new JLabel("Deleting Edge");
        label4.setFont(new Font("Helvetics", Font.BOLD, 18));

        this.txt1 = new JTextField();
        this.txt2 = new JTextField();
        this.utxt = new JTextField();
        this.vtxt = new JTextField();

        btn1 = new JButton("Read Graph From KeyBoard");
        btn2 = new JButton("Generate Random Graph");
        btn_mst = new JButton("Run Prims Algorithm");
        exit = new JButton("exit");
        clear = new JButton("clear");

        btn_delete1 = new JButton("Select Min Weight Edge");
        btn_delete2 = new JButton("Implement Again MST");

        separator1 = new JSeparator();
        separator2 = new JSeparator();

        area = new JTextArea(10, 10);
        
        
        
        
        // add Element
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label_cost1);
        this.add(label_cost2);
//        this.add(label_time1);

        this.add(txt1);
        this.add(txt2);
        this.add(separator1);
        this.add(separator2);
        this.add(utxt);
        this.add(vtxt);

        this.add(btn1);
        this.add(btn2);
        this.add(btn_mst);
        this.add(btn_delete1);
        this.add(btn_delete2);
        this.add(clear);
        this.add(exit);
        this.add(area);

        // set Bounds
        label1.setBounds(20, 20, 180, 40);
        txt1.setBounds(200, 27, 160, 28);

        label2.setBounds(20, 60, 180, 40);
        txt2.setBounds(200, 67, 160, 28);
        btn1.setBounds(60, 117, 260, 34);
        btn2.setBounds(60, 166, 260, 34);
        separator1.setBounds(0, 220, 410, 14);
        label3.setBounds(10, 220, 410, 34);
        btn_mst.setBounds(60, 270, 260, 34);
        label_cost1.setBounds(10, 310, 180, 34);
//        label_time1.setBounds(10, 344, 180, 34);
        separator2.setBounds(0, 390, 410, 14);

        label4.setBounds(135, 390, 400, 34);
        btn_delete1.setBounds(10, 480, 190, 26);
        btn_delete2.setBounds(225, 480, 190, 26);
        utxt.setBounds(120, 440, 80, 22);
        vtxt.setBounds(225, 440, 80, 22);
        exit.setBounds(680, 500, 90, 26);
        area.setBounds(450, 60, 320, 380);
        label_cost2.setBounds(450, 10, 200, 60);
//        btn_delete1.setBounds(135, 390, 260, 34);

        btn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int n = Integer.parseInt(txt1.getText());
                int m = Integer.parseInt(txt2.getText());
                mst = new MST(n, m);

                for (int i = 0; i < m; i++) {
                    String u = JOptionPane.showInputDialog("input vertex u");
                    String v = JOptionPane.showInputDialog("input vertex v");
                    String w = JOptionPane.showInputDialog("input weight u,v");
                    mst.adjMatrix[Integer.parseInt(u)][Integer.parseInt(v)] = Integer.parseInt(w);
                    mst.adjMatrix[Integer.parseInt(v)][Integer.parseInt(u)] = Integer.parseInt(w);
                    
                }

            }
        });
        
        
        
        btn_mst.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mst.runMST();
                area.setText("");
                String s = "";
                s = "The MST Tree is:\n";
                for(int i=1;i<mst.n;i++){
                    s += i + " ---> " + mst.parent[i] + "\n";
                }
                area.setText(s);
                label_cost1.setText("The Cost of MST is " + mst.weightMST());
                label_cost2.setText("The Cost of MST is " + mst.weightMST());
                
            }
        });
        
        
        
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        
        
        btn_delete1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int u = Integer.parseInt(utxt.getText());
                int v = Integer.parseInt(vtxt.getText());
                mst.deleteEdge(u, v);
                
                area.setText("");
                String s = "";
                s = "The new MST Tree is:\n";
//                s += mst.msg_Min_Cut;
                for(int i=1;i<mst.n;i++){
                    if(u == i && v == mst.parent[i] || v ==i && u == mst.parent[i])
                        continue;
                    s += i + " ---> " + mst.parent[i] + "\n";
                }
                area.setText(s);
//                label_cost1.setText("The Cost of MST is " + mst.weightMST());
                label_cost2.setText("The Cost of MST is " + mst.weightMST());
                
            
                
            };
        });
        
        
        btn_delete2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int u = Integer.parseInt(utxt.getText());
                int v = Integer.parseInt(vtxt.getText());
                mst.deleteEdge(u, v);
                
                area.setText("");
                String s = "";
                s = "The new MST Tree is:\n";
//                s += mst.msg_Min_Cut;
                for(int i=1;i<mst.n;i++){
                    s += i + " ---> " + mst.parent[i] + "\n";
                }
                area.setText(s);
//                label_cost1.setText("The Cost of MST is " + mst.weightMST());
                label_cost2.setText("The Cost of MST is " + mst.costMST_Again);
                
            
                
            };
        });
        

        btn2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(txt1.getText());
                int m = Integer.parseInt(txt2.getText());
                mst = new MST(n, m);
                
                mst.generateRandomAdjMatrix();
                
                
            }
        });
        
        
        
    }

}
