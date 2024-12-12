package ui.settings;

import common.provider.UIProvider;
import common.pool.CollaboratorThreadPool;

import javax.swing.*;
import java.awt.*;

public class SettingsTab extends JPanel {

    UIProvider uiProvider = UIProvider.INSTANCE;
    CollaboratorThreadPool collaboratorThreadPool = CollaboratorThreadPool.INSTANCE;

    public SettingsTab() {
        this.setLayout(null);

        Font burpFont = uiProvider.currentDisplayFont();

        int rootX = 0;
        int rootY = 0;

        JLabel threadPOolSettingLabel = new JLabel("线程池配置");
        threadPOolSettingLabel.setFont(
                new Font(burpFont.getName(), Font.BOLD, burpFont.getSize() + 7)
        );
        threadPOolSettingLabel.setBounds(
                rootX + 20,
                rootY + 20,
                200,
                20
        );

        JLabel corePoolSizeLabel = new JLabel("核心线程数:");
        corePoolSizeLabel.setFont(
                new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 1 )
        );
        corePoolSizeLabel.setBounds(
                threadPOolSettingLabel.getX(),
                threadPOolSettingLabel.getY() + threadPOolSettingLabel.getHeight()  + 5,
                70, 20
        );

        JTextField corePoolSizeInput = new JTextField();
        corePoolSizeInput.setBounds(
                corePoolSizeLabel.getX() + corePoolSizeLabel.getWidth() + 5,
                corePoolSizeLabel.getY(),
                200, 20
        );
        corePoolSizeInput.setText(
                String.valueOf(collaboratorThreadPool.getCorePoolSize())
        );

        JLabel maxPoolSizeLabel = new JLabel("最大线程数:");
        maxPoolSizeLabel.setFont(
                new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 1 )
        );
        maxPoolSizeLabel.setBounds(
                corePoolSizeLabel.getX(),
                corePoolSizeLabel.getY() + corePoolSizeLabel.getHeight()  + 5,
                70, 20
        );

        JTextField maxPoolSizeInput = new JTextField();
        maxPoolSizeInput.setBounds(
                maxPoolSizeLabel.getX() + maxPoolSizeLabel.getWidth() + 5,
                maxPoolSizeLabel.getY(),
                200, 20
        );
        maxPoolSizeInput.setText(
                String.valueOf(collaboratorThreadPool.getMaxPoolSize())
        );

        JButton saveButton = new JButton("保存");
        saveButton.setBounds(
                maxPoolSizeLabel.getX(),
                maxPoolSizeLabel.getY() + maxPoolSizeInput.getHeight() + 5,
                60, 20
        );
        saveButton.addActionListener(e -> {
            collaboratorThreadPool.setPoolSize(
                    Integer.valueOf(corePoolSizeInput.getText()),
                    Integer.valueOf(maxPoolSizeInput.getText())
            );
        });

        this.add(threadPOolSettingLabel);
        this.add(corePoolSizeLabel);
        this.add(corePoolSizeInput);
        this.add(maxPoolSizeLabel);
        this.add(maxPoolSizeInput);
        this.add(saveButton);

//        JPanel jPanel1 = new JPanel();
//
//        JLabel threadPoolSettingsLabel = new JLabel("线程池配置");
//        threadPoolSettingsLabel.setFont(
//                new Font(burpFont.getName(), Font.BOLD, burpFont.getSize() + 5)
//        );
//        threadPoolSettingsLabel.setBounds(
//                rootX + 20,
//                rootY + 20,
//                100,
//                100
//        );
//
//        JLabel inputLabel1 = new JLabel("核心线程池大小");
//        JTextField inputField1 = new JTextField();
//        inputLabel1.setBounds(
//                rootX + 20,
//                rootY + threadPoolSettingsLabel.getHeight() + 40,
//                inputLabel1.getWidth(),
//                inputLabel1.getHeight()
//        );
//        inputField1.setBounds(
//                rootX + inputLabel1.getWidth() + 20,
//                rootY + 40,
//                inputField1.getWidth(),
//                inputField1.getHeight()
//        );
//
//        jPanel1.add(inputLabel1);
//        jPanel1.add(inputField1);
//
//
//
//        jPanel1.add(threadPoolSettingsLabel);
//
////        JPanel jPanel2 = new JPanel(new CardLayout());
////        JLabel collaboratorSettingsLabel = new JLabel("Collaborator配置");
////        collaboratorSettingsLabel.setFont(
////                new Font(burpFont.getName(), Font.BOLD, burpFont.getSize() + 5)
////        );
////        jPanel2.add(collaboratorSettingsLabel);
//
//
//        this.add(jPanel1);
//
//        this.add(getHorizontalSeparator());
//        this.add(jPanel2);

    }

    private JSeparator getHorizontalSeparator() {
        JSeparator jSeparator = new JSeparator();
        jSeparator.setPreferredSize(
                new Dimension(uiProvider.getSuiteFrame().getWidth(), 20)
        );
        jSeparator.setBackground(new Color(242, 242, 242));
        return jSeparator;
    }
}
