package com.ce.entranceguard;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.ce.serialport.FindCardUtils;
import com.ce.socket.UrlConnector;

public class InUi {

	protected Shell shlIn;
	private Text goodsIdText;
	private Text goodsNameText;
	private Text storageIdText;
	private Text goodsStatusText;
	
	public static String goodsId = "";
	public static String goodsName = "";
	public static String storageId = "";
	public static String goodsStatus = "";
	
	Thread thread;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InUi window = new InUi();
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
		shlIn.open();
		shlIn.layout();
		
		FindCardUtils.openCOM4();
		thread = new Thread(new FindCardUtils() {
			
			@Override
			public void listen(final String subString) {
				// TODO Auto-generated method stub
				display.syncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						goodsIdText.setText(subString);
					}
				});
			}
		});
		thread.start();
		
		while (!shlIn.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlIn = new Shell();
		shlIn.setSize(450, 300);
		shlIn.setText("\u5165\u5E93\u7BA1\u7406");
		
		Label lblNewLabel = new Label(shlIn, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(27, 41, 76, 20);
		lblNewLabel.setText("\u5546\u54C1\u5361\u53F7");
		
		Label label = new Label(shlIn, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setBounds(29, 81, 76, 20);
		label.setText("\u5546\u54C1\u540D\u79F0");
		
		Label label_1 = new Label(shlIn, SWT.NONE);
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(29, 121, 76, 20);
		label_1.setText("\u6240\u5728\u4ED3\u5E93");
		
		Label label_2 = new Label(shlIn, SWT.NONE);
		label_2.setAlignment(SWT.CENTER);
		label_2.setBounds(29, 161, 76, 20);
		label_2.setText("\u5546\u54C1\u72B6\u6001");
		
		Button inBtn = new Button(shlIn, SWT.NONE);
		inBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				goodsId = goodsIdText.getText();
				goodsName = goodsNameText.getText();
				storageId = storageIdText.getText();
				goodsStatus = goodsStatusText.getText();

				//if(true){
				if(UrlConnector.addGoodsUrl()){
					MessageBox dialog = new MessageBox(shlIn, SWT.OK);
					dialog.setText("通知");
					dialog.setMessage("入库成功！！！");
					dialog.open();
				}else{
					MessageBox dialog = new MessageBox(shlIn, SWT.OK);
					dialog.setText("错误");
					dialog.setMessage("该商品已入库！！！");
					dialog.open();
				}
			
			}
		});
		inBtn.setBounds(209, 198, 98, 30);
		inBtn.setText("\u5165\u5E93");
		
		Button exitBtn = new Button(shlIn, SWT.NONE);
		exitBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				FindCardUtils.closeCOM4();
				
				shlIn.close();
				InoutStorageUi io = new InoutStorageUi();
				io.open();
			}
		});
		exitBtn.setBounds(324, 198, 98, 30);
		exitBtn.setText("\u8FD4\u56DE");
		
		goodsIdText = new Text(shlIn, SWT.BORDER);
		goodsIdText.setBounds(126, 41, 105, 26);
		
		goodsNameText = new Text(shlIn, SWT.BORDER);
		goodsNameText.setBounds(126, 81, 105, 26);
		
		storageIdText = new Text(shlIn, SWT.BORDER);
		storageIdText.setBounds(126, 121, 105, 26);
		
		goodsStatusText = new Text(shlIn, SWT.BORDER);
		goodsStatusText.setBounds(126, 161, 105, 26);

	}
}
