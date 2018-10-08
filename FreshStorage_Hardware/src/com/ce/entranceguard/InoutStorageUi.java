package com.ce.entranceguard;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import com.ce.serialport.FindCardUtils;
import com.ce.socket.UrlConnector;

public class InoutStorageUi {

	protected Shell shlInoutstorage;
	Thread thread;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InoutStorageUi window = new InoutStorageUi();
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
		shlInoutstorage.open();
		shlInoutstorage.layout();
		
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
						if(UrlConnector.outGoodstoUrl()){
							MessageBox dialog = new MessageBox(shlInoutstorage, SWT.OK);
							dialog.setText("提示");
							dialog.setMessage("出库成功！！！");
							dialog.open();
						
						}else{
							MessageBox dialog = new MessageBox(shlInoutstorage, SWT.OK);
							dialog.setText("警告");
							dialog.setMessage("该商品未入库！！！");
							dialog.open();
						}
					}
				});
			}
		});
		thread.start();
		
		while (!shlInoutstorage.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlInoutstorage = new Shell();
		shlInoutstorage.setSize(450, 300);
		shlInoutstorage.setText("\u5546\u54C1\u51FA\u5165\u5E93\u7CFB\u7EDF");
		
		Button inBtn = new Button(shlInoutstorage, SWT.NONE);
		inBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				FindCardUtils.closeCOM4();
				
				shlInoutstorage.close();
				InUi in = new InUi();
				in.open();
			}
		});
		inBtn.setBounds(33, 48, 98, 30);
		inBtn.setText("\u5165\u5E93");

		//关闭窗口时停止寻卡、关闭串口
		shlInoutstorage.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e){
				thread.stop();
				FindCardUtils.closeCOM4();
			}
		});
		
	}
}
