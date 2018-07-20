package com.JoSuYoung.memo;
import java.util.Scanner;

/**
 * ���� �޸���
 * @author JoSuYoung
 *
 */
public class MemoMain {

	public static void main(String[] args) {
		// 1. Ű���� �Է����� ��ɾ ���� �޴´�.
		// -���α׷��� �����ϸ� ��ɾ� ��ȣ�� �����ش�.
		// - 1.����, 2.�б�, 3.����, 4.����
		Memo memo = new Memo();
		
		
		Scanner scanner = new Scanner(System.in);
		
		boolean runFlag = true;
		
		while(runFlag) {
			memo.showCommand();
			String cmd = scanner.nextLine();
			
			switch(cmd) {
			case "1": //����
				memo.write(scanner);
				break;
			case "2": //�б�
				memo.read(scanner);
				break;
			case "3": //����
				memo.update(scanner);
				break;
			case "4": //����
				memo.destroy(scanner);
				break;
			case "0": //���α׷�����
				runFlag = false;
				break;
			default:
				System.out.println("��ɾ �߸��Ǿ����ϴ�.");
			}	
		}

	}

}
