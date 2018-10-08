package com.ce.entranceguard;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.awt.Window;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;

import com.ce.serialport.ByteUtils;
import com.ce.serialport.FindCardUtils;
import com.ce.serialport.SerialPortManager;
import com.ce.socket.UrlConnector;
import com.ce.zigbee.ExecuteB;

public class WelcomeUi {

	protected static Shell shell;
	
	private Text text;
	private Button managerCard;
	
	//public static String cardId = "";
	
	Thread thread;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		shell = new Shell(display);
		try {
			WelcomeUi window = new WelcomeUi();
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		FindCardUtils.openCOM4();
		thread = new Thread(new FindCardUtils() {
			
			@Override
			public void listen(String subString) {
				// TODO Auto-generated method stub
				display.syncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						if(true){
						if(UrlConnector.EGsendtoUrl()){
							ExecuteB.setSerialPort();
							ExecuteB.light("0F");
							
							MessageBox dialog = new MessageBox(shell, SWT.OK);
							dialog.setText("提示");
							dialog.setMessage("刷卡成功！！！");
							dialog.open();
							
//							ExecuteB.setSerialPort();
							ExecuteB.light("00");
							ExecuteB.closeSerialPort();
							
						}else{
							MessageBox dialog = new MessageBox(shell, SWT.OK);
							dialog.setText("警告");
							dialog.setMessage("该卡未启用！！！");
							dialog.open();
						}
					}
				});
			}
		});
		thread.start();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("\u95E8\u7981\u7CFB\u7EDF");
		
		text = new Text(shell, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		text.setText("\u60A8\u597D\uFF0C\u6B22\u8FCE\u4F7F\u7528\u95E8\u7981\u7CFB\u7EDF\uFF0C\u8BF7\u5237\u5361\uFF01");
		text.setBounds(10, 10, 412, 38);
		
		managerCard = new Button(shell, SWT.NONE);
		managerCard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				FindCardUtils.closeCOM4();
				
				shell.close();
				AdminLoginUi adl = new AdminLoginUi();
				adl.open();
			}
		});
		managerCard.setBounds(324, 54, 98, 30);
		managerCard.setText("\u7BA1\u7406");

		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e){
				thread.stop();
				FindCardUtils.closeCOM4();
			}
		});
	}
}
