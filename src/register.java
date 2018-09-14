import com.mongodb.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class register {
    private JPanel main;
    private JTextField tfusername;
    private JPasswordField pfpassword;
    private JTextField tfnickname;
    private JRadioButton MALE;
    private JRadioButton FEMALE;
    private JButton CONFIRMButton;
    private JButton CLEARButton;
    private JLabel username;
    private JLabel password;
    private JLabel nicename;
    private JLabel GENDER;
    static MongoClientURI uri ;
    static MongoClient mongo ;
    static DB db;
    static DBCollection user;
    static DBObject checkUsername;
    static DBObject checkName;
    static String gender;



    public register() {

        MALE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MALE.isSelected()){
                    FEMALE.setSelected(false);
                    FEMALE.updateUI();
                    gender = "male";
                }

            }
        });
        FEMALE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FEMALE.isSelected()){
                    MALE.setSelected(false);
                    MALE.updateUI();
                    gender = "female";
                }
            }
        });

        CONFIRMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        register from = new register();
        frame.setContentPane(from.main);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        from.connect();
        frame.setVisible(true);

    }


    public  void connect(){
        try {
            uri = new MongoClientURI("mongodb://admin:88634159sd@ds245532.mlab.com:45532/ox");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("User");

        }catch (IOException ex) {

        }
    }

    public void submit(){
       BasicDBObject searchQuery1 = new BasicDBObject();
       BasicDBObject searchQuery2 = new BasicDBObject();
       searchQuery1.put("username",tfusername.getText());
       searchQuery2.put("name",tfnickname.getText());
       checkUsername =  user.findOne(searchQuery1);
       checkName = user.findOne(searchQuery2);
       if(tfnickname.getText().isEmpty() || pfpassword.getPassword().length==0 || tfusername.getText().isEmpty() || gender==null){
           JOptionPane.showMessageDialog(null,"ใส่ข้อมูลให้ครบ");
       }else if (checkUsername!=null){
           JOptionPane.showMessageDialog(null,"USERNAME ซ้ำ");
       }else if (checkName!=null){
           JOptionPane.showMessageDialog(null,"NICKNAME ซ้ำ");
       }else {
           BasicDBObject add = new BasicDBObject();
           add.put("name" , tfnickname.getText());
           add.put("username" , tfusername.getText());
           add.put("password" , new String(pfpassword.getPassword()));
           if (MALE.isSelected()){
               add.put("gender",gender);
           }else if (FEMALE.isSelected()){
               add.put("genger",gender);
           }
           user.insert(add);
           JOptionPane.showMessageDialog(null,"สมัครสมาชิกสำเร็จ");
           reset();
       }


    }

    private void reset() {
        tfusername.setText("");
        tfnickname.setText("");
        pfpassword.setText("");
        MALE.setSelected(false);
        FEMALE.setSelected(false);
    }


}