package com.ce.entranceguard;

import java.awt.Window;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AdminLoginUi {

	protected Shell shlAdLogin;
	private Text zhanghao;
	private Text mima;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AdminLoginUi window = new AdminLoginUi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAdLogin.open();
		shlAdLogin.layout();
		while (!shlAdLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAdLogin = new Shell();
		shlAdLogin.setSize(450, 300);
		shlAdLogin.setText("\u7BA1\u7406\u5458\u767B\u5F55");
		
		zhanghao = new Text(shlAdLogin, SWT.BORDER);
		zhanghao.setBounds(126, 23, 89, 26);
		
		Label lblUser = new Label(shlAdLogin, SWT.NONE);
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setToolTipText("");
		lblUser.setBounds(10, 26, 97, 20);
		lblUser.setText("\u7BA1\u7406\u5458\u8D26\u53F7");
		
		Label lblPassword = new Label(shlAdLogin, SWT.NONE);
		lblPassword.setAlignment(SWT.CENTER);
		lblPassword.setBounds(10, 63, 97, 20);
		lblPassword.setText("\u7BA1\u7406\u5458\u5BC6\u7801");
		
		mima = new Text(shlAdLogin, SWT.PASSWORD);
		mima.setBounds(126, 55, 89, 26);
		
		Button btnLogin = new Button(shlAdLogin, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(zhanghao.getText().equals("admin") && mima.getText().equals("123456")){
					//System.out.println(text.getText());
					shlAdLogin.close();
					AddUi aUi = new AddUi();
					aUi.open();
				}else{
					MessageBox dialog = new MessageBox(shlAdLogin, SWT.OK);
					dialog.setText("warning");
					dialog.setMessage("The wrong userId or password£¡£¡£¡");
					dialog.open();
				}
					
			}
		});
		btnLogin.setBounds(256, 58, 98, 30);
		btnLogin.setText("\u767B\u5F55");

	}
}
