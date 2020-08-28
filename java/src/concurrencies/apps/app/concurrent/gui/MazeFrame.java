package app.concurrent.gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.prism.paint.Color;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MazeFrame extends JFrame{

	
	//裡面的new imageIcon 是抓取檔案並存成image 型態來做大小更改，外面再new 依次是將image 型態再變回imageIcon
	public ImageIcon wallIcon = new ImageIcon(
				new ImageIcon(concurrencies.utilities.Resource.PATH +"/apps/brickwall.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	public Icon heartIcon = new ImageIcon(
				new ImageIcon(concurrencies.utilities.Resource.PATH +"/apps/heart.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	public Icon diamondIcon = new ImageIcon(
				new ImageIcon(concurrencies.utilities.Resource.PATH +"/apps/diamond.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	//private final JLabel structureLabel[];
	private final JPanel allPnl = new JPanel();
	private final List<JLabel> blockLabel = new ArrayList<JLabel>();
	private final BloodPanel bloodPnl = new BloodPanel();
	public ScheduledExecutorService seService= Executors.newScheduledThreadPool(10);
	
	public MazeFrame() {
		super("Maze");
		//設定最大的Frame 排版
		setLayout(new BorderLayout());
		//設定10*10的迷宮地圖排版
		allPnl.setLayout(new GridLayout(10,10));
		
		//建立程序池
		ScheduledExecutorService seService = Executors.newScheduledThreadPool(10);
		//這個是用來放改變愛心的
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		//ImageIcon wallImg = new ImageIcon(wallIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		
		
	/*	try(
				Stream<String> stream =
					Files.lines(Paths.get("map.txt"))){
			stream.forEach(System.out::println);
		}catch(Exception e) {
			
		}
		*/
			SecureRandom random = new SecureRandom();
			Pattern pattern = Pattern.compile("\\s+");   //用來分隔地圖0,1中間的空格
		List<String> list;
		try {
			 list = 
					Files.lines(Paths.get(concurrencies.utilities.Resource.PATH +"/apps/map.txt"))//讀黨  地圖的格式是0101010的組成0是路  1 士強
					.flatMap(line->pattern.splitAsStream(line))   //用空格隔開並且展開
					.collect(Collectors.toList());
			 
			 
			 random.ints(10,0,100)  //隨機十個變數，0到100   當list 在x 的時候數值是1(代表牆壁)，變成3
			 		.boxed()
			 		.sorted()
			 		.collect(Collectors.groupingBy(Function.identity()))
			 		//.forEach((a,b)->System.out.println(a));
			 		.forEach((x,y)->{System.out.println(x);
			 						if(list.get(x).equals("1")) {
			 							list.set(x, "3");
			 						}//end if
			 		});
			 
			 list.stream()
				.forEach(x->{
					//System.out.println(x);//遍歷每一項，相對應的數字就給成相對應的icon，並將之加到GUI上
					if(x.equals("0")) {
						blockLabel.add(new JLabel());
						
						allPnl.add(blockLabel.get(blockLabel.size()-1));
						//這裡放listener 或是Anonymous 都沒有作用
						blockLabel.get(blockLabel.size()-1).addMouseListener(new mouseHandler(0));
				
					}//end 
					else if(x.equals("1")) {
						
						blockLabel.add(new JLabel(wallIcon));
						allPnl.add(blockLabel.get(blockLabel.size()-1));
						blockLabel.get(blockLabel.size()-1).addMouseListener(new mouseHandler(1));
					}//end else if
					else if(x.equals("2")) {
						blockLabel.add(new JLabel(diamondIcon));
						allPnl.add(blockLabel.get(blockLabel.size()-1));
						blockLabel.get(blockLabel.size()-1).addMouseListener(new mouseHandler(2));
					}//end else if
					else if(x.equals("3")) {
						blockLabel.add(new JLabel(heartIcon));
						allPnl.add(blockLabel.get(blockLabel.size()-1));
						blockLabel.get(blockLabel.size()-1).addMouseListener(
								new mouseHandler(3,blockLabel.get(blockLabel.size()-1)));
						//這裡每次初始化一個Label 都要將他放進一個執行續裡
						executorService.execute(new ChangeHeart(blockLabel.get(blockLabel.size()-1)));
						
					}//end else if
					
				});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//blockLabel.get(0).addMouseListener(new mouseHandler());
		//allPnl.addMouseListener(new mouseHandler());
		
		add(allPnl,BorderLayout.CENTER);
		add(bloodPnl,BorderLayout.NORTH);
		
		
		
	}//end constructor
	
	
	
	private class mouseHandler extends MouseAdapter{
		public int blocktype=0;
		public JLabel label;
		
		public mouseHandler(int blocktype) {
			this.blocktype = blocktype;
			
		}//end constructor
		//過載
		public mouseHandler(int blocktype, JLabel label) {
			this.blocktype = blocktype;//種類   1代表牆壁，0代表道路，3代表愛心 2是鑽石
			this.label = label;
		}//end constructor
		
		
		@Override
		public void mouseEntered(MouseEvent e) {
			//這個執行續池子是用來放停下來是扣血用的，ScheduledThreadPool是可以在指定秒數
			//之後執行，還有在指定週期重複執行
			 seService= Executors.newScheduledThreadPool(10);
			IdleHurting idleHurting = new IdleHurting(blocktype);
			//設定第一次執行在一秒後，之後週期為一秒，意思是每一秒都會執行一次
			//注意這執行序都是在進入一個JPanel 會執行，在MouseExited 裡面再刪除
			//等於是每次都只會有一個執行續被觸發且扣血(意味不會疊加扣血)
			seService.scheduleAtFixedRate(idleHurting, 1, 1,TimeUnit.SECONDS );
			switch(blocktype) {
			case 0:
				System.out.println("-2");
				bloodPnl.hitRoad();
				break;
			case 1:
				
				System.out.println("-20");
				bloodPnl.hitWall();
				break;
			case 2:
				System.out.println("finish");
				JOptionPane.showMessageDialog(null, "Congraduation!!", "Success", JOptionPane.DEFAULT_OPTION);
				System.exit(0);
				break;
			case 3:
				//第三種情況本來只有愛心而已，但是現在要讓他可以回牆壁再變回愛心，所以用label的icon 來判斷現在是哪一種狀況
				System.out.println("+10");
				if(label.getIcon().equals(wallIcon)) {
					bloodPnl.hitWall();
				}//end if
				else {
					bloodPnl.hitHeart();
				}//end else
				break;
			}//end switch
			
			if(bloodPnl.blood<1) {
				bloodPnl.relife(); //血回滿
				seService.shutdown();
		}//end if
		}//end mouseEntered
		
		
		@Override
		public void mouseExited(MouseEvent e) {
			seService.shutdown();
		}//end mouseExited
		
		
		
		
		
	}//end mouseHandler
	
	
	//改變愛心的執行緒
	public class ChangeHeart implements Runnable{
		public JLabel label;
		SecureRandom generator = new SecureRandom();
		
		public ChangeHeart(JLabel label) {
			this.label = label;
			
		}//end constructor
		
		public void run() {
			while(true) {
				
			//這邊讓他每次執行時都隨機睡覺    最多休息兩秒
			try {
				Thread.sleep(generator.nextInt(2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//要變動，所以原本是牆壁要變愛心，愛心要變牆壁
			if(label.getIcon().equals(wallIcon)) {
				label.setIcon(heartIcon);
			}//end if
			else {
				label.setIcon(wallIcon);
			}//end else
			}//end while
		}//end run
	}//end class changeHeart
	
	//這事讓他停下來的時候扣血用的執行續
	public class IdleHurting implements Runnable{
		public int blocktype;
		
		public IdleHurting(int blocktype) {
			this.blocktype = blocktype;
		}//end constructor
		
		public void run() {
			
			switch(blocktype) {
			case 0:
				bloodPnl.idleRoad();
				System.out.println("-2");
				break;
			case 1:
				bloodPnl.idleWall();
				break;
				
			}//end switch
			
		}//end running
	}//end class IdleHurting
	
}//end mazeFrame