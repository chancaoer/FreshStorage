package com.ce.entranceguard;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.ce.serialport.ByteUtils;
import com.ce.serialport.FindCardUtils;
import com.ce.socket.UrlConnector;
import com.ce.zigbee.ExecuteB;


public class AddUi {

	protected static Shell shlAddCardManagement;
	private Text cardIdText;
	private Text nameText;
	
	public static String cardId = "";
	public static String name = "";
	
	Thread thread;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		shlAddCardManagement = new Shell(display);
		try {
			AddUi window = new AddUi();
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
		shlAddCardManagement.open();
		shlAddCardManagement.layout();
		
		FindCardUtils.openCOM4();
		thread = new Thread(new FindCardUtils() {
			
			@Override
			public void listen(final String subString) {
				// TODO Auto-generated method stub
				display.syncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						cardIdText.setText(subString);
					}
				});
			}
		});
		thread.start();
		
		while (!shlAddCardManagement.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAddCardManagement = new Shell();
		shlAddCardManagement.setSize(450, 300);
		shlAddCardManagement.setText("\u6DFB\u52A0\u65B0\u5458\u5DE5");
		
		Label lblNewLabel = new Label(shlAddCardManagement, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(27, 41, 76, 20);
		lblNewLabel.setText("\u95E8\u7981\u5361\u53F7");
		
		Label lblName = new Label(shlAddCardManagement, SWT.NONE);
		lblName.setAlignment(SWT.CENTER);
		lblName.setBounds(27, 81, 76, 20);
		lblName.setText("\u5458\u5DE5\u59D3\u540D");
		
		cardIdText = new Text(shlAddCardManagement, SWT.BORDER);
		cardIdText.setBounds(109, 41, 105, 26);
		
		nameText = new Text(shlAddCardManagement, SWT.BORDER);
		nameText.setBounds(109, 81, 105, 26);
		
		Button addBtn = new Button(shlAddCardManagement, SWT.NONE);
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//把cardid和name传到url
				cardId = cardIdText.getText();
				name = nameText.getText();
				
//				if(true){
				if(UrlConnector.addUserUrl()){
					MessageBox dialog = new MessageBox(shlAddCardManagement, SWT.OK);
					dialog.setText("通知");
					dialog.setMessage("注册成功！！！");
					dialog.open();
				}else{
					MessageBox dialog = new MessageBox(shlAddCardManagement, SWT.OK);
					dialog.setText("警告");
					dialog.setMessage("该卡已注册过！！！");
					dialog.open();
				}
			}
		});
		addBtn.setBounds(197, 200, 98, 30);
		addBtn.setText("\u6DFB\u52A0");
		
		Button exitBtn = new Button(shlAddCardManagement, SWT.NONE);
		exitBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				FindCardUtils.closeCOM4();
				
				shlAddCardManagement.close();
			}
		});
		exitBtn.setBounds(324, 200, 98, 30);
		exitBtn.setText("\u5173\u95ED");

	}
}

