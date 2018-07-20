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
 * 메모
 * @author JoSuYoung
 *
 */
public class Memo {
	//디렉토리 경로 상수 정의
	public static final String MEMO_DR = "/temp/memo/";
	
	//종료 커맨드를 상수로 정의
	public static final String EXIT = "/exit";
	
	//생성자에서 메모를 저장할 디펙토리를 생성해준다.
	public Memo() {
		File dir = new File(MEMO_DR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	// 1.명령어를 출력하는 함수
	public void showCommand() {
		System.out.println("아래 명령어를 입력하세요 ---");
		System.out.println("1.쓰기, 2.읽기, 3.수정, 4.삭제, 0.프로그램종료");
	}

	// 쓰기 
	public void write(Scanner scanner) {
		System.out.println("----쓰기 모드----"); 
		
		//전제글을 저장할 변수
		StringBuilder content = new StringBuilder();
		
		//키보드 입력을 받아야 한다.
		while(true) {
			String line = scanner.nextLine();
			if(line.equals(EXIT)) {
				break;
			}else {
				content.append(line + "\r\n");
			}
		}
		
		//작성한 내용이 있으면 파일로 쓴다.
		if(!content.toString().equals("")) {
			//가. 현재 시간을 가져와 파일명으로 만든다.
			long now = System.currentTimeMillis();
			//나. 년월일_시분초.txt 파일로 포맷팅
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String filename = sdf.format(now) + ".txt";
			//다. 내용을 저장할 파일경로 설정
			Path path = Paths.get(MEMO_DR, filename);
			try {
				//라. 파일쓰기
				Files.write(path, content.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println(String.format("$s를 성공적으로 지웠습니다.", filename));
			
		}
	}
	
	//읽기
	public void read(Scanner scanner) {
		while(true) {
			//파일목록호출
			String[] fileList = list();
			//파일 번호 선택
			System.out.println("읽을려는 파일번호를 입력하세요.[취소 : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//해당파일번호 검색
				System.out.println("----읽기 모드----");
				Path path = Paths.get(MEMO_DR, fileList[Integer.parseInt(input)]);
				File searchFile = new File(path.toString());
				//검색파일내용 보여주기
				show(searchFile);			 
			}
		}
		
	}

	//수정
	public void update(Scanner scanner) {	
		while(true) {
			//파일목록호출
			String[] fileList = list();
			
			//파일 번호 선택
			System.out.println("수정할 파일번호를 입력하세요.[취소 : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//해당파일번호 검색
				System.out.println("----수정 모드----");
				Path path = Paths.get(MEMO_DR, fileList[Integer.parseInt(input)]);
				File search_file = new File(path.toString());
				StringBuilder content = show(search_file);
				
				//추가내용 기입
				while(true) {
					String line = scanner.nextLine();
					if(line.equals(EXIT)) {
						try {
							//라. 파일쓰기
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

	//삭제
	public void destroy(Scanner scanner) {
		while(true) {
			//파일목록호출
			String[] fileList = list();
			
			//파일 번호 선택
			System.out.println("삭제할 파일번호를 입력하세요.[취소 : '/exit']");
			String input = scanner.nextLine();
			
			if(input.equals(EXIT)) {
				break;
			}else {
				//해당파일번호 검색
				System.out.println("----삭제 모드----");
				String filename = fileList[Integer.parseInt(input)];
				Path path = Paths.get(MEMO_DR, filename);
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

//		        if( searchFile.exists() ){ //파일존재여부확인
//		             
//		            if(searchFile.isDirectory()){ //파일이 디렉토리인지 확인
//		                File[] files = searchFile.listFiles();
//	                     if( files[Integer.parseInt(input)].delete() ){
//	                        System.out.println(files[Integer.parseInt(input)].getName()+" 삭제성공");
//	                    }else{
//	                        System.out.println(files[Integer.parseInt(input)].getName()+" 삭제실패");
//	                    }
//		            }
//		            if(searchFile.delete()){
//		                System.out.println("파일삭제 성공");
//		            }else{
//		                System.out.println("파일삭제 실패");
//		            }
//		             
//		        }else{
//		            System.out.println("파일이 존재하지 않습니다.");
//		        }
			}
		}
	}
	
	//파일목록
	private String[] list() {
		int count = 0; //파일번호
		
		//파일목록 보여주기
		File file = new File(MEMO_DR);
		String fileList[] = file.list();
		
		for(String filename : fileList) {
			System.out.println( String.format("%d. %s", count++, filename));
		}
		return fileList;
	}
	
	//파일내용 보여주기
	private StringBuilder show(File File) {
		//전제글을 저장할 변수
		StringBuilder newContent = new StringBuilder();
		
		//해당 파일 읽기
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
