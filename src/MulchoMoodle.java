import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Label;


public class MulchoMoodle {

	static MulchoMoodle instance;
	private JFrame frmMulchomoodleExportc;
	private JTextField txtDatabase;
	private JTextField txtLogin;
	private JTextField txtPassword;
	static mm m;
	JComboBox themenComboBox;
	JList list = new JList();
	JLabel infolbl;
	JFileChooser chooser = new JFileChooser();
	JFileChooser dirchooser = new JFileChooser();
	JButton exportButton;
	JCheckBox chckbxAnzahlDerRichtigen;
	
	LinkedList themes;
	LinkedList questions;
	private JTextField txtCtemp;
	private JTextField bildertextField;
	private JLabel lblBilderverzecinis;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MulchoMoodle window = new MulchoMoodle();
					instance = window;
					window.frmMulchomoodleExportc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		m = new mm();
		
	}

	/**
	 * Create the application.
	 */
	public MulchoMoodle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMulchomoodleExportc = new JFrame();
		frmMulchomoodleExportc.setIconImage(Toolkit.getDefaultToolkit().getImage(MulchoMoodle.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		frmMulchomoodleExportc.setResizable(false);
		frmMulchomoodleExportc.setTitle("MulchoMoodle Export V1.1 (c) 2011\\2013 by Dr. J\u00F6rg Tuttas");
		frmMulchomoodleExportc.setBounds(100, 100, 638, 508);
		frmMulchomoodleExportc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMulchomoodleExportc.getContentPane().setLayout(null);
		
		txtDatabase = new JTextField();
		txtDatabase.setText("mc");
		txtDatabase.setBounds(130, 11, 73, 20);
		frmMulchomoodleExportc.getContentPane().add(txtDatabase);
		txtDatabase.setColumns(10);
		
		txtLogin = new JTextField();
		txtLogin.setText("tuttas");
		txtLogin.setBounds(303, 11, 67, 20);
		frmMulchomoodleExportc.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Database");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setBounds(74, 14, 46, 14);
		frmMulchomoodleExportc.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Benutzername");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(213, 14, 80, 14);
		frmMulchomoodleExportc.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Kennwort");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(377, 14, 53, 14);
		frmMulchomoodleExportc.getContentPane().add(lblNewLabel_2);
		
		txtPassword = new JTextField();
		txtPassword.setText("joerg123");
		txtPassword.setBounds(435, 11, 67, 20);
		frmMulchomoodleExportc.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton.setIcon(new ImageIcon(MulchoMoodle.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean r = m.connect(txtDatabase.getText(),txtLogin.getText(),txtPassword.getText());
				if (r) {
					exportButton.setEnabled(true);
					infolbl.setText("Connected");
					themenComboBox.setEnabled(true);
					themes = m.getThemes();
					if (themes!=null) {
						themenComboBox.setModel(new DefaultComboBoxModel(themes.toArray()));
						Theme firstTheme = (Theme)themes.getFirst();
						list.setEnabled(true);
						questions = m.getQuestions(firstTheme);
						
						if (questions!=null) {
							list.setModel(new AbstractListModel() {
								Object[] values = questions.toArray();
								public int getSize() {
									return values.length;
								}
								public Object getElementAt(int index) {
									return values[index];
								}
							});
							
						}
						
					}
				}
				else {
					infolbl.setText("Failed to connect to Database");
				}
			}
		});
		btnNewButton.setBounds(512, 10, 99, 23);
		frmMulchomoodleExportc.getContentPane().add(btnNewButton);
		
		themenComboBox = new JComboBox();
		themenComboBox.setBorder(null);
		themenComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println ("Action:"+themenComboBox.getSelectedIndex());
				Theme theme = (Theme)themes.get(themenComboBox.getSelectedIndex());
				questions = m.getQuestions(theme);
				if (questions!=null) {
					list.setModel(new AbstractListModel() {
						Object[] values = questions.toArray();
						public int getSize() {
							return values.length;
						}
						public Object getElementAt(int index) {
							return values[index];
						}
					});
					
				}
			}
		});
		themenComboBox.setEnabled(false);
		themenComboBox.setBounds(74, 73, 537, 20);
		frmMulchomoodleExportc.getContentPane().add(themenComboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Themen");
		lblNewLabel_3.setBounds(18, 76, 46, 14);
		frmMulchomoodleExportc.getContentPane().add(lblNewLabel_3);
		
		JLabel lblFragen = new JLabel("Fragen");
		lblFragen.setBounds(18, 101, 46, 14);
		frmMulchomoodleExportc.getContentPane().add(lblFragen);
		
		exportButton = new JButton("Export");
		exportButton.setIcon(new ImageIcon(MulchoMoodle.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					String s="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
	                s=s+"<quiz>\n";
					Object[] qe = (Object[])list.getSelectedValues();
					if (qe.length!=0) {
						/*
		                int inum=0;  
						for (int i=0;i<qe.length;i++) {
							Question q = (Question)qe[i];
							if(q.getImageFile()!=null) {
								inum++;
								instance.copy(new File(instance.bildertextField.getText()+"\\"+q.getImageFile()), new File(txtCtemp.getText().substring(0, txtCtemp.getText().lastIndexOf("\\")+1)+q.getImageFile()));
							}
						}
						*/
						for (int i=0;i<qe.length;i++) {
							Question q = (Question)qe[i];
			                s=s+q.toMoodleString(chckbxAnzahlDerRichtigen.isSelected(),Integer.parseInt(instance.gradetextField.getText()),Float.parseFloat(instance.penaltytextField.getText()),new File(instance.bildertextField.getText()+"\\"));
						}
		                s=s+"</quiz>\n";
		                System.out.println("------ OUTPUT ------\n"+s);
		                File file = new File(txtCtemp.getText());
		                try {
		                  FileWriter writer = new FileWriter(file);
		                  
		                  // Text wird in den Stream geschrieben
		                  writer.write(s);		                  
		                  writer.flush();		                  
		                  writer.close();
 		                 } catch (IOException e) {
			                 e.printStackTrace();
			                 infolbl.setText(e.getMessage());
			             }		                
		                
			                  
  		                infolbl.setText("Exported "+qe.length+" Questions to "+txtCtemp.getText());
					}
					else {
		                infolbl.setText("No Questions selected");

					}
			
			}
		});
		exportButton.setEnabled(false);
		exportButton.setBounds(407, 426, 95, 23);
		frmMulchomoodleExportc.getContentPane().add(exportButton);
		//frmMulchomoodleExportc.getContentPane().add(list);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(new LineBorder(new Color(0, 0, 0)));
		toolBar.setBackground(Color.WHITE);
		toolBar.setBounds(0, 460, 632, 20);
		frmMulchomoodleExportc.getContentPane().add(toolBar);
		
		infolbl = new JLabel("Not connected");
		toolBar.add(infolbl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(74, 104, 537, 247);
		frmMulchomoodleExportc.getContentPane().add(scrollPane);
		
		txtCtemp = new JTextField();
		txtCtemp.setText("c:\\Temp\\test.xml");
		txtCtemp.setBounds(74, 429, 260, 20);
		frmMulchomoodleExportc.getContentPane().add(txtCtemp);
		txtCtemp.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("...");
		btnNewButton_2.setIcon(new ImageIcon(MulchoMoodle.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser.setCurrentDirectory(new File(txtCtemp.getText().substring(0, txtCtemp.getText().lastIndexOf("\\"))));
				chooser.setSelectedFile(
						new File(chooser.getCurrentDirectory().getAbsolutePath() +
						"\\" + txtCtemp.getText().substring(txtCtemp.getText().lastIndexOf("/")+1) ));
				int returnVal = chooser.showDialog(frmMulchomoodleExportc, "Open");
				 
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			       txtCtemp.setText(chooser.getCurrentDirectory()+"\\"+chooser.getSelectedFile().getName());
			    }
			}
			
		});
		btnNewButton_2.setBounds(344, 426, 53, 23);
		frmMulchomoodleExportc.getContentPane().add(btnNewButton_2);
		
		chckbxAnzahlDerRichtigen = new JCheckBox("Anzahl der richtigen Antworten anzeigen");
		chckbxAnzahlDerRichtigen.setSelected(true);
		chckbxAnzahlDerRichtigen.setBounds(74, 358, 296, 23);
		frmMulchomoodleExportc.getContentPane().add(chckbxAnzahlDerRichtigen);
		
		list = new JList();
		list.setAutoscrolls(false);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setVisibleRowCount(4);
		list.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		list.setBounds(76, 104, 535, 247);
		scrollPane.setViewportView(list);
		
		bildertextField = new JTextField();
		bildertextField.setText(".");
		bildertextField.setBounds(303, 47, 199, 20);
		frmMulchomoodleExportc.getContentPane().add(bildertextField);
		bildertextField.setColumns(10);
		
		lblBilderverzecinis = new JLabel("Bilderverzeichnis");
		lblBilderverzecinis.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBilderverzecinis.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBilderverzecinis.setBounds(204, 48, 89, 14);
		frmMulchomoodleExportc.getContentPane().add(lblBilderverzecinis);
		
		button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dirchooser.setCurrentDirectory(new File(bildertextField.getText()));
				int returnVal = dirchooser.showDialog(frmMulchomoodleExportc, "Select");
				 
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       bildertextField.setText(dirchooser.getCurrentDirectory().getPath()+"\\"+dirchooser.getSelectedFile().getName());
			    }
				
			}
		});
		button.setIcon(new ImageIcon(MulchoMoodle.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		button.setBounds(512, 44, 99, 23);
		frmMulchomoodleExportc.getContentPane().add(button);
		
		gradetextField = new JTextField();
		gradetextField.setText("1");
		gradetextField.setBounds(471, 359, 31, 20);
		frmMulchomoodleExportc.getContentPane().add(gradetextField);
		gradetextField.setColumns(10);
		
		penaltytextField = new JTextField();
		penaltytextField.setText("0.1");
		penaltytextField.setBounds(573, 359, 39, 20);
		frmMulchomoodleExportc.getContentPane().add(penaltytextField);
		penaltytextField.setColumns(10);
		
		lblNewLabel_4 = new JLabel("defaultgrade");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(383, 362, 78, 14);
		frmMulchomoodleExportc.getContentPane().add(lblNewLabel_4);
		
		JLabel lblPenalty = new JLabel("penalty");
		lblPenalty.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPenalty.setBounds(517, 362, 46, 14);
		frmMulchomoodleExportc.getContentPane().add(lblPenalty);
		
		dirchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.addChoosableFileFilter(
		new FileFilter() {
		    public boolean accept(File f) {
		      if (f.isDirectory()) return true;
		      return f.getName().toLowerCase().endsWith(".xml");
		    }
		    public String getDescription () { return "Moodle XMLs"; }  
		  });

		

	}
	long chunckSizeInBytes = 1024 * 1024; //Standard: Buffer 1MB;
	boolean verbose=true;
	private JTextField gradetextField;
	private JTextField penaltytextField;
	private JLabel lblNewLabel_4;
	
	public void copy(File source, File destination) {
		
		//System.out.println ("Copy from:"+source.getAbsolutePath()+" -->"+destination.getAbsolutePath());
		try {
			FileInputStream fileInputStream = new FileInputStream(source);
			FileOutputStream fileOutputStream = new FileOutputStream(destination);
			FileChannel inputChannel = fileInputStream.getChannel();
			FileChannel outputChannel = fileOutputStream.getChannel();
			transfer(inputChannel, outputChannel, source.length(), false);
			fileInputStream.close();
			fileOutputStream.close();
			destination.setLastModified(source.lastModified());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void transfer(FileChannel fileChannel, ByteChannel byteChannel, long lengthInBytes, boolean verbose)	throws IOException {
		long overallBytesTransfered = 0L;
		long time = -System.currentTimeMillis();
		while (overallBytesTransfered < lengthInBytes) {
			long bytesTransfered = 0L;
			bytesTransfered = fileChannel.transferTo(overallBytesTransfered, Math.min(chunckSizeInBytes, lengthInBytes - overallBytesTransfered), byteChannel);
			overallBytesTransfered += bytesTransfered;
			if (verbose) {
				System.out.println("overall bytes transfered: " + overallBytesTransfered + " progress " + (Math.round(overallBytesTransfered / ((double) lengthInBytes) * 100.0)) + "%");
			}
		}
		time += System.currentTimeMillis();
		if (verbose) {
			System.out.println("Transfered: " + overallBytesTransfered + " bytes in: " + (time / 1000) + " s -> " + (overallBytesTransfered / 1024.0) / (time / 1000.0) + " kbytes/s");
		}
	}
}

