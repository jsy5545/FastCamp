package com.JoSuYoung.memo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * �޸�
 * @author JoSuYoung
 *
 */
public class Memo {
	//���丮 ��� ��� ����
	public static final String MEMO_DR = "/temp/memo/";
	
	//���� Ŀ�ǵ带 ����� ����
	public static final String EXIT = "/exit";
	
	//�����ڿ��� �޸� ������ �����丮�� �������ش�.
	public Memo() {
		File dir = new File(MEMO_DR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	// 1.��ɾ ����ϴ� �Լ�
	public void showCommand() {
		System.out.println("�Ʒ� ��ɾ �Է��ϼ��� ---");
		System.out.println("1.����, 2.�б�, 3.����, 4.����, 0.���α׷�����");
	}

	// ���� 
	public void write(Scanner scanner) {
		System.out.println("----���� ���----"); 
		
		//�������� ������ ����
		StringBuilder content = new StringBuilder();
		
		//Ű���� �Է��� �޾ƾ� �Ѵ�.
		while(true) {
			String line = scanner.nextLine();
			if(line.equals(EXIT)) {
				break;
			}else {
				content.append(line + "\r\n");
			}
		}
		
		//�ۼ��� ������ ������ ���Ϸ� ����.
		if(!content.toString().equals("")) {
			//��. ���� �ð��� ������ ���ϸ����� �����.
			long now = System.currentTimeMillis();
			//��. �����_�ú���.txt ���Ϸ� ������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String filename = sdf.format(now) + ".txt";
			//��. ������ ������ ���ϰ�� ����
			Path path = Paths.get(MEMO_DR, filename);
			try {
				//��. ���Ͼ���
				Files.write(path, content.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println(String.format("$s�� ���������� �������ϴ�.", filename));
			
		}
	}
	
	//�б�
	public void read(Scanner scanner) {
		while(true) {
			//���ϸ��ȣ��
			String[] fileList = list();
			//���� ��ȣ ����
			System.out.println("�������� ���Ϲ�ȣ�� �Է��ϼ���.[��� : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//�ش����Ϲ�ȣ �˻�
				System.out.println("----�б� ���----");
				Path path = Paths.get(MEMO_DR, fileList[Integer.parseInt(input)]);
				File searchFile = new File(path.toString());
				//�˻����ϳ��� �����ֱ�
				show(searchFile);			 
			}
		}
		
	}

	//����
	public void update(Scanner scanner) {	
		while(true) {
			//���ϸ��ȣ��
			String[] fileList = list();
			
			//���� ��ȣ ����
			System.out.println("������ ���Ϲ�ȣ�� �Է��ϼ���.[��� : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//�ش����Ϲ�ȣ �˻�
				System.out.println("----���� ���----");
				Path path = Paths.get(MEMO_DR, fileList[Integer.parseInt(input)]);
				File search_file = new File(path.toString());
				StringBuilder content = show(search_file);
				
				//�߰����� ����
				while(true) {
					String line = scanner.nextLine();
					if(line.equals(EXIT)) {
						try {
							//��. ���Ͼ���
							Files.write(path, content.toString().getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}else {
						content.append(line + "\r\n");
					}
				}
			}
		}
	}

	//����
	public void destroy(Scanner scanner) {
		while(true) {
			//���ϸ��ȣ��
			String[] fileList = list();
			
			//���� ��ȣ ����
			System.out.println("������ ���Ϲ�ȣ�� �Է��ϼ���.[��� : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//�ش����Ϲ�ȣ �˻�
				System.out.println("----���� ���----");
				String filename = fileList[Integer.parseInt(input)];
				Path path = Paths.get(MEMO_DR, filename);
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

//		        if( searchFile.exists() ){ //�������翩��Ȯ��
//		             
//		            if(searchFile.isDirectory()){ //������ ���丮���� Ȯ��
//		                File[] files = searchFile.listFiles();
//	                     if( files[Integer.parseInt(input)].delete() ){
//	                        System.out.println(files[Integer.parseInt(input)].getName()+" ��������");
//	                    }else{
//	                        System.out.println(files[Integer.parseInt(input)].getName()+" ��������");
//	                    }
//		            }
//		            if(searchFile.delete()){
//		                System.out.println("���ϻ��� ����");
//		            }else{
//		                System.out.println("���ϻ��� ����");
//		            }
//		             
//		        }else{
//		            System.out.println("������ �������� �ʽ��ϴ�.");
//		        }
			}
		}
	}
	
	//���ϸ��
	private String[] list() {
		int count = 0; //���Ϲ�ȣ
		
		//���ϸ�� �����ֱ�
		File file = new File(MEMO_DR);
		String fileList[] = file.list();
		
		for(String filename : fileList) {
			System.out.println( String.format("%d. %s", count++, filename));
		}
		return fileList;
	}
	
	//���ϳ��� �����ֱ�
	private StringBuilder show(File File) {
		//�������� ������ ����
		StringBuilder newContent = new StringBuilder();
		
		//�ش� ���� �б�
		try {
			BufferedReader content = new BufferedReader(new FileReader(File));
			String line;
            while ((line = content.readLine()) != null) {
                System.out.println(line);
                newContent.append(line + "\r\n");
            }
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return newContent;
	}

}
